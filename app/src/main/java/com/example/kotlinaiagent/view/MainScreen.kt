package com.example.kotlinaiagent.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.kotlinaiagent.viewmodel.QueryAndroidViewModel

@Composable
fun MainScreen(innerPadding: PaddingValues, modifier: Modifier = Modifier) {
    val viewModel = viewModel<QueryAndroidViewModel>()
    var textInput by remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = textInput,
            onValueChange = { newText ->
                textInput = newText
            },
            label = { Text("Enter your query") },
            placeholder = { Text("e.g., What is activity?") },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            trailingIcon = {
                if (textInput.isNotEmpty()) {
                    IconButton(onClick = { viewModel.query(textInput) }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    }
                }
            }
        )
        if (!viewModel.queryState.value.isLoading) {
            Text(
                text = viewModel.queryState.value.data.orEmpty(),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(1f)
                    .align(Alignment.CenterHorizontally)
                    .verticalScroll(
                        state = rememberScrollState(),
                        enabled = true
                    ),
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground
            )
        } else {
            CircularProgressIndicator(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.CenterHorizontally)
                    .height(16.dp),
                color = MaterialTheme.colorScheme.primary,
                strokeWidth = 4.dp
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen(innerPadding = PaddingValues())
}