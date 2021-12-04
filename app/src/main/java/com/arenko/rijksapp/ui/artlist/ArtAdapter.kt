package com.arenko.rijksapp.ui.artlist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.arenko.rijksapp.R
import com.arenko.rijksapp.data.model.ArtObject
import com.arenko.rijksapp.databinding.ItemArtBinding
import com.arenko.rijksapp.di.GlideApp

class ArtAdapter(private val clickListener: (ArtObject) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val artList = ArrayList<ArtObject>()

    fun setList(arts: List<ArtObject>) {
        artList.clear()
        artList.addAll(arts)
    }

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): RijksViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemArtBinding =
            DataBindingUtil.inflate(layoutInflater, R.layout.item_art, parent, false)
        return RijksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as RijksViewHolder
        viewHolder.bind(artList[position], clickListener)
    }

    override fun getItemCount(): Int {
        return artList.size
    }

    inner class RijksViewHolder(val binding: ItemArtBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(artObject: ArtObject, clickListener: (ArtObject) -> Unit) {
            binding.tvTitle.text = artObject.title
            binding.tvMaker.text = artObject.principalOrFirstMaker
            try {
                GlideApp.with(binding.root.context).load(artObject.headerImage?.url)
                    .into(binding.ivItem)
            } catch (e: Exception) {
            }
            binding.root.setOnClickListener {
                clickListener(artObject)
            }
        }
    }
}
