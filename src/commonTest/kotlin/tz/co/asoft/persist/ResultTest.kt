package tz.co.asoft.persist

import kotlin.test.Test
import tz.co.asoft.persist.result.Result
import kotlin.test.assertNotNull

class ResultTest {
    @Test
    fun should_not_respond_with_null() {
        val res = Result.success(listOf(1))
        assertNotNull(res.respond())
    }
}