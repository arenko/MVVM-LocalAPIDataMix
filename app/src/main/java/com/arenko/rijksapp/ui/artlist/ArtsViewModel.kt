package com.arenko.rijksapp.ui.artlist

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.arenko.rijksapp.data.Constants.TAG_OUTPUT
import com.arenko.rijksapp.domain.usecase.GetArtObjectsUseCase
import com.arenko.rijksapp.workmanager.TimerWorker
import java.util.concurrent.TimeUnit


class ArtsViewModel @ViewModelInject constructor(
    private val getArtObjectsUseCase: GetArtObjectsUseCase,
    application: Application
) :
    ViewModel() {

    private val workManager = WorkManager.getInstance(application)
    internal val outputWorkInfos: LiveData<List<WorkInfo>>

    init {
        outputWorkInfos = workManager.getWorkInfosByTagLiveData(TAG_OUTPUT)
    }

    internal fun cancelWork() {
        workManager.pruneWork()
    }

    fun getArtObjects(page: Int) = liveData {
        emit(getArtObjectsUseCase.getArtObjects(page))
    }

    fun getDetail(code: String) = liveData {
        emit(getArtObjectsUseCase.getArtObjectsDetail(code))
    }

    fun cleanDB() {
        getArtObjectsUseCase.cleanDB()
    }

    fun startWorkManager() {
        val timerRequest = OneTimeWorkRequest.Builder(TimerWorker::class.java)
            .setInitialDelay(5, TimeUnit.MINUTES)
            .addTag(TAG_OUTPUT)
            .build()

        workManager.enqueue(timerRequest)
    }

}