package app.push.demoappkotlin.repository

import push.kotlin.sdk.ENV
import push.kotlin.sdk.PushAPI.PushAPI
import push.kotlin.sdk.Signer
import java.util.concurrent.Executor

abstract class Result<T> private constructor() {
    class Success<T>(var data: T) : Result<T>()

    class Error<T>(var exception: Exception) : Result<T>()
}

fun interface RepositoryCallback<T> {
    fun onComplete(result: Result<T>?)
}

class PushRepository(private val executor: Executor) {


    fun onInitializePush(signer: Signer, callback: RepositoryCallback<PushAPI>) {

        executor.execute {
            try {
                val result = makeSynchronousOnInitializePush(signer);

                callback.onComplete(result);
            } catch (e: Exception) {
                val error = Result.Error<PushAPI>(e)
                callback.onComplete(error);
            }
        }
    }

    private fun makeSynchronousOnInitializePush(signer: Signer): Result<PushAPI> {
        try {
            val result = PushAPI.initialize(
                signer,
                PushAPI.PushAPIInitializeOptions(env = ENV.staging)
            )
            return Result.Success<PushAPI>(result)
        } catch (e: Exception) {
            return Result.Error<PushAPI>(e)
        }
    }


}