package com.magnum.noteapp.presentation.screens.list_all_notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.magnum.noteapp.presentation.view.components.RoundedCornerCard
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.magnum.noteapp.domain.model.Note

@Composable
fun ListAllNotesRootScreen(navController: NavController) {
    val noteViewModel: ListAllNotesViewModel = koinViewModel()
    val combinedState by noteViewModel.combinedState.collectAsStateWithLifecycle()
    ListAllNotesScreen(
        noteUIState = combinedState,
        handleViewNote = { noteId ->
            navController.navigate("viewNote/$noteId")
        },
        handleEditNote = { noteId ->
            navController.navigate("editNote/$noteId")
        },
        handleDeleteNote = {
            noteViewModel.deleteNote(it)
        },
        handleNavigateToCreateNote = {
            navController.navigate("createNote")
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListAllNotesScreen(
    noteUIState: ListAllNotesViewModel.ListAllNotesUIState,
    handleViewNote: (String) -> Unit = {},
    handleEditNote: (String) -> Unit = {},
    handleDeleteNote: (String) -> Unit = {},
    handleNavigateToCreateNote: () -> Unit = {},
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Absolute.Right
                        ) {
                            Text("NOTES", fontWeight = FontWeight.Bold)
                            Spacer(modifier = Modifier.width(90.dp))
                            Text(
                                noteUIState.timer,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(end = 10.dp)
                            )
                        }
                    }
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
                    handleNavigateToCreateNote()
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


                if (noteUIState.notes.isEmpty()) {
                    Text(text = "NO notes!")
                } else {
                    LazyColumn(
                        Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    ) {
                        itemsIndexed(noteUIState.notes) { _, item ->
                            Spacer(modifier = Modifier.height(2.dp))
                            RoundedCornerCard(
                                item,
                                onDelete = { handleDeleteNote(item.id) },
                                handleViewNote = { handleViewNote(item.id) },
                                handleEditNote = { handleEditNote(item.id) })
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }

            }
        }
    )
}

@Composable
@Preview
fun LandingScreenPreview() {
    val notes = listOf(
        Note(
            id = "1",
            title = "Note 1",
            content = "This is the first note"
        ),
        Note(
            id = "2",
            title = "Note 2",
            content = "This is the second note"
        )
    )
    ListAllNotesScreen(
        noteUIState = ListAllNotesViewModel.ListAllNotesUIState(
            notes = notes,
            timer = "00:00:00"
        ),
        handleViewNote = {},
        handleEditNote = {},
        handleDeleteNote = {},
        handleNavigateToCreateNote = {}
    )
}