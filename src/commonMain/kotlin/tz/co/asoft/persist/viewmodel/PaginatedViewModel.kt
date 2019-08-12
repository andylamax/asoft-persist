package tz.co.asoft.persist.viewmodel

import kotlinx.coroutines.Job
import tz.co.asoft.persist.repo.PaginatedRepo

abstract class PaginatedViewModel<T>(parentJob: Job? = null, private val repo: PaginatedRepo<T>) : ViewModel<T>(parentJob, repo) {
    open suspend fun getMemory() = repo.getMemory()
    open suspend fun loadPage(pageNumber: Int) = repo.loadPage(pageNumber)
}