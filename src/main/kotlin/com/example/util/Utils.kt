package com.example.util

import com.example.Either

fun Any.print() = println(this)

typealias Function<T, R> = (T) -> R

// we have to call this plus instead of combine to overload the + operator
operator fun <T, U, R> Function<T, U>.plus(fn: Function<U, R>): Function<T, R> {
    return { t ->
        fn(this(t))
    }
}

fun <T, U, R> compose(f: Function<T, U>, g: Function<U, R>): Function<T, R> {
    return { param: T ->
        g(f(param))
    }
}

fun <T, R> Iterable<T>.map(f: (T) -> R): Iterable<R> {
    return this.fold(listOf()) { acc, next ->
        acc + f(next)
    }
}

fun <T, R> Iterable<T>.flatMap(f: (T) -> Iterable<R>): Iterable<R> {
    return this.fold(listOf()) { acc, next ->
        acc + f(next)
    }
}

fun <T, R> Iterable<Function<T, R>>.ap(elements: Iterable<T>): Iterable<R> {
    return this.fold(listOf()) { acc, next ->
        acc + elements.map(next)
    }
}

class CoIdentity<T>(val value: T) {

    fun extract() = value

    fun extend(f: (T) -> T) = CoIdentity(f(value))
}
