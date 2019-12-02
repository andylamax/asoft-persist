package tz.co.asoft.persist.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tz.co.asoft.persist.dao.IDao
import tz.co.asoft.persist.result.Result
import tz.co.asoft.rx.lifecycle.LiveData

interface IRepo<T : Any> : IDao<T> {
    override val liveData: LiveData<List<T>?> get() = LiveData(null)
    fun allFlow(): Flow<List<T>> = flow {
        all()?.let { emit(it) }
    }

    fun allFlowCatching(): Flow<Result<List<T>>> = flow {
        emit(allCatching())
    }

    fun loadFlowing(id: Any): Flow<T> = flow {
        load(id)?.let { emit(it) }
    }

    fun loadFlowCatching(id: Any): Flow<Result<T>> = flow {
        emit(loadCatching(id))
    }
}