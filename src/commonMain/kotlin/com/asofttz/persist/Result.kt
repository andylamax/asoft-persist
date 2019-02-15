package com.asofttz.persist

sealed class Result<T>(var value: T? = null) {
    var status = true
    var reason = ""


    class Success<T>(t: T) : Result<T>(t)

    class Failure<T>(reason: String) : Result<T>() {
        init {
            status = false
            this.reason = reason
        }
    }

    companion object {
        fun <T> success(t: T) = Success(t)
        fun failure(reason: String = "Unknown Reason") = Failure<Unit>(reason)
    }
}