package org.hexworks

import org.hexworks.util.print
import kotlin.reflect.jvm.reflect

fun main() {
    val sum = { x: Int, y: Int ->
        x + y
    }

    val arity = sum.reflect()!!.parameters.size

    arity.print() // 2

    // The arity of sum is 2
}
