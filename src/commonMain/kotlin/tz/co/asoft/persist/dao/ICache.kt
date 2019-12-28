package tz.co.asoft.persist.dao

import tz.co.asoft.persist.model.Entity

interface ICache<T : Entity> : IDao<T> {
    var data: MutableMap<String, T>?
    override suspend fun create(list: List<T>): List<T> {
        if (data == null) {
            data = mutableMapOf()
        }
        data?.putAll(list.associateBy { it.uid })
        return list
    }

    override suspend fun create(t: T) = create(listOf(t)).first()

    override suspend fun edit(t: T) = create(t)

    override suspend fun wipe(t: T) = data?.remove(t.uid) ?: t

    override suspend fun load(id: String) = data?.get(id)

    override suspend fun load(ids: List<Any>): List<T> = ids.mapNotNull { data?.get(it) }

    override suspend fun all(): List<T> = data?.values?.toList() ?: listOf()
}