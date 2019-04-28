package com.example

import com.example.util.ap
import com.example.util.print

data class Coords(val x: Int, val y: Int)

sealed class Either<out L, out R> {

    companion object {

        fun <T : Any> of(value: T): Either<Nothing, T> = Right(value)
    }
}

data class Left<out L>(val value: L) : Either<L, Nothing>()

data class Right<out R>(val value: R) : Either<Nothing, R>()


fun main() {

    // Endomorphism
    // (String) -> String
    val uppercase = { str: String -> str.toUpperCase() }

    // (Int) -> Int
    val decrement = { x: Int -> x - 1 }


    // Isomorphism
    val pairToCoords = { (x, y): Pair<Int, Int> -> Coords(x, y) }

    val coordsToPair = { (x, y): Coords -> x to y }

    coordsToPair(pairToCoords(1 to 2)).print() // (1, 2)

    pairToCoords(coordsToPair(Coords(1, 2))).print() // Coords(x=1, y=2)


    // Homomorphism
    (listOf(uppercase).ap(listOf("oreos")) == listOf(uppercase("oreos"))).print() // true


    // Catamorphism
    val sum = { values: List<Int> -> values.reduceRight { x, acc -> acc + x } }

    sum(listOf(1, 2, 3, 4, 5)).print() // 15

}
