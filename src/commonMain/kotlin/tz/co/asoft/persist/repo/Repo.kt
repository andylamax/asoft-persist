package tz.co.asoft.persist.repo

import tz.co.asoft.persist.dao.IDao
import tz.co.asoft.persist.result.catching

open class Repo<T : Any>(private val dao: IDao<T>) : IRepo<T> {

    override suspend fun create(list: List<T>) = dao.create(list)

    override suspend fun create(t: T) = dao.create(t)

    override suspend fun createCatching(list: List<T>) = catching { create(list) }

    override suspend fun createCatching(t: T) = catching { create(t) }

    override suspend fun edit(list: List<T>) = dao.edit(list)

    override suspend fun edit(t: T) = dao.edit(t)

    override suspend fun editCatching(list: List<T>) = catching { edit(list) }

    override suspend fun editCatching(t: T) = catching { edit(t) }

    override suspend fun delete(list: List<T>) = dao.delete(list)

    override suspend fun delete(t: T) = dao.delete(t)

    override suspend fun deleteCatching(list: List<T>) = catching { delete(list) }

    override suspend fun deleteCatching(t: T) = catching { delete(t) }

    override suspend fun load(ids: List<Any>) = dao.load(ids)

    override suspend fun load(id: String) = dao.load(id)

    override suspend fun loadCatching(ids: List<Any>) = catching { load(ids) }

    override suspend fun loadCatching(id: String) = catching { load(id) }

    override suspend fun all() = dao.all()

    override suspend fun allCatching() = dao.allCatching()

    override suspend fun loadAll() = all()

    override suspend fun loadAllCatching() = catching { loadAll() }
}