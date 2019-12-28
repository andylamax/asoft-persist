package tz.co.asoft.persist.repo

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import tz.co.asoft.persist.dao.IDao

interface IRepo<T : Any> : IDao<T> {

    fun allFlow(): Flow<List<T>> = flow {
        all()?.let { emit(it) }
    }

    fun loadFlowing(id: String): Flow<T> = flow {
        load(id)?.let { emit(it) }
    }

    fun loadFlowing(id: Number): Flow<T> = loadFlowing(id.toString())

    fun loadFlowing(ids: List<Any>): Flow<List<T>> = flow {
        emit(load(ids))
    }
}