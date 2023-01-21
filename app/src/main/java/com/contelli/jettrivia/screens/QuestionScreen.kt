package com.contelli.jettrivia.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionScreen(viewModel: QuestionViewModel = hiltViewModel()) {
    val questions = viewModel.data.value.data?.toMutableList()

    if (viewModel.data.value.loading == true) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
        val count = viewModel.count
        val choiceSelected = viewModel.choiceSelected
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = "Questions: ${count.value + 1}/${questions?.size}")
            Divider()
            Spacer(Modifier.padding(16.dp))
            questions?.get(count.value)?.let {
                Text(
                    text = it.question,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            if (questions != null) {
                LazyColumn(
                    Modifier
                        .selectableGroup()
                        .fillMaxWidth()
                ) {
                    items(questions[count.value].choices) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(selected = choiceSelected.value == it, onClick = {
                                choiceSelected.value = it
                            }, enabled = choiceSelected.value == "")
                            Text(
                                text = it,
                                color = verifyColor(
                                    choiceSelected.value,
                                    questions[count.value].answer,
                                    it
                                )
                            )
                        }
                    }
                }
            }
            Spacer(Modifier.padding(16.dp))
            Button(onClick = {
                count.value++
                choiceSelected.value = ""
            }, modifier = Modifier.fillMaxWidth(), enabled = choiceSelected.value.trim() != "") {
                Text(text = "Next")
            }
        }
    }
}

fun verifyColor(choiceSelected: String, answer: String, it: String): Color {
    if (choiceSelected == answer && it == choiceSelected) {
        return Color(
            0xFF4CAF50
        )
    } else if (choiceSelected != "" && choiceSelected != answer && it == choiceSelected) {
        return Color.Red
    }
    return Color.Black
}