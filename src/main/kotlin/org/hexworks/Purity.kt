package org.hexworks

import org.hexworks.util.print

fun main() {
    pureGreet()
    impureGreet0()
    impureGreet1()
}

fun pureGreet() {
    val greet = { name: String ->
        "Hi, $name"
    }

    greet("Brianne").print() // "Hi, Brianne"
}

fun impureGreet0() {
    val name = "Brianne"

    val greet = {
        "Hi, $name"
    }

    greet().print() // "Hi, Brianne"
}

fun impureGreet1() {
    var greeting = ""

    val greet = { name: String ->
        greeting = "Hi, $name"
    }

    greet("Brianne")
    greeting.print() // "Hi, Brianne"
}
