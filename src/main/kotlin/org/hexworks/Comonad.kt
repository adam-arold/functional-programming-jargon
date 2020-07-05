package org.hexworks

import org.hexworks.util.CoIdentity
import org.hexworks.util.print

fun main() {

    CoIdentity(1).extract().print() // 1

    CoIdentity(1).extend { it + 1 }.print() // CoIdentity(2)
}
