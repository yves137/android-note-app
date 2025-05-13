package com.magnum.noteapp.presentation.pages

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.magnum.noteapp.R
import com.magnum.noteapp.data.database.entity.NoteEntity
import com.magnum.noteapp.presentation.viewModel.NoteViewModel
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNotePage(navController: NavController) {
    val noteViewModel: NoteViewModel = koinViewModel()
    val noteTitle = remember { mutableStateOf("") }
    val noteContent = remember { mutableStateOf("") }
    val isSaveEnabled = remember { derivedStateOf { noteTitle.value.trim().isNotEmpty() } }
    val coroutineScope = rememberCoroutineScope()
    val localContext = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(onClick = {
                            navController.popBackStack()
                        }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }

                        Text(
                            "CREATE NOTE", fontWeight = FontWeight.Bold
                        )

                        IconButton(onClick = {
                            coroutineScope.launch {
                                val noteEntity =
                                    NoteEntity(title = noteTitle.value, content = noteContent.value)
                                noteViewModel.insertNote(noteEntity)
                                Toast.makeText(localContext, "Note Created", Toast.LENGTH_SHORT).show()
                                navController.popBackStack()
                            }
                        }, enabled = isSaveEnabled.value) {
                            Icon(Icons.Filled.Done, contentDescription = "Save")
                        }
                    }

                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.teal_700),
                    titleContentColor = colorResource(id = R.color.white),
                )
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize()
                ) {

                    TextField(
                        value = noteTitle.value,
                        onValueChange = {
                            noteTitle.value = it
                        },
                        label = { Text("Title") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(width = 2.dp, color = colorResource(id = R.color.teal_300))
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        value = noteContent.value,
                        onValueChange = {
                            noteContent.value = it
                        },
                        label = { Text("Content") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.9f)
                            .border(width = 2.dp, color = colorResource(id = R.color.teal_300))
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }

            }
        }
    )

}