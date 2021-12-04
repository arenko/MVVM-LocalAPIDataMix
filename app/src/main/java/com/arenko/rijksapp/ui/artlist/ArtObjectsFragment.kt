package com.arenko.rijksapp.ui.artlist

import android.nfc.tech.MifareUltralight
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkInfo
import com.arenko.rijksapp.R
import com.arenko.rijksapp.data.model.ArtObject
import com.arenko.rijksapp.databinding.ListFragmentBinding
import com.arenko.rijksapp.util.AlertDialogHelper
import com.arenko.rijksapp.util.NetworkHelper
import com.arenko.rijksapp.util.ext.toast
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ArtObjectsFragment : Fragment() {
    lateinit var binding: ListFragmentBinding
    private lateinit var artObjectAdapter: ArtAdapter
    private lateinit var viewModel: ArtsViewModel
    lateinit var layoutManager: LinearLayoutManager
    var pageNumber: Int = 1
    private var isLoading = false
    private var isLastPage = false
    private var isFromScroll = false
    private var artObjectList: ArrayList<ArtObject> = arrayListOf()

    companion object {
        fun newInstance() = ArtObjectsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ArtsViewModel::class.java)
        viewModel.outputWorkInfos.observe(this, workInfosObserver())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (NetworkHelper.isInternetAvailable(requireActivity())) {
            initAdapter()
            showResult()
        } else {
            binding.loader.root.visibility = View.GONE
            context?.toast(resources.getString(R.string.error_network))
        }
    }

    private fun initAdapter() {
        artObjectAdapter =
            ArtAdapter { selectedItem: ArtObject -> listItemClicked(selectedItem) }
        layoutManager = LinearLayoutManager(context)
        binding.rvList.layoutManager = layoutManager
        binding.rvList.adapter = artObjectAdapter
        binding.rvList.addOnScrollListener(recyclerViewOnScrollListener)
        binding.swipeRefreshLayout.setOnRefreshListener {
            refreshItems()
        }
    }

    private fun showResult() {
        viewModel.getArtObjects(pageNumber).observe(viewLifecycleOwner, Observer {
            binding.loader.root.visibility = View.GONE
            if (it != null) {
                if (it.isEmpty()) {
                    isLastPage = true
                }
                isLoading = false
                val listTempArt: List<ArtObject> = it
                artObjectList.addAll(listTempArt)
                artObjectAdapter.setList(artObjectList)
                artObjectAdapter.notifyDataSetChanged()
            } else {
                isLoading = true
                context?.toast(resources.getString(R.string.error_response))
            }
        })
    }

    fun refreshItems() {
        initAdapter()
        showResult()
        binding.swipeRefreshLayout.isRefreshing = false
    }

    private val recyclerViewOnScrollListener: RecyclerView.OnScrollListener =
        object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount: Int = layoutManager.getChildCount()
                val totalItemCount: Int = layoutManager.getItemCount()
                val firstVisibleItemPosition: Int = layoutManager.findFirstVisibleItemPosition()
                if (!isLoading && !isLastPage) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0 && totalItemCount >= MifareUltralight.PAGE_SIZE) {
                        isLoading = true
                        isFromScroll = true
                        pageNumber++
                        showResult()
                    }
                }
            }
        }

    private fun workInfosObserver(): Observer<List<WorkInfo>> {
        return Observer { listOfWorkInfo ->
            if (listOfWorkInfo.isNullOrEmpty()) {
                return@Observer
            }
            val workInfo = listOfWorkInfo[0]
            if (workInfo.state.isFinished) {
                viewModel.cleanDB()
                viewModel.cancelWork()
            }
        }
    }

    private fun listItemClicked(artObject: ArtObject) {
        binding.loader.root.visibility = View.VISIBLE
        viewModel.getDetail(artObject.objectNumber).observe(viewLifecycleOwner, {
            binding.loader.root.visibility = View.GONE
            if (it != null) {
                AlertDialogHelper.showAlert(
                    requireContext(),
                    it.title,
                    it.description,
                    true
                )
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.startWorkManager()
    }
}