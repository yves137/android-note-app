package com.magnum.noteapp.presentation.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.magnum.noteapp.R
import com.magnum.noteapp.domain.model.Note


@Composable
fun RoundedCornerCard(
    note: Note,
    onDelete: () -> Unit,
    handleViewNote: () -> Unit,
    handleEditNote: () -> Unit
) {
    val showAlertDialog = remember { mutableStateOf(false) }

    if (showAlertDialog.value) {
        DeleteNoteAlert(showAlertDialog, onDelete)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .border(
                width = 2.dp,
                color = colorResource(id = R.color.teal_300),
                shape = RoundedCornerShape(10.dp)
            ),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
        shape = RoundedCornerShape(10.dp),
        onClick = {
            handleViewNote()
        }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp, top = 10.dp, bottom = 5.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = note.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f),
                    fontWeight = FontWeight.Medium,
                    color = colorResource(id = R.color.teal_700)
                )
                Row {
                    IconButton(onClick = {
                        handleEditNote()
                    }) {
                        Icon(
                            Icons.Filled.Edit,
                            contentDescription = "Edit",
                            tint = colorResource(id = R.color.light_blue)
                        )
                    }
                    IconButton(onClick = {
                        showAlertDialog.value = true

                    }) {
                        Icon(
                            Icons.Filled.Delete,
                            contentDescription = "Delete",
                            tint = colorResource(id = R.color.light_blue)
                        )
                    }

                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = note.content,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(0.8f),
                    fontSize = 12.sp,
                )
            }
        }
    }
}