package tz.co.asoft.persist

import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.json.Json
import kotlin.test.Test
import tz.co.asoft.persist.result.Result
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ResultTest {
    @Test
    fun should_not_respond_with_null() {
        val res = Result.success(listOf(1))
        assertNotNull(res.respond())
    }

    @Test
    fun should_serialize_stuff() {
        val res1 = Result(1)
        val json = Json.stringify(Result.serializer(Int.serializer()), res1)
        println(json)
        val res2 = Json.parse(Result.serializer(Int.serializer()), json)
        assertEquals(res1.data, res2.data)
    }
}