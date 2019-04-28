package com.example

import com.example.util.compose
import com.example.util.print
import kotlin.math.roundToInt

fun main() {
    val floorAndToString = compose(Double::roundToInt) { it.toString() }

    floorAndToString(121.212121).print() // "121"
}
