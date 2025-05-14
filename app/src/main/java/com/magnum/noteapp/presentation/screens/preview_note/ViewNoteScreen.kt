package com.magnum.noteapp.presentation.screens.preview_note


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.magnum.noteapp.R
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.magnum.noteapp.domain.model.Note
import com.magnum.noteapp.presentation.screens.shared_viewModel.GetNoteByIdViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ViewNoteRootScreen(navController: NavController, noteId: String) {
    val viewModel: GetNoteByIdViewModel = koinViewModel()
    viewModel.loadNoteById(noteId)
    val state by viewModel.note.collectAsStateWithLifecycle()

    ViewNoteScreen(
        noteState = state,
        handleNavigateBack = {
            navController.popBackStack()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewNoteScreen(
    noteState: Note?,
    handleNavigateBack: () -> Unit = {},
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = {
                            handleNavigateBack()
                        }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }

                        Spacer(modifier = Modifier.width(60.dp))

                        Text(
                            "PREVIEW", fontWeight = FontWeight.Bold
                        )

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

                    Text(
                        text = noteState?.title ?: "No title",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = colorResource(id = R.color.teal_700)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = noteState?.content ?: "",
                        fontSize = 18.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .verticalScroll(rememberScrollState())
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }

            }
        }
    )
}

@Composable
@Preview
fun ViewNotePreview() {
    ViewNoteScreen(
        noteState = Note(
            title = "Sample Note",
            content = "This is a sample note content."
        ),
        handleNavigateBack = {}
    )
}