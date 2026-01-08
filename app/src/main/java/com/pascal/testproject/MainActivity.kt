package com.pascal.testproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.pascal.testproject.ui.navigation.NavGraph
import com.pascal.testproject.ui.theme.TestProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestProjectTheme {
                NavGraph()
            }
        }
    }
}