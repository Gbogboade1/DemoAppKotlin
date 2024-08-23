package app.push.demoappkotlin


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import app.push.demoappkotlin.ui.theme.DemoAppKotlinTheme
import app.push.demoappkotlin.view.ChatListView
import app.push.demoappkotlin.view.LogInView
import app.push.demoappkotlin.viewmodels.PushViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: PushViewModel = viewModel()


            val user = viewModel.pushUser.component1()
            DemoAppKotlinTheme {
                if (user == null) {
                    LogInView()
                } else {
                    ChatListView()
                }
            }
        }
    }
}
