package com.contelli.jettrivia.screens

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun QuestionScreen(viewModel: QuestionViewModel = hiltViewModel()) {
    val questions = viewModel.data.value.data?.toMutableList()
    Log.d("Questions", "QuestionScreen: ${questions}")
}