package com.magnum.noteapp.presentation.screens.view_note


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ViewNoteRootScreen(navController: NavController, noteId: String) {
    val viewModel: ViewNoteViewModel = koinViewModel {
        parametersOf(noteId)
    }

    val state by viewModel.note.collectAsStateWithLifecycle()
    val timer by viewModel.timer.collectAsStateWithLifecycle()

    ViewNoteScreen(
        timer = timer,
        noteState = state,
        handleNavigateBack = {
            navController.popBackStack()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewNoteScreen(
    timer: String,
    noteState: ViewNoteViewModel.GetNoteUIState,
    handleNavigateBack: () -> Unit = {},
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
                            "PREVIEW", fontWeight = FontWeight.Bold
                        )
                        Text(
                            timer,
                            fontSize = 16.sp,
                            modifier = Modifier.padding(end = 10.dp)
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
                        text = noteState.note?.title ?: "No title",
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        color = colorResource(id = R.color.teal_700)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = noteState.note?.content ?: "",
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
        timer = "00:00:00",
        noteState = ViewNoteViewModel.GetNoteUIState(
            note = Note(
                title = "Sample Note",
                content = "This is a sample note content."
            )
        ),
        handleNavigateBack = {}
    )
}