package com.example.ex11

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import org.junit.Rule
import org.junit.Test

class NoteScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun addNoteButtonIsDisplayed() {
        composeRule.setContent {
            NoteScreen()
        }
        composeRule
            .onNodeWithText("Add Note")
            .assertIsDisplayed()
    }

    @Test
    fun clickingAddNoteDisplaysMessage() {
        composeRule.setContent {
            NoteScreen()
        }
        composeRule
            .onNodeWithText("Add Note")
            .performClick()

        composeRule
            .onNodeWithText("Please enter a valid note")
            .assertIsDisplayed()
    }
}
