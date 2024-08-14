package app.push.demoappkotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import app.push.demoappkotlin.repository.PushRepository
import app.push.demoappkotlin.ui.theme.DemoAppKotlinTheme
import app.push.demoappkotlin.viewmodels.PushViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class MainActivity : ComponentActivity() {
    var executorService: ExecutorService = Executors.newFixedThreadPool(4)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DemoAppKotlinTheme {
                MainApp(executorService)
            }
        }
    }
}


@Composable
fun MainApp(executor: Executor) {
    val viewModel: PushViewModel = viewModel()

    val address = viewModel.address.component1()
    val push = viewModel.pushUser.component1()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column {
            Greeting(
                name = "$address",
                modifier = Modifier.padding(innerPadding)
            )
            Greeting(
                name = "${push == null}",
                modifier = Modifier.padding(innerPadding)
            )
            Button(onClick = {
                viewModel.loadPush()
            }) {
                Text(text = "Load")
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

    DemoAppKotlinTheme {
        Greeting("hello")
    }
}