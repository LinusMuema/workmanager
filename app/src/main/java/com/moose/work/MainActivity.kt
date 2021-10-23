package com.moose.work

import android.os.Bundle
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.moose.work.data.User
import com.moose.work.local.AppDatabase
import com.moose.work.ui.theme.WorkTheme

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