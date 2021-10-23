package com.moose.work.work

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class ExampleWork(context: Context, params: WorkerParameters) : Worker(context, params) {
    override fun doWork(): Result {
        val name = inputData.getString("name")
        val age = inputData.getInt("age", 0)
        Log.d("ExampleWork", "doWork: My name is $name and I am $age years old")
        return Result.success()
    }
}