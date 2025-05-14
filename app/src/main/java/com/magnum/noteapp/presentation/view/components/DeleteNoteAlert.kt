package com.magnum.noteapp.presentation.view.components

import android.widget.Toast
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext

@Composable
fun DeleteNoteAlert(showAlertDialog: MutableState<Boolean>, handleConfirmDelete: () -> Unit) {
    val localContext = LocalContext.current
    AlertDialog(
        onDismissRequest = { showAlertDialog.value = false },
        title = { Text("Delete Note") },
        text = { Text("Are you sure you want to delete this note?") },
        confirmButton = {
            Button(onClick = {
                showAlertDialog.value = false
                handleConfirmDelete()
                Toast.makeText(localContext, "note deleted", Toast.LENGTH_SHORT).show()
            }) { Text("Delete") }
        },
        dismissButton = {
            Button(onClick = {
                showAlertDialog.value = false
            }) { Text("Cancel") }
        }
    )
}