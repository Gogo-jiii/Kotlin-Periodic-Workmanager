package com.example.periodicworkmanager

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.work.Data
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyPeriodicWork(
    private val context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {
    private var timeMillis: Long = 0
    private val handler: Handler = Handler(Looper.getMainLooper())

    companion object {
        private var counter = 0
        var isStopped: Boolean = false
    }

    init {
        setProgressAsync(
            Data.Builder().putString("PROGRESS", "Time: " + 0L + "_" + "Counter: " + 0).build()
        )
        Companion.isStopped = false
    }

    override fun doWork(): Result {
        //get current milliseconds
        timeMillis = System.currentTimeMillis()
        counter++
        setProgressAsync(
            Data.Builder().putString(
                "PROGRESS",
                "Time: " + timeMillis + "_" + "Counter: " + counter
            ).build()
        )
        handler.post {
            Toast.makeText(
                context, "Time: " + timeMillis + "_and_" + "Counter: " + counter,
                Toast.LENGTH_SHORT
            ).show()
        }
        return Result.success()
    }
}