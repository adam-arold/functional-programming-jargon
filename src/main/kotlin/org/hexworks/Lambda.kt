package org.hexworks

import org.hexworks.util.print

fun main() {

    { a: Int -> a + 1 }

    listOf(1, 2).map { a: Int -> a + 1 }.print() // [2, 3]

    val add1 = { a: Int -> a + 1 }

    listOf(1, 2).map(add1).print()
}
