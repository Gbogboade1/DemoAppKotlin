package app.push.demoappkotlin.utils


import push.kotlin.sdk.PrivateKeySigner
import push.kotlin.sdk.Signer


fun getRandomEthPrivateKey(): String {
    val length = 64
    val letters = "abcdef0123456789"
    return (0 until length).map { letters.random() }.joinToString("")
}

fun getNewSinger():Pair<String, Signer>{
    val privateKey = getRandomEthPrivateKey()
    println("privateKey: $privateKey ")
    val signer = PrivateKeySigner(privateKey)
    val address = signer.getAddress().getOrThrow()

    return Pair(address, signer)
}