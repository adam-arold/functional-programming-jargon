package org.hexworks

import org.hexworks.util.print

fun main() {

    fun <A, B, C> Function2<A, B, C>.partial(a: A): (B) -> C {
        return { b -> invoke(a, b) }
    }

    // Something to apply
    val add2 = { a: Int, b: Int ->
        a + b
    }

    // Partially applying `2` and `3` to `add3` gives you a one-argument function
    val fivePlus = add2.partial(5)

    fivePlus(4).print() // 9
}
