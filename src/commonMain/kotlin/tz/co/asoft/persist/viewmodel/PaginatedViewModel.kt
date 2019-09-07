package tz.co.asoft.persist.viewmodel

import tz.co.asoft.persist.repo.PaginatedRepo

abstract class PaginatedViewModel<T>(private val repo: PaginatedRepo<T>) : ViewModel<T>(repo) {
    open suspend fun getMemory() = repo.getMemory()
    open suspend fun loadPage(pageNumber: Int) = repo.loadPage(pageNumber)
}