package com.moose.work.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.moose.work.data.Data
import com.moose.work.local.AppDatabase
import java.util.*

class UsersWork(context: Context, params: WorkerParameters):  CoroutineWorker(context, params){
    private val dao = AppDatabase.getDatabase(context).userDao()

    override suspend fun doWork(): Result {
        return try {
            val user = Data.getUser()
            user.description = "User was created at ${Date()}"

            dao.addUser(user)

            Result.success(workDataOf("user" to user.toString()))
        } catch (e: Throwable) {
            Result.failure(workDataOf("error" to e.message))
        }
    }
}