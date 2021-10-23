package com.moose.work

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.work.*
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.moose.work.data.User
import com.moose.work.local.AppDatabase
import com.moose.work.ui.theme.WorkTheme
import com.moose.work.work.ExampleWork
import com.moose.work.work.UsersWork

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WorkTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Content()
                }
            }
        }

        // start our Job
        startWork()
    }

    private fun startWork() {

        // get the instance
        val manager = WorkManager.getInstance(application)

        // create the constraints
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresBatteryNotLow(true)
            .build()

        // create the data
        val data = workDataOf("name" to "LinusMoose", "age" to 20)

        // create the request
        val request = OneTimeWorkRequestBuilder<UsersWork>()
            .setConstraints(constraints)
            .setInputData(data)
            .build()

        manager.enqueue(request)
    }
}

@Composable
fun Content() {
    val dao = AppDatabase.getDatabase(LocalContext.current).userDao()
    val users: List<User>? by dao.getUsers().observeAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            if (users !== null){
                items(users!!){
                    Card(elevation = 10.dp, modifier = Modifier.fillMaxWidth().padding(10.dp)) {
                        Row {
                            Image(
                                painter = rememberImagePainter(
                                    data = it.image,
                                    builder = { transformations(CircleCropTransformation()) }
                                ),
                                contentDescription = null,
                                modifier = Modifier.size(125.dp)
                            )
                            Column(Modifier.padding(20.dp)) {
                                Text(it.name)
                                Text(it.description)
                            }
                        }
                    }
                }
            }
        }
    }
}