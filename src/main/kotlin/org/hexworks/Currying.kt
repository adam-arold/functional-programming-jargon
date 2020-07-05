package org.hexworks

import org.hexworks.util.print

fun main() {

    fun <A, B, Z> ((A, B) -> Z).curry(): (A) -> (B) -> Z = { a: A ->
        { b: B ->
            invoke(a, b)
        }
    }

    val sum = { a: Int, b: Int ->
        a + b
    }

    val curriedSum = sum.curry()

    curriedSum(40)(2) // 42.

    val add2 = curriedSum(2) // (b: Int) -> 2 + b

    add2(10).print() // 12
}
