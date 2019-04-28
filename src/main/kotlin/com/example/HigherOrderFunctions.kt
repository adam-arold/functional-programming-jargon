package com.example

import com.example.util.print
import kotlin.reflect.KClass
import kotlin.reflect.full.isSuperclassOf

fun main() {
    val filter = { predicate: (Any) -> Boolean, xs: Array<out Any> ->
        xs.filter(predicate)
    }

    val isA = { type: KClass<out Any> ->
        { x: Any ->
            type.isSuperclassOf(x::class)
        }
    }

    filter(isA(Int::class), arrayOf(0, "1", 2)).print() // [0, 2]
}
