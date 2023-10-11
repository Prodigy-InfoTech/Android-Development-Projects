package com.phoenix.note

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.phoenix.note.screen.NavGraphs
import com.phoenix.note.ui.theme.NoteTakingAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                Color.Transparent.toArgb(), Color.Transparent.toArgb()
            ),
            navigationBarStyle = SystemBarStyle.auto(
                Color.Transparent.toArgb(), Color.Transparent.toArgb()
            ),
        )
        setContent {
            NoteTakingAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    /**
                     * Use ComposeDestination library to implement navigation
                     * Use default root nav graph
                     * You can navigate to any composable annotated with @Destination
                     * HomeScree.kt is start route of root nav graph
                     */
                    DestinationsNavHost(navGraph = NavGraphs.root)
                }
            }
        }
    }
}
