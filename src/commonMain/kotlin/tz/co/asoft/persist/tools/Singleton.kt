package tz.co.asoft.persist.tools

abstract class Singleton<in In, out Out>(private val builder: (In) -> Out) {
    private var instance: Out? = null
    fun getInstance(config: In) = instance ?: builder(config).also { instance = it }
}