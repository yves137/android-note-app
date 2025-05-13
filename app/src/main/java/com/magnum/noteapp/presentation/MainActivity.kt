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
import com.magnum.noteapp.presentation.pages.CreateNotePage
import com.magnum.noteapp.presentation.pages.EditNotePageRoot
import com.magnum.noteapp.presentation.pages.LandingPage
import com.magnum.noteapp.presentation.pages.ViewNotePage
import com.magnum.noteapp.presentation.theme.NoteAppTheme

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
            LandingPage(navigationController)
        }

        composable("createNote") {
            CreateNotePage(navigationController)
        }

        composable(
            route = "editNote/{noteId}", arguments = listOf(
            navArgument("noteId") { type = NavType.StringType }
        )) { navBackStackEntry ->

            val noteId = navBackStackEntry.arguments?.getString("noteId")
            noteId?.let {
                EditNotePageRoot(navigationController, noteId)

            }
        }

        composable(
            route = "viewNote/{noteId}", arguments = listOf(
            navArgument("noteId") { type = NavType.StringType }
        )) { navBackStackEntry ->
            val noteId = navBackStackEntry.arguments?.getString("noteId")
            noteId?.let {
                ViewNotePage(navigationController, noteId)

            }
        }
    }
}
