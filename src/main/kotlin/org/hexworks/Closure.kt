package org.hexworks

import org.hexworks.util.print

fun main() {

    val addTo = { x: Int ->
        { y: Int ->
            x + y
        }
    }

    val addToFive = addTo(5)

    addToFive(3).print() // 8
}
