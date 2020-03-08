package tz.co.asoft.persist.repo

import tz.co.asoft.persist.dao.IDao

interface IRepo<T : Any> : IDao<T> {
    @Deprecated("use allFlowing")
    fun allFlow() = allFlowing()
}