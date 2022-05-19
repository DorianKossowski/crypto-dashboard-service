package com.codibly_doko

import io.micronaut.runtime.Micronaut.build

fun main(args: Array<String>) {
    build()
        .args(*args)
        .packages("com.codibly_doko")
        .start()
}
