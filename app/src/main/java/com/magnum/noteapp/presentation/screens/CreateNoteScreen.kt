package com.magnum.noteapp.presentation.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.magnum.noteapp.R
import com.magnum.noteapp.presentation.viewModel.CreateNoteViewModel
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.magnum.noteapp.domain.model.Note

@Composable
fun CreateNoteScreenRoot(navController: NavController) {
    val noteViewModel: CreateNoteViewModel = koinViewModel()
    val noteState by noteViewModel.note.collectAsStateWithLifecycle()
    val localContext = LocalContext.current
    CreateNoteScreen(
        noteState = noteState,
        handleCreateNote = {
            noteViewModel.createNote()
            Toast.makeText(localContext, "Note Created", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
        },
        handleNavigateBack = {
            navController.popBackStack()
        },
        handleModifyTitle = { title ->
            noteViewModel.modifyNoteTitle(title)
        },
        handleModifyContent = { content ->
            noteViewModel.modifyNoteContent(content)
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteScreen(
    noteState: CreateNoteViewModel.CreateNoteUIState,
    handleCreateNote: () -> Unit,
    handleNavigateBack: () -> Unit,
    handleModifyTitle: (title: String) -> Unit,
    handleModifyContent: (content: String) -> Unit
) {
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
                            "CREATE NOTE", fontWeight = FontWeight.Bold
                        )

                        IconButton(onClick = {
                            handleCreateNote()
                        }, enabled = noteState.isSavable) {
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
                        value = noteState.note.title,
                        onValueChange = {
                            handleModifyTitle(it)
                        },
                        label = { Text("Title") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(width = 2.dp, color = colorResource(id = R.color.teal_300))
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    TextField(
                        value = noteState.note.content,
                        onValueChange = {
                            handleModifyContent(it)
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
fun PreviewCreateNote() {

    val state = CreateNoteViewModel.CreateNoteUIState(
        note = Note(
            title = "custom title",
            content = " custom Content"
        ),
    )

    CreateNoteScreen(
        noteState = state,
        handleCreateNote = { },
        handleNavigateBack = { },
        handleModifyTitle = { },
        handleModifyContent = { }
    )
}