package org.hexworks

import org.hexworks.util.print


fun main() {
    val predicate = { a: Int ->
        a > 2
    }

    listOf(1, 2, 3, 4).filter(predicate).print() // [3, 4]
}
