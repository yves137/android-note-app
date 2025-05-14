package com.magnum.noteapp.presentation.screens.update_note

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.magnum.noteapp.R
import com.magnum.noteapp.domain.model.Note
import com.magnum.noteapp.presentation.screens.shared_viewModel.GetNoteByIdViewModel
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun EditNotePageScreen(
    navController: NavController,
    noteId: String
) {
    val getNoteByIdViewModel: GetNoteByIdViewModel = koinViewModel()
    val updateNoteViewModel: UpdateNoteViewModel = koinViewModel()

    getNoteByIdViewModel.loadNoteById(noteId)
    val foundNoteState by getNoteByIdViewModel.note.collectAsStateWithLifecycle()
    val state by updateNoteViewModel.note.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = foundNoteState) {
        foundNoteState?.let {
            updateNoteViewModel.modifyNote(it)
        }
    }

    EditNotePage(
        handleNavigateBack = { navController.popBackStack() },
        state = state,
        handleSaveNote = {
            updateNoteViewModel.updateNote()
            navController.popBackStack()
        },
        handleEditTitle = { noteTitle ->
            updateNoteViewModel.modifyNoteTitle(noteTitle)
        },
        handleEditContent = { noteContent ->
            updateNoteViewModel.modifyNoteContent(noteContent)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNotePage(
    handleNavigateBack: () -> Unit,
    state: UpdateNoteViewModel.UpdateNoteUIState,
    handleSaveNote: () -> Unit = {},
    handleEditTitle: (title: String) -> Unit = {},
    handleEditContent: (content: String) -> Unit = {},
) {

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
                            handleNavigateBack()
                        }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }

                        Text(
                            "EDIT NOTE", fontWeight = FontWeight.Bold
                        )

                        IconButton(onClick = {
                            handleSaveNote()
                            Toast.makeText(localContext, "Note Edited", Toast.LENGTH_SHORT).show()
                        }, enabled = state.isSavable) {
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
                        value = state.note?.title ?: "NO nOte",
                        onValueChange = {
                            handleEditTitle(it)
                        },
                        label = { Text("Title") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(width = 2.dp, color = colorResource(id = R.color.teal_300))
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        value = state.note?.content ?: "No Content",
                        onValueChange = {
                            handleEditContent(it)
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

@Composable
@Preview
fun PreviewEditNote() {

    val state =
        UpdateNoteViewModel.UpdateNoteUIState(
            note = Note(title = "Thomas", content = "Content"),
            isSavable = true
        )


    EditNotePage(
        handleNavigateBack = {},
        state = state,
        handleSaveNote = {},
        handleEditTitle = {},
        handleEditContent = {})
}