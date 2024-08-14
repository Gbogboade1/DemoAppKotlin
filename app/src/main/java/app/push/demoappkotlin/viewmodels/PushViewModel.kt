package app.push.demoappkotlin.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.push.demoappkotlin.repository.PushRepository
import app.push.demoappkotlin.repository.Result
import app.push.demoappkotlin.utils.getNewSinger
import push.kotlin.sdk.PushAPI.PushAPI
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PushViewModel() : ViewModel() {
    var executorService: ExecutorService = Executors.newFixedThreadPool(4)
    var pushRepository: PushRepository

    init {
        pushRepository = PushRepository(executorService)
    }

    var pushUser = mutableStateOf<PushAPI?>(null)
    val address = mutableStateOf<String?>(null)

    val error = mutableStateOf<String?>(null)
    fun loadPush() {
        val (newAddress, signer) = getNewSinger()
        address.value = newAddress

        pushRepository.onInitializePush(signer) { result ->
            when (result) {
                is Result.Success -> {
                    pushUser.value = result.data
                    println("....done")
                }

                is Result.Error -> {
                    println("error occurred...")
                    error.value = "Error loading push"
                    // Show error in UI
                }

                else -> {
                    println("Unexpected result")
                }
            }
        }

    }

}

