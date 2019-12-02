package tz.co.asoft.persist.tools

import kotlin.reflect.KClass

expect fun Any.isInstanceOf(clazz: KClass<*>): Boolean