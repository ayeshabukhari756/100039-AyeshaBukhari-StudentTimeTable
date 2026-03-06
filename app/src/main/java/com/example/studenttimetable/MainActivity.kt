package com.example.studenttimetable

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                TimeTableApp()
            }
        }
    }
}

@Composable
fun TimeTableApp() {
    var selectedDay by remember { mutableStateOf("Monday") }

    // This allows you to "Select subjects of your own will"
    val customSchedule = remember {
        mutableStateMapOf(
            "Monday" to "09:00 - Math",
            "Tuesday" to "10:00 - Science",
            "Wednesday" to "11:00 - History",
            "Thursday" to "09:00 - English",
            "Friday" to "10:00 - Sports"
        )
    }

    var currentInput by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "My Timetable",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        // THE MAIN CARD (Fills the empty screen space)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // This pushes the buttons to the bottom
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Day: $selectedDay",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 15.dp))
                Text(
                    text = customSchedule[selectedDay] ?: "No classes set",
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 22.sp,
                    lineHeight = 32.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // THE INPUT BOX (To change subjects live)
        OutlinedTextField(
            value = currentInput,
            onValueChange = { currentInput = it },
            label = { Text("Update subject for $selectedDay") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = {
                if (currentInput.isNotEmpty()) {
                    customSchedule[selectedDay] = currentInput
                    currentInput = ""
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Text("Save Subject", fontWeight = FontWeight.Bold)
        }

        Spacer(modifier = Modifier.height(10.dp))

        // BOTTOM NAVIGATION (Day Selectors)
        Text("Select Day:", style = MaterialTheme.typography.labelLarge)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOf("Mon", "Tue", "Wed", "Thu", "Fri").forEach { day ->
                val fullDay = when(day) {
                    "Mon" -> "Monday"
                    "Tue" -> "Tuesday"
                    "Wed" -> "Wednesday"
                    "Thu" -> "Thursday"
                    else -> "Friday"
                }

                TextButton(
                    onClick = { selectedDay = fullDay }
                ) {
                    Text(
                        text = day,
                        color = if (selectedDay == fullDay) MaterialTheme.colorScheme.primary else Color.Gray,
                        fontWeight = if (selectedDay == fullDay) FontWeight.ExtraBold else FontWeight.Normal,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}