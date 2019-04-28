package com.example

import com.example.util.print

fun main() {

    val contract = { input: Any ->
        if (input is Int) {
            input
        } else throw RuntimeException(
                "Contract violated: expected an Int")
    }

    val addOne = { num: Any ->
        contract(num) + 1
    }

    addOne(2).print() // 3

    addOne("some string").print() // Contract violated: expected an Int
}
