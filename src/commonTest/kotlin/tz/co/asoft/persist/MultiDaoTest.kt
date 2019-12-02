package tz.co.asoft.persist

import tz.co.asoft.persist.dao.Cache
import tz.co.asoft.persist.dao.MultiDao
import tz.co.asoft.persist.model.Entity
import tz.co.asoft.persist.repo.Repo
import tz.co.asoft.test.asyncTest
import kotlin.test.Test
import kotlin.test.assertEquals

class MultiDaoTest {
    open class Person : Entity {
        override var uid = ""
        var name = "Fruit 1"
    }

    open class Employee : Person() {
        var position = "Teacher"
    }

    open class Teacher : Employee() {
        var course = "Science 1"
    }

    open class Student : Person() {
        var course = "OLevel"
    }

    val teachersDao = Cache<Teacher>()
    val employeesDao = Cache<Employee>()
    val studentsDao = Cache<Student>()

    @Test
    fun should_add_a_student() = asyncTest {
        val dao = MultiDao(
                Teacher::class to teachersDao,
                Employee::class to employeesDao,
                Student::class to studentsDao
        )

        val stud1 = Student().apply { uid = "s1" }
        val teach1 = Teacher().apply { uid = "t1" }
        val emp1 = Employee().apply { uid = "e1" }
        dao.create(stud1)
        assertEquals(1, dao.all()?.size)

        dao.create(teach1)
        assertEquals(2, dao.all()?.size)

        dao.create(emp1)
        assertEquals(3, dao.all()?.size)

        val loadedTeach1 = dao.load("t1")
        assertEquals(teach1, loadedTeach1)

        assertEquals(1, dao.loadAll(Teacher::class)?.size)
    }

    @Test
    fun links_well_with_a_repo() = asyncTest {
        val dao = MultiDao(
                Teacher::class to teachersDao,
                Employee::class to employeesDao,
                Student::class to studentsDao
        )
        val repo = Repo(dao)

        val stud1 = Student().apply { uid = "s1" }
        val teach1 = Teacher().apply { uid = "t1" }
        val emp1 = Employee().apply { uid = "e1" }
        repo.create(stud1)
        assertEquals(1, repo.all()?.size)

        repo.create(teach1)
        assertEquals(2, repo.all()?.size)

        repo.create(emp1)
        assertEquals(3, repo.all()?.size)

        val loadedTeach1 = repo.load("t1")
        assertEquals(teach1, loadedTeach1)
    }
}