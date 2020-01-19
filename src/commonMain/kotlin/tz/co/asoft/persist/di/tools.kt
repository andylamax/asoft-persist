package tz.co.asoft.persist.di

import kotlinx.atomicfu.AtomicRef

//
//fun <T> AtomicRef<MutableList<T>>.add(t: T) {
//    value = mutableListOf<T>().apply {
//        addAll(value)
//        add(t)
//    }
//}

fun <K, V> AtomicRef<MutableMap<K, V>>.add(k: K, v: V) {
    value = mutableMapOf<K, V>().apply {
        putAll(value)
        put(k, v)
    }
}

fun <K, V> AtomicRef<MutableMap<K, V>>.getOrPut(k: K, block: () -> V): V {
    if (value.containsKey(k)) {
        return value[k] ?: block()
    }
    add(k, block())
    return getOrPut(k, block)
}