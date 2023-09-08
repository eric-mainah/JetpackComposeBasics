package com.example.jetpackcomposebasics

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpackcomposebasics.ui.theme.JetpackComposeBasicsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Apply the JetpackComposeBasicsTheme to the entire app
            JetpackComposeBasicsTheme {
                // Call the Myapp composable function to define the app's UI
                Myapp(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

// Composable function for the main app content
@Composable
fun Myapp(modifier: Modifier = Modifier){
    // State to control whether onboarding should be shown
    var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

    // Display either the onboarding screen or greetings based on the state
    Surface(modifier){
        if(shouldShowOnboarding){
            OnboardingScreen(onContinueClicked = { shouldShowOnboarding = false })
        }else{
            Greetings()
        }
    }
}

// Composable function for displaying a list of greetings
@Composable
private fun Greetings(
    modifier: Modifier = Modifier,
    names: List<String> = List(20){"$it"}
) {
    // LazyColumn to efficiently display a list of items
    LazyColumn(modifier = modifier.padding(vertical = 5.dp)){
        items(items = names)  { name ->
            // Display individual greetings
            Greeting(name = name)
        }
    }
}

// Composable function for the onboarding screen
@Composable
fun OnboardingScreen(
    onContinueClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Column layout for vertical arrangement
    Column(
        modifier= modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Text and button for onboarding
        Text("Welcome to My Practice Lab!" )
        Button(
            modifier = Modifier.padding(vertical = 25.dp),
            onClick = onContinueClicked
        ) {
            Text("Continue")
        }
    }
}

// Preview function for the OnboardingScreen
@Preview(showBackground = true , widthDp = 350, heightDp = 350)
@Composable
fun OnboardingPreview() {
    JetpackComposeBasicsTheme {
        // Display the onboarding screen in a preview
        OnboardingScreen(onContinueClicked = {})
    }
}

// Composable function for displaying a single greeting
@Composable
private fun Greeting(name: String){
    // State to control the expansion of the greeting
    var expanded by remember { mutableStateOf(false) }

    // Animated padding to control the "Show More" and "Show Less" animation
    val extraPadding by animateDpAsState( if (expanded) 50.dp else 0.dp ,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ), label = ""
    )

    // Surface to create a card-like container
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 5.dp , horizontal = 10.dp)
    ) {
        // Row layout for horizontal arrangement
        Row(modifier = Modifier.padding(25.dp)) {
            // Column for the greeting text
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "Hello, ")
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    )
                )
            }
            // Button to expand/collapse the greeting
            ElevatedButton(
                onClick = { expanded = !expanded }
            ) {
                Text(if (expanded) "Show Less" else "Show More")
            }
        }
    }
}

// Preview function for the Greetings composable
@Preview(
    showBackground = true,
    widthDp = 350,
    uiMode = UI_MODE_NIGHT_YES,
    name = "Dark"
)
@Composable
fun GreetingPreview() {
    JetpackComposeBasicsTheme {
        // Display the Greetings composable in a preview
        Greetings()
    }
}

// Preview function for the Myapp composable
@Preview
@Composable
fun MyAppPreview() {
    JetpackComposeBasicsTheme {
        // Display the Myapp composable in a preview
        Myapp(Modifier.fillMaxSize())
    }
}
