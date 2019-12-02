package tz.co.asoft.persist.repo

import tz.co.asoft.persist.dao.PaginatedDao
import tz.co.asoft.persist.memory.Page

open class PaginatedRepo<T : Any>(private val dao: PaginatedDao<T>) : Repo<T>(dao) {

    open val pages = mutableMapOf<String, Page<T>>()

    @Deprecated(message = "Doesn't work well with more than one client", replaceWith = ReplaceWith("User all instead"))
    open suspend fun getMemory() = dao.getMemory()

    open suspend fun loadPage(pageNumber: Int) = dao.loadPage(pageNumber)
}