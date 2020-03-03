package tz.co.asoft.persist.repo

import tz.co.asoft.persist.dao.IMultiDao

open class MultiRepo<T : Any>(private val dao: IMultiDao<T>) : IMultiRepo<T> {
    override val daos get() = dao.daos
}