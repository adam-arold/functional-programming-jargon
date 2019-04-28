package com.example

import com.example.util.print

fun main() {

    (1 + (2 + 3) == (1 + 2) + 3).print() // true

    (listOf(1, 2) + listOf(3, 4)).print() // [1, 2, 3, 4]

    (listOf(1, 2) + listOf()).print() // [1, 2]
}
