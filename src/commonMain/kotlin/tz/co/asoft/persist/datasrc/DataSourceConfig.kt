package tz.co.asoft.persist.datasrc

open class DataSourceConfig(val url: String = "") {
    var username = ""
    var password = ""
    var apiKey = ""
    var headers = mutableMapOf<String, String>()
}