package com.asofttz.persist.memory

open class Memory<T>(val perPage: Int = 100) {
    var pages = mutableMapOf<Int, Page<T>>().apply {
        put(0, newFirstPage())
    }

    var total = -1

    val noOfPages get() = pages.size

    val size get() = pages.values.map { it.size }.reduce { acc, i -> acc + i }

    val firstPage get() = pages[0]!!

    val lastPage get() = pages.values.sortedBy { it.pageNumber }.last()

    private fun newFirstPage() = Page<T>(perPage).apply {
        pageNumber = 0
    }

    private fun createNewPage(): Page<T> {
        val newPage = Page<T>(perPage).apply {
            pageNumber = lastPage.pageNumber + 1
        }
        lastPage.next = newPage
        newPage.prev = lastPage
        pages[newPage.pageNumber] = newPage
        return newPage
    }

    private fun sort() {
        val all = getAll()
        pages = mutableMapOf<Int, Page<T>>().apply {
            put(0, newFirstPage())
        }
        addAll(all)
    }

    fun addPage(page: Page<T>) {
        pages[page.pageNumber] = page
        total = page.total
        page.prev = pages.getOrElse(page.pageNumber - 1) { null }
        page.next = pages.getOrElse(page.pageNumber + 1) { null }
    }

    fun remove(t: T) {
        var page: Page<T>? = null
        pages.values.forEach {
            if (it.data.contains(t)) {
                page = it
                return@forEach
            }
        }
        page?.let {
            it.data.remove(t)
            sort()
        }
    }

    fun add(t: T) {
        if (lastPage.isNotFull) {
            lastPage.data.add(t)
        } else {
            val newPage = createNewPage()
            newPage.data.add(t)
        }
    }

    fun addAll(data: Collection<T>) = data.forEach {
        add(it)
    }

    fun getAll() = mutableListOf<T>().apply {
        pages.values.forEach { page ->
            addAll(page.data)
        }
    }

    fun map(perPage: Int) = Memory<T>(perPage).apply {
        addAll(this@Memory.getAll())
    }
}