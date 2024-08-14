package app.push.demoappkotlin


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import app.push.demoappkotlin.ui.theme.DemoAppKotlinTheme
import app.push.demoappkotlin.view.LogInView


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoAppKotlinTheme {
                LogInView()
            }
        }
    }
}
