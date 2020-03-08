package tz.co.asoft.persist

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import tz.co.asoft.persist.dao.Cache
import tz.co.asoft.persist.dao.IDao
import tz.co.asoft.persist.model.Entity
import tz.co.asoft.persist.repo.CachedRepo
import tz.co.asoft.test.asyncTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CachedRepoTest {
    class Person : Entity {
        override var uid = ""
    }

    class HeavyDao : Cache<Person>()

    val personCache = Cache<Person>()
    val personDao = HeavyDao()

    val repo = CachedRepo(personCache, personDao)

    @Test
    fun should_take_too_long_to_create() = asyncTest {
        println("Beginning test")
        val p1 = Person().apply { uid = "p1" }
        println("Creating person")
        repo.create(p1)
        println("Created person. Loading all people")
        assertEquals(1, repo.cache.all().size, "Cached size")
        assertEquals(1, repo.dao.all().size, "Dao size")
        assertEquals(1, repo.all().size)
        println("Loaded everyone.Loading again")
        assertEquals(1, repo.all().size)

        var flowCount = 0
        repo.allFlow().collect {
            println("Flow count: ${++flowCount}, People: ${it.size}")

            assertEquals(1, it.size)
        }
    }
}