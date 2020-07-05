package org.hexworks

import org.hexworks.util.print
import java.io.File

fun main() {
    val printAsString = { num: Int ->
        println("Given $num")
    }

    val addOneAndContinue = { num: Int, cc: (Int) -> Unit ->
        val result = num + 1
        cc(result)
    }

    addOneAndContinue(2, printAsString).print() // "Given 3"


    val continueProgramWith = { data: Any ->
        println("Data is: '$data'")
    }

    try {
        continueProgramWith(File("src/main/resources/testfile").readBytes())
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
