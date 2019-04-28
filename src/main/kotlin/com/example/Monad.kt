package com.example

import com.example.util.print

fun main() {

    // Usage
    listOf("cat,dog", "fish,bird").flatMap {
        it.split(",")
    }.print() // [cat, dog, fish, bird]

    // Contrast to map
    listOf("cat,dog", "fish,bird").map {
        it.split(",")
    }.print() // [[cat, dog], [fish, bird]]
}
