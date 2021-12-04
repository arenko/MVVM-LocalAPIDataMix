package com.arenko.rijksapp.workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class TimerWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        try {
            return Result.success()
        } catch (e: Exception) {
            return Result.failure()
        }
    }
}