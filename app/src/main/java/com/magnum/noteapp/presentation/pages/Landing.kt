package com.magnum.noteapp.presentation.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.magnum.noteapp.R
import com.magnum.noteapp.presentation.components.RoundedCornerCard
import com.magnum.noteapp.presentation.viewModel.NoteViewModel
import androidx.compose.runtime.livedata.observeAsState
import org.koin.compose.viewmodel.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingPage(navController: NavController) {
    val noteViewModel:NoteViewModel= koinViewModel()
    val allNotes = noteViewModel.allNotes.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) { Text("NOTES", fontWeight = FontWeight.Bold) }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(id = R.color.teal_700),
                    titleContentColor = colorResource(id = R.color.white),
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("createNote")
                },
                content = {
                    Icon(Icons.Filled.Add, contentDescription = "Add")
                }
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                allNotes.value?.let {

                    if (it.isEmpty()) {
                        Text(text = "NO notes!")
                    } else {
                        LazyColumn(
                            Modifier
                                .fillMaxSize()
                                .padding(10.dp)
                        ) {
                            itemsIndexed(it) { _, item ->
                                Spacer(modifier = Modifier.height(2.dp))
                                RoundedCornerCard(
                                    item,
                                    onDelete = { noteViewModel.deleteNote(item) },
                                    handleViewNote = { navController.navigate("viewNote/${item.id}") },
                                    handleEditNote = { navController.navigate("editNote/${item.id}") })
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }
    )
}