package com.example.basicscodelab

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.basicscodelab.ui.theme.BasicsCodelabTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTheme {
                // A surface container using the 'background' color from the theme
                MyApp(Modifier.fillMaxSize())
            }
        }
    }
}
@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    onContinueClicked: ()->Unit
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Welcome to the Basics Codelab!")
        Button(
            modifier = Modifier.padding(vertical = 24.dp),
            onClick = onContinueClicked
        ) {
            Text(text = "Continue")
        }
    }
}

@Composable
fun Greeting(name: String) {
    var expended by rememberSaveable {
        mutableStateOf(false)
    }
    val extraPadding by animateDpAsState(
        if(expended) 48.dp else 0.dp,
        animationSpec = spring(
            dampingRatio=Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        Row(modifier = Modifier.padding(24.dp)) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            ) {
                Text(text = "Hello,")
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                if(expended){
                    Text(text = ("Composem ipsum color sit lazy, " +
                            "padding theme elit, sed do bouncy. ").repeat(4),)
                }
            }
            IconButton(
                onClick = {
                    expended = !expended
                }
            ) {
                Icon(
                    imageVector = if(expended) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                    contentDescription = if(expended) stringResource(id = R.string.show_less) else stringResource(
                        id =  R.string.show_more
                    )
                )
            }
        }
    }
}
@Composable
fun MyApp(
    modifier: Modifier = Modifier,
){
    var shouldShowBoarding by rememberSaveable {
        mutableStateOf(true)
    }
    Surface(modifier = modifier) {
        if (shouldShowBoarding)
            OnBoardingScreen(onContinueClicked = {
                shouldShowBoarding=false
            })
        else
            Greetings()
    }
}

@Composable
fun Greetings(
    modifier: Modifier = Modifier,
    names:List<String> = List(100){"$it"}
){
    LazyColumn{
        items(names){name->
            Greeting(name = name)
        }
    }
}
@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelabTheme {
        MyApp()
        //OnBoardingScreen()
    }
}