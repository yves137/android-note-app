package com.magnum.noteapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.magnum.noteapp.presentation.screens.create_note.CreateNoteScreenRoot
import com.magnum.noteapp.presentation.screens.update_note.UpdateNotePageScreen
import com.magnum.noteapp.presentation.screens.list_all_notes.ListAllNotesRootScreen
import com.magnum.noteapp.presentation.screens.view_note.ViewNoteRootScreen
import com.magnum.noteapp.presentation.view.theme.NoteAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NoteAppTheme {
                NoteAppNavigation()
            }
        }
    }
}

@Composable
fun NoteAppNavigation() {
    val navigationController = rememberNavController()

    NavHost(navController = navigationController, startDestination = "landing") {
        composable("landing") {
            ListAllNotesRootScreen(navigationController)
        }

        composable("createNote") {
            CreateNoteScreenRoot(navigationController)
        }

        composable(
            route = "editNote/{noteId}", arguments = listOf(
                navArgument("noteId") { type = NavType.StringType }
            )) { navBackStackEntry ->

            val noteId = navBackStackEntry.arguments?.getString("noteId")
            noteId?.let {
                UpdateNotePageScreen(navigationController, noteId)

            }
        }

        composable(
            route = "viewNote/{noteId}", arguments = listOf(
                navArgument("noteId") { type = NavType.StringType }
            )) { navBackStackEntry ->
            val noteId = navBackStackEntry.arguments?.getString("noteId")
            noteId?.let {
                ViewNoteRootScreen(navigationController, noteId)

            }
        }
    }
}
