package tz.co.asoft.persist.repo

import tz.co.asoft.persist.dao.IMultiDao

interface IMultiRepo<T : Any> : IRepo<T>, IMultiDao<T>