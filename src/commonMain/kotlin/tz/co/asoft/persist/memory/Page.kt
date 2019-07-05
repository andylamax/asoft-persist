package tz.co.asoft.persist.memory

class Page<T>(val max: Int = 100) {
    var prevUrl: String? = null
    var nextUrl: String? = null
    var selfUrl: String = ""
    var pageNumber = -1
    var totalPages = -1
    var total = -1
    var prev: Page<T>? = null
    var next: Page<T>? = null
    var data = mutableListOf<T>()
    val isFull get() = data.size >= max
    val isNotFull get() = !isFull
    val size get() = data.size

    fun toSelfPageAlone() = Page<T>(max).also {
        it.prevUrl = prevUrl
        it.selfUrl = selfUrl
        it.nextUrl = nextUrl
        it.pageNumber = pageNumber
        it.data = data
        it.totalPages = totalPages
        it.total = total
    }
}