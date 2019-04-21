package com.asofttz.persist.memory

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class `Given a set of data in a memory of strings` {

    @Test
    @JsName("test1")
    fun `should have one page`() {
        val mem = Memory<String>().apply {
            addAll(listOf("Andy", "Lamax", "The", "Kop", "And", "Pop"))
        }
        assertEquals(1, mem.pages.size)
    }

    @Test
    @JsName("test2")
    fun `should have 3 pages`() {
        val mem = Memory<String>(2).apply {
            addAll(listOf("Andy", "Lamax", "The", "Kop", "And", "Pop"))
        }
        printPages(mem.firstPage)
        assertEquals(3, mem.noOfPages)
    }

    @Test
    @JsName("test3")
    fun `should delete 2 entities and have 2 pages`() {
        val mem = Memory<String>(2).apply {
            addAll(listOf("Andy", "Lamax", "The", "Kop", "And", "Pop"))
        }
        mem.remove("Andy")
        mem.remove("Lamax")
        assertEquals(2, mem.noOfPages)
        assertEquals(4, mem.size)
    }


    private fun <T> printPages(page: Page<T>) {
        println(page.data)
        page.next?.let {
            println("next: ")
            printPages(it)
        }
    }

    @Test
    @JsName("test4")
    fun `should print all the pages`() {
        val mem = Memory<String>(2).apply {
            addAll(listOf("Andy", "Lamax", "The", "Kop", "And", "Pop"))
        }
        println("Printing pages")
        printPages(mem.firstPage)
    }

    @Test
    @JsName("test5")
    fun `should map from 2 entries per page to 3 entries per page`() {
        val mem = Memory<String>(2).apply {
            addAll(listOf("Andy", "Lamax", "The", "Kop", "And", "Pop"))
        }.map(3)
        assertEquals(2, mem.pages.size)
        assertEquals(6, mem.size)
        assertEquals(3, mem.firstPage.max)
    }
}