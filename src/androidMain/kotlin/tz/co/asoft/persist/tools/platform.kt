package tz.co.asoft.persist.tools

import kotlin.reflect.KClass

actual fun Any.isInstanceOf(clazz: KClass<*>): Boolean = clazz.java.isInstance(this)