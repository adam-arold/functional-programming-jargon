package org.hexworks

import org.hexworks.util.ap
import org.hexworks.util.print

fun main() {

    listOf({a: Int -> a + 1}).ap(listOf(1)).print() // [2]

    // Lists that you want to combine
    val arg0 = listOf(1, 3)
    val arg1 = listOf(4, 5)

    // combining function - must be curried for this to work

    val add = { x: Int -> { y: Int -> x + y } }

    val partiallyAppliedAdds = listOf(add).ap(arg0) // [(y) -> 1 + y, (y) -> 3 + y]

    partiallyAppliedAdds.ap(arg1).print() // [5, 6, 7, 8]
}
