package tz.co.asoft.persist.repo

import tz.co.asoft.persist.dao.PaginatedDao

abstract class PaginatedRepo<T>(private val dao: PaginatedDao<T>) : Repo<T>(dao){
    open suspend fun getMemory() = dao.getMemory()
    open suspend fun loadPage(pageNumber: Int) = dao.loadPage(pageNumber)
}