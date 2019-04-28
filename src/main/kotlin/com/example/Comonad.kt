package com.example

import com.example.util.CoIdentity
import com.example.util.print

fun main() {

    CoIdentity(1).extract().print() // 1

    CoIdentity(1).extend { it + 1 }.print() // CoIdentity(2)
}
