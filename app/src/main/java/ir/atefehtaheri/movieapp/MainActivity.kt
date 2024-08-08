package ir.atefehtaheri.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import ir.atefehtaheri.movieapp.core.designsystem.theme.MovieAppTheme
import ir.atefehtaheri.movieapp.ui.MovieApp

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieAppTheme {
                MovieApp(windowSizeClass = calculateWindowSizeClass(this))
            }
        }
    }
}
