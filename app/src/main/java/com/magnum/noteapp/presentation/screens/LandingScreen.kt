package com.magnum.noteapp.presentation.screens

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
import com.magnum.noteapp.presentation.viewModel.GetNotesViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.magnum.noteapp.domain.model.Note
import com.magnum.noteapp.presentation.viewModel.DeleteNoteViewModel

@Composable
fun LandingRootScreen(navController: NavController) {
    val noteViewModel: GetNotesViewModel = koinViewModel()
    val deleteNoteViewModel: DeleteNoteViewModel = koinViewModel()
    val allNotes by noteViewModel.notes.collectAsStateWithLifecycle()
    LandingScreen(
        allNotes = allNotes,
        handleViewNote = { noteId ->
            navController.navigate("viewNote/$noteId")
        },
        handleEditNote = { noteId ->
            navController.navigate("editNote/$noteId")
        },
        handleDeleteNote = {
            deleteNoteViewModel.deleteNote(it)
        },
        handleNavigateToCreateNote = {
            navController.navigate("createNote")
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandingScreen(
    allNotes: GetNotesViewModel.GetNotesUIState,
    handleViewNote: (String) -> Unit = {},
    handleEditNote: (String) -> Unit = {},
    handleDeleteNote: (Note) -> Unit = {},
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


                if (allNotes.notes.isEmpty()) {
                    Text(text = "NO notes!")
                } else {
                    LazyColumn(
                        Modifier
                            .fillMaxSize()
                            .padding(10.dp)
                    ) {
                        itemsIndexed(allNotes.notes) { _, item ->
                            Spacer(modifier = Modifier.height(2.dp))
                            RoundedCornerCard(
                                item,
                                onDelete = { handleDeleteNote(item) },
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
    LandingScreen(
        allNotes = GetNotesViewModel.GetNotesUIState(notes),
        handleViewNote = {},
        handleEditNote = {},
        handleDeleteNote = {},
        handleNavigateToCreateNote = {}
    )
}