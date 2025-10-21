package com.example.geoquiz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.geoquiz.ui.theme.GeoQuizTheme

data class Question(val text: String, val answer: Boolean)

val questions = listOf(
    Question("Canberra is the capital of Australia.", true),
    Question("The Pacific Ocean is larger than the Atlantic Ocean.", true),
    Question("The Suez Canal connects the Red Sea and the Indian Ocean.", false),
    Question("The source of the Nile River is in Egypt.", false),
    Question("The Amazon River is the longest river in the Americas.", true),
    Question("Lake Baikal is the world's oldest and deepest freshwater lake.", true)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GeoQuizTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GeoQuizApp()
                }
            }
        }
    }
}

@Composable
fun GeoQuizApp() {

    var currentQuestionIndex by remember { mutableStateOf(0) }
    var answered by remember { mutableStateOf(false) }
    var userAnswers by remember { mutableStateOf(List(questions.size) { false }) }

    val currentQuestion = questions[currentQuestionIndex]
    val isLastQuestion = currentQuestionIndex == questions.size - 1

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "GeoQuiz",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Text(
            text = "Question ${currentQuestionIndex + 1}/${questions.size}",
            fontSize = 18.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = currentQuestion.text,
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp)
        )

        Text(
            text = "GeoQuiz App",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text("True", fontSize = 18.sp)
            }

            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text("False", fontSize = 18.sp)
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (currentQuestionIndex < questions.size - 1) {
                    currentQuestionIndex++
                }
            },
            modifier = Modifier.fillMaxWidth(0.8f)
        ) {
            Text("Next Question", fontSize = 18.sp)
        }
        if (!answered) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        checkAnswer(true, currentQuestion.answer, userAnswers, currentQuestionIndex) { newAnswers ->
                            userAnswers = newAnswers
                        }
                        answered = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text("True", fontSize = 18.sp)
                }

                Button(
                    onClick = {
                        checkAnswer(false, currentQuestion.answer, userAnswers, currentQuestionIndex) { newAnswers ->
                            userAnswers = newAnswers
                        }
                        answered = true
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("False", fontSize = 18.sp)
                }
            }
        } else {
            Text(
                text = if (userAnswers[currentQuestionIndex]) "Your answer: True" else "Your answer: False",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        if (answered && !isLastQuestion) {
            Button(
                onClick = {
                    currentQuestionIndex++
                    answered = false
                },
                enabled = answered && !isLastQuestion,
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Next Question", fontSize = 18.sp)
            }
        }
    }
}

fun checkAnswer(
    userAnswer: Boolean,
    correctAnswer: Boolean,
    currentAnswers: List<Boolean>,
    currentIndex: Int,
    onAnswersUpdated: (List<Boolean>) -> Unit
) {
    val newAnswers = currentAnswers.toMutableList()
    newAnswers[currentIndex] = userAnswer
    onAnswersUpdated(newAnswers)
}

@Composable
fun GeoQuizTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = androidx.compose.ui.graphics.Color(0xFF2196F3),
            onPrimary = androidx.compose.ui.graphics.Color.White,
            surface = androidx.compose.ui.graphics.Color.White,
            onSurface = androidx.compose.ui.graphics.Color.Black
        ),
        content = content
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GeoQuizAppPreview() {
    GeoQuizTheme {
        GeoQuizApp()
    }
}