package org.hexworks

import org.hexworks.util.compose
import org.hexworks.util.print
import kotlin.math.roundToInt

fun main() {
    val floorAndToString = compose(Double::roundToInt) { it.toString() }

    floorAndToString(121.212121).print() // "121"
}
