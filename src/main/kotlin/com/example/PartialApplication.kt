package com.example

import com.example.util.print

fun main() {
    // Something to apply
    val add3 = { a: Int, b: Int, c: Int ->
        a + b + c
    }

    // Partially applying `2` and `3` to `add3` gives you a one-argument function
    val fivePlus = { x: Int ->
        add3(x, 2, 3)
    }

    fivePlus(4).print() // 9
}
