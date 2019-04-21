package com.asofttz.persist.memory

import com.asofttz.rx.Subscriber
import kotlin.js.JsName
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class `Given an observable memory of strings` {
    val mem = ObservableMemory(Memory<String>().apply {
        addAll(listOf("Andy", "Lamax", "The", "Kop", "And", "Pop"))
    })

    lateinit var subscriber: Subscriber<Memory<String>>

    @BeforeTest
    fun prepare() {
        subscriber = mem.subscribe {
            println("Total: ${it.size}")
        }
    }

    @Test
    @JsName("test1")
    fun `Should add an entry and the subscriber should print the current total now`() {
        mem.add("Prince")
    }

    @Test
    @JsName("test2")
    fun `Should remove an entry and the subscriber should print the current total now`() {
        mem.remove("Andy")
    }

    @Test
    @JsName("test3")
    fun `Should add multiple entries and the subscriber should print the current total now`() {
        mem.addAll("Prince Of Physics".split(" "))
    }

    @AfterTest
    fun cancel() {
        subscriber.cancel()
    }
}