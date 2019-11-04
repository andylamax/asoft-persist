package tz.co.asoft.persist

import kotlinx.coroutines.delay
import tz.co.asoft.persist.dao.Dao
import tz.co.asoft.persist.di.onlyDao
import tz.co.asoft.persist.di.onlyRepo
import tz.co.asoft.persist.repo.Repo
import tz.co.asoft.persist.tools.Cause
import tz.co.asoft.persist.viewmodel.ViewModel
import tz.co.asoft.test.asyncTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class DaoTest {
    private val list = mutableListOf<Int>()

    data class Fruit(val type: String, val no: Int)

    class TheDao : Dao<Fruit>() {
        val q = 9
        override suspend fun create(list: List<Fruit>): List<Fruit>? {
            println("Adding Mango")
            return super.create(list)
        }

        override suspend fun all(): List<Fruit>? {
            delay(1000)
            throw Cause("Error for fun")
        }
    }

    private val dao = onlyDao { TheDao() }
    private val repo = onlyRepo { Repo(dao) }
    private val vm = ViewModel(repo)

    @Test
    fun should_put_1_in_the_list() = asyncTest {
        delay(1000)
        vm.create(listOf())
        delay(1000)
        vm.create(Fruit("Mango", 1))
        list.add(1)
        delay(1000)
        println("adding 1 to list")
        assertEquals(1, list.size)
    }

    @Test
    fun should_put_2_in_the_list() = asyncTest {
        delay(1000)
        list.add(2)
        delay(1500)
        assertEquals(1, list.size)
    }

    @Test
    fun error_handling() = asyncTest {
        dao.liveData.observeForever {
            println("It reached here")
        }
        delay(2000)
    }

    @Test
    fun error_handling_it_surely_fails() = asyncTest {
        assertFails {
            dao.getLiveData().observeForever {
                println("It Reached Here")
            }
        }
    }
}