package com.example

import com.example.util.print

fun main() {

    val map = { fn: (Int) -> Int ->
        { list: List<Int> ->
            list.map(fn)
        }
    }

    val add = { a: Int ->
        { b: Int ->
            a + b
        }
    }

    // Not points-free - `numbers` is an explicit argument
    val incrementAll = { numbers: List<Int> ->
        map(add(1))(numbers)
    }

    // Points-free - The list is an implicit argument
    val incrementAll2 = map(add(1))

    incrementAll(listOf(4, 3 ,2)).print()

    incrementAll2(listOf(4, 3 ,2)).print()
}
