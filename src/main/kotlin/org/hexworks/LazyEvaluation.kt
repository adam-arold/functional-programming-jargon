package org.hexworks

import org.hexworks.util.print
import kotlin.random.Random

fun main() {

    val rand = {
        sequence {
            while (true) {
                yield(Random.nextInt())
            }
        }
    }


    rand().take(10).toList().print()
}
