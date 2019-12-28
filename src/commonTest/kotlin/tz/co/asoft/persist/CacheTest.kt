package tz.co.asoft.persist

import tz.co.asoft.persist.dao.Cache
import tz.co.asoft.persist.model.Entity
import tz.co.asoft.test.asyncTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CacheTest {
    class Person : Entity {
        override var uid = ""
    }

    val cache = Cache<Person>()

    @Test
    fun should_save_to_cache() = asyncTest {
        val pToSave = Person().apply { uid = "1" }
        cache.create(pToSave)
        assertEquals(1, cache.all().size)

        val loadedPerson = cache.load(1)
        assertEquals(pToSave, loadedPerson)

        assertEquals(pToSave.uid, loadedPerson?.uid)
    }
}