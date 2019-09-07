package tz.co.asoft.persist

import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import kotlin.test.assertTrue

class ViewModelSpek : Spek({
    val dao by memoized { DaoTest.TheDao() }
    describe("A very nice view model") {
        it("should work with no problem") {
            println("It worked")
            assertTrue { true }
        }
    }
})