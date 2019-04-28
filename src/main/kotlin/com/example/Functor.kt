package com.example

import com.example.util.print

fun main() {

    listOf(1, 2, 3).map { it }.print() // [1, 2, 3]

    val f = { x: Int -> x + 1 }
    val g = { x: Int -> x * 2 }

    listOf(1, 2, 3).map { f(g(it)) }.print() // [3, 5, 7]
    listOf(1, 2, 3).map(g).map(f).print() // [3, 5, 7]
}
