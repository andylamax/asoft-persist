package tz.co.asoft.persist.repo

import tz.co.asoft.persist.dao.IDao
import tz.co.asoft.persist.result.catching

open class Repo<T : Any>(private val dao: IDao<T>) : IRepo<T>, IDao<T> by dao