package app.push.demoappkotlin.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import app.push.demoappkotlin.repository.PushRepository
import app.push.demoappkotlin.repository.Result
import app.push.demoappkotlin.utils.getNewSinger
import push.kotlin.sdk.PrivateKeySigner
import push.kotlin.sdk.PushAPI.PushAPI
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class PushViewModel() : ViewModel() {
    private var executorService: ExecutorService = Executors.newFixedThreadPool(4)
    private var pushRepository: PushRepository = PushRepository(executorService)

    var pushUser = mutableStateOf<PushAPI?>(null)
    val userAddress = mutableStateOf<String?>(null)

    val error = mutableStateOf<String?>(null)
    fun loadPush() {
        val (newAddress, signer) = getNewSinger()
        userAddress.value = newAddress

        pushRepository.onInitializePush(signer) { result ->
            when (result) {
                is Result.Success -> {
                    pushUser.value = result.data
                    error.value = null
                }

                is Result.Error -> {
                    error.value = "Error loading push"
                }

                else -> {
                    error.value = "Unexpected result"

                }
            }
        }

    }

    fun loadPush(privateKey: String) {

        val signer = PrivateKeySigner(privateKey)
        val address = signer.getAddress().getOrThrow()

        userAddress.value = address

        pushRepository.onInitializePush(signer) { result ->
            when (result) {
                is Result.Success -> {
                    pushUser.value = result.data

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

