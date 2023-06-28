package com.example.periodicworkmanager

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.work.BackoffPolicy
import androidx.work.Data
import androidx.work.Operation
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.periodicworkmanager.databinding.ActivityMainBinding
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var periodicWorkRequest: PeriodicWorkRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnStartWork.setOnClickListener {
            Log.d("TAG", "Starting periodic work!")
            Toast.makeText(this, "Starting periodic work!", Toast.LENGTH_SHORT).show()
            val data: Data = Data.Builder().putString("key", "IT wala periodic work").build()

            periodicWorkRequest =
                PeriodicWorkRequest.Builder(MyPeriodicWork::class.java, 15, TimeUnit.MINUTES)
                    .addTag("periodicwork" + System.currentTimeMillis()).setBackoffCriteria(
                        BackoffPolicy.LINEAR,
                        PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS,
                        TimeUnit.MILLISECONDS
                    ).setInputData(data).build()

            WorkManager.getInstance(this).enqueue(periodicWorkRequest)
        }

        binding.btnStopWork.setOnClickListener {
            val operation: Operation =
                WorkManager.getInstance(this).cancelWorkById(periodicWorkRequest.id)
            MyPeriodicWork.isStopped = true
            Log.d("TAG", "Stopping periodic work!")
            Toast.makeText(
                this, "Stopping periodic work!" + operation.state, Toast.LENGTH_SHORT
            ).show()
        }

    }
}