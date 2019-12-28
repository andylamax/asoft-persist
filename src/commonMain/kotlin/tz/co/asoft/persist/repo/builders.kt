package tz.co.asoft.persist.repo

import tz.co.asoft.persist.dao.IDao
import tz.co.asoft.persist.dao.cache
import tz.co.asoft.persist.di.repo
import tz.co.asoft.persist.model.Entity

inline fun <reified T : Entity> CachedRepo(dao: IDao<T>) = repo { CachedRepo(cache(), dao) }

inline fun <reified T : Entity> TwinRepo(dao: IDao<T>): ITwinRepo<T> = CachedRepo(dao)