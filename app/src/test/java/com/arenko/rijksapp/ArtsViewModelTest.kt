package com.arenko.rijksapp

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.work.Configuration
import androidx.work.testing.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import com.arenko.RijksApp
import com.arenko.rijksapp.data.model.ArtImage
import com.arenko.rijksapp.data.model.ArtObject
import com.arenko.rijksapp.domain.usecase.GetArtObjectsUseCase
import com.arenko.rijksapp.ui.artlist.ArtsViewModel
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito

@RunWith(AndroidJUnit4::class)
class ArtsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ArtsViewModel

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getTargetContext()
        val config = Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setExecutor(SynchronousExecutor())
            .build()
        WorkManagerTestInitHelper.initializeTestWorkManager(context, config)

        val fakeArtRepository = FakeArtRepository()
        val getMoviesUsecase = GetArtObjectsUseCase(fakeArtRepository)
        viewModel = ArtsViewModel(getMoviesUsecase, Mockito.mock(RijksApp::class.java))
    }

    @Test
    fun getArtObjects_returnsCurrentList() {
        val artObjects = mutableListOf<ArtObject>()
        artObjects.add(ArtObject("SK-A", "title1", "maker1", "desc1", ArtImage("")))
        artObjects.add(ArtObject("SK-B", "title2", "maker2", "desc2", ArtImage("")))


        val currentList = viewModel.getArtObjects(1).getOrAwaitValue()
        assertThat(currentList).isEqualTo(artObjects)

    }

    @Test
    fun getArtObject_returnDetail() {
        val artObject =ArtObject("SK-C", "title3", "maker3", "desc3", ArtImage(""))

        val detailObject = viewModel.getDetail("SK-C").getOrAwaitValue()
        assertThat(detailObject).isEqualTo(artObject)

    }
}









