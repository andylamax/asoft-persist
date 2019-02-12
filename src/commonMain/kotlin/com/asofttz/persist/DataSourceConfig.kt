package com.asofttz.persist

open class DataSourceConfig(val url: String = "") {
    var username = ""
    var password = ""
    var headers = mutableMapOf<String, String>()
}