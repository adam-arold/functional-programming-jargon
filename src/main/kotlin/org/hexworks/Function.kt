package org.hexworks

import org.hexworks.util.print

fun main() {

    // times2 :: Number -> Number
    val times2 = { n: Int -> n * 2 }

    listOf(1, 2, 3).map(times2).print() // [2, 4, 6]
}