# Functional Programming Jargon

> This document is a port of the original [Functional Programming Jargon](https://github.com/hemanth/functional-programming-jargon)
to the **Kotlin** programming language. Feel free to read the original if you are looking for a Javascript version.

Functional programming (FP) provides many advantages, and its popularity has been increasing as a result.
However, each programming paradigm comes with its own unique jargon and FP is no exception.
By providing a glossary, we hope to make learning FP easier.

The examples are presented in Kotlin.

**Table of Contents**

* [Arity](#arity)
* [Higher-Order Functions (HOF)](#higher-order-functions)
* [Closure](#closure)
* [Partial Application](#partial-application)
* [Currying](#currying)
* [Auto Currying](#auto-currying)
* [Function Composition](#function-composition)
* [Continuation](#continuation)
* [Purity](#purity)
* [Side effects](#side-effects)
* [Idempotent](#idempotent)
* [Point-Free Style](#point-free-style)
* [Predicate](#predicate)
* [Contracts](#contracts)
* [Category](#category)
* [Value](#value)
* [Constant](#constant)
* [Functor](#functor)
* [Pointed Functor](#pointed-functor)
* [Lift](#lift)
* [Referential Transparency](#referential-transparency)
* [Lambda](#lambda)
* [Lambda Calculus](#lambda-calculus)
* [Lazy evaluation](#lazy-evaluation)
* [Monoid](#monoid)
* [Monad](#monad)
* [Comonad](#comonad)
* [Applicative Functor](#applicative-functor)
* [Morphism](#morphism)
  * [Endomorphism](#endomorphism)
  * [Isomorphism](#isomorphism)
  * [Homomorphism](#homomorphism)
  * [Catamorphism](#catamorphism)
* [Semigroup](#semigroup)
* [Foldable](#foldable)
* [Lens](#lens)
* [Algebraic data type](#algebraic-data-type)
  * [Sum type](#sum-type)
  * [Product type](#product-type)
* [Option](#option)
* [Function](#function)
* [Partial function](#partial-function)
* [Functional Programming Libraries in JavaScript](#functional-programming-libraries-in-javascript)


## Arity

The number of arguments a function takes. From words like unary, binary, ternary, etc. This word has the distinction of
being composed of two suffixes, "-ary" and "-ity." Addition, for example, takes two arguments, and so it is defined as
a binary function or a function with an arity of two. Such a function may sometimes be called "dyadic" by people who
prefer Greek roots to Latin. Likewise, a function that takes a variable number of arguments is called "variadic,"
whereas a binary function must be given two and only two arguments, currying and partial application notwithstanding
(see below).

```kotlin
val sum = { x: Int, y: Int ->
    x + y
}

val arity = sum.reflect()!!.parameters.size

println(arity) // 2

// The arity of sum is 2
```


## Higher-Order Functions

A function which takes a function as an argument and/or returns a function.

```kotlin
val filter = { predicate: (Any) -> Boolean, xs: Array<out Any> ->
    xs.filter(predicate)
}
```

```kotlin
val isA = { type: KClass<out Any> ->
    { x: Any ->
        type.isSuperclassOf(x::class)
    }
}
```

```kotlin
filter(isA(Int::class), arrayOf(0, "1", 2)) // [0, 2]
```


## Closure

A closure is a way of accessing a variable outside its scope. This is important for 
[partial application](#partial-application) to work. Formally, a closure is a technique for implementing
lexically scoped named binding. It is a way of storing a function with an environment.

A closure is a scope which captures local variables of a function for access even after the execution has moved
out of the block in which it is defined. ie. they allow referencing a scope after the block in which the variables
were declared has finished executing.


```kotlin
val addTo = { x: Int ->
    fun add(y: Int): Int {
        return x + y
    }
    ::add
}

val addToFive = addTo(5)

addToFive(3) // 8
```

> Note that we could have defined `addTo` with just lambdas. `add` here demonstrates how local function
declaration works in Kotlin.

The function `addTo()` returns a function (`add`), lets us store it in a variable called `addToFive` with a
[curried](#currying) call having the parameter `5`.

Ideally, when the function `addTo` finishes execution, its scope, with local variables `add`, `x` and `y` should
not be accessible. But, it returns 8 on calling `addToFive()`. This means that the state of the function `addTo`
is saved even after the block of code has finished executing, otherwise there is no way of knowing that `addTo`
was called as `addTo(5)` and the value of `x` was set to `5`.

Lexical scoping is the reason why it is able to find the values of `x` and `add` - the private variables of the
parent which has finished executing. This value is called a Closure.

The stack along with the lexical scope of the function is stored in form of reference to the parent.
This prevents the closure and the underlying variables from being garbage collected (since there is at least one
live reference to it).

Lambda Vs Closure: A lambda is essentially a function that is defined inline rather than the standard method of
declaring functions. Lambdas can frequently be passed around as objects.

A closure is a function that encloses its surrounding state by referencing fields external to its body.
The enclosed state remains across invocations of the closure.


**Further reading / Sources**
* [Lambda Vs Closure](http://stackoverflow.com/questions/220658/what-is-the-difference-between-a-closure-and-a-lambda)
* [Closures in Kotlin](https://kotlinlang.org/docs/reference/lambdas.html#closures)


## Partial Application

Partially applying a function means creating a new function by pre-filling some of the arguments to the original function.


```kotlin
// Something to apply
val add3 = { a: Int, b: Int, c: Int ->
    a + b + c
}

// Partially applying `2` and `3` to `add3` gives you a one-argument function
val fivePlus = { x: Int ->
    add3(x, 2, 3)
}

fivePlus(4) // 9
```

Partial application helps create simpler functions from more complex ones by baking in data when you have it.
[Curried](#currying) functions are automatically partially applied.


## Currying

The process of converting a function that takes multiple arguments into a function that takes them one at a time.

Each time the function is called it only accepts one argument and returns a function that takes one argument
until all arguments are passed.

```kotlin
val sum = { a: Int, b: Int ->
    a + b
}

val curriedSum = { a: Int ->
    { b: Int ->
        a + b
    }
}

curriedSum(40)(2) // 42.

val add2 = curriedSum(2) // (b: Int) -> 2 + b

add2(10) // 12
```


## Auto Currying

Transforming a function that takes multiple arguments into one that if given less than its correct number
of arguments returns a function that takes the rest. When the function gets the correct number of arguments
it is then evaluated.

When using Kotlin you can add [Arrow](https://arrow-kt.io) to your project dependencies which comes with built-in
support for currying.


**Further reading**
* [Favoring Curry](http://fr.umio.us/favoring-curry/)


## Function Composition

The act of putting two functions together to form a third function where the output of one function is the input
of the other.

```kotlin
typealias Function<T, R> = (T) -> R

fun <T, U, R> compose(f: Function<T, U>, g: Function<U, R>): Function<T, R> {
    return { param: T ->
        g(f(param))
    }
}

val floorAndToString = compose(Double::roundToInt) { it.toString() }

floorAndToString(121.212121) // "121"
```

> Note that the Kotlin compiler can infer the generic type parameters so we don't have to pass them here.


## Continuation

At any given point in a program, the part of the code that's yet to be executed is known as a continuation.

```kotlin
val printAsString = { num: Int ->
    println("Given $num")
}

val addOneAndContinue = { num: Int, cc: (Int) -> Unit ->
    val result = num + 1
    cc(result)
}

addOneAndContinue(2, printAsString) // "Given 3"
```

Continuations are often seen in asynchronous programming when the program needs to wait to receive data before it
can continue. The response is often passed off to the rest of the program, which is the continuation, once it's been
received.

```kotlin
val continueProgramWith = { data: Any ->
    // continues program with data
}

try {
    continueProgramWith(File("path/to/file").readBytes())
} catch (e: Exception) {
    // handle error
}
```


## Purity

A function is pure if the return value is only determined by its
input values, and does not produce side effects.

```kotlin
val greet = { name: String ->
    "Hi, $name"
}

greet("Brianne") // "Hi, Brianne"
```

As opposed to each of the following:

```kotlin
var name = "Brianne"

val greet = {
    "Hi, $name"
}

greet() // "Hi, Brianne"
```

The above example's output is based on data stored outside of the function...

```kotlin
var greeting = ""

val greet = { name: String ->
    greeting = "Hi, $name"
}

greet("Brianne")
greeting // "Hi, Brianne"
```

... and this one modifies state outside of the function.


## Side effects

A function or expression is said to have a *side effect* if apart from returning a value, 
it interacts with (reads from or writes to) external mutable state.

```kotlin
val differentEveryTime = {
    Date()
}
```

```kotlin
println("IO is a side effect!")
```


## Idempotence

A function is *idempotent* if reapplying it to its result does not produce a different result.

```
f(f(x)) ≍ f(x)
```

```kotlin
abs(abs(10))
```

```kotlin
listOf(2, 1).sorted().sorted().sorted()
```


## Point-Free Style

Writing functions where the definition does not explicitly identify the arguments used.
This style usually requires [currying](#currying) or other [Higher-Order functions](#higher-order-functions).
A.K.A *Tacit programming*.

```kotlin
// Given
val map = { fn: (Int) -> Int ->
    { list: List<Int> ->
        list.map(fn)
    }
}

val add = { a: Int ->
    { b: Int ->
        a + b
    }
}

// Then

// Not points-free - `numbers` is an explicit argument
val incrementAll = { numbers: List<Int> ->
    map(add(1))(numbers)
}

// Points-free - The list is an implicit argument
val incrementAll2 = map(add(1))
```

`incrementAll` identifies and uses the parameter `numbers`, so it is not points-free. `incrementAll2` is written just
by combining functions and values, making no mention of its arguments.  It **is** points-free.

Points-free function definitions look just like normal assignments without `numbers: List<Int> ->`


## Predicate

A predicate is a function that returns `true` or `false` for a given value. A common use of a predicate is
as the callback for filters.

```kotlin
val predicate = { a: Int ->
    a > 2
}

listOf(1, 2, 3, 4).filter(predicate) // [3, 4]
```


## Contracts

A *contract* specifies the obligations and guarantees of the behavior from a function or expression at runtime.
This acts as a set of rules that are expected from the input and output of a function or expression,
and errors are generally reported whenever a contract is violated.

```kotlin
val contract = { input: Any ->
    if (input is Int) {
        input
    } else throw RuntimeException(
            "Contract violated: expected an Int")
}

val addOne = { num: Any ->
    contract(num) + 1
}

addOne(2) // 3

addOne("some string") // Contract violated: expected an Int
```

> Note that Kotlin has its own implementation of [Contracts](https://kotlinlang.org/docs/reference/whatsnew13.html#contracts)
but it is still an *experimental* feature


## Category

A *category* in category theory is a collection of objects and morphisms between them. In programming, typically types
act as the objects and functions as morphisms.

To be a valid category 3 rules must be met:

1. There must be an identity morphism that maps an object to itself.
   Where `a` is an object in some category, there must be a function from `a -> a`.
2. Morphisms must compose.
   Where `a`, `b`, and `c` are objects in some category,
   and `f` is a morphism from `a -> b`, and `g` is a morphism from `b -> c`;
   `g(f(x))` must be equivalent to `(g • f)(x)`.
3. Composition must be associative
   `f • (g • h)` is the same as `(f • g) • h`

> Note that `•` is the `compose` operator

Since these rules govern composition at a very abstract level, category theory is great at uncovering new ways of
composing things.

**Further reading**

* [Category Theory for Programmers](https://bartoszmilewski.com/2014/10/28/category-theory-for-programmers-the-preface/)


## Value

Anything that can be assigned to a variable.

```kotlin
5
mapOf("name" to "John", "age" to 30) // a Map is immutable
{ a: Any ->
    a
}
listOf(1)
null
```


## Constant

A variable that cannot be reassigned once defined.

```kotlin
val five = 5
val twoAndThree = listOf(2, 3)
```

Constants are [referentially transparent](#referential-transparency). That is, they can be replaced with the values
that they represent without affecting the result.

With the above two constants the following expression will always return `true`.

```kotlin
twoAndThree + five == listOf(2, 3) + 5
```


## Functor

An object that implements a `map` function which, while iterating over each value in the object to produce a new object,
adheres to two rules:

**Preserves identity**
```
object.map { x -> x } ≍ object
```

**Composable**

```
object.map(compose(f, g)) ≍ object.map(g).map(f)
```

(`f`, `g` are arbitrary functions)

A common functor in *Kotlin* is `List` since it adheres to the two functor rules:

```kotlin
listOf(1, 2, 3).map { it } // [1, 2, 3]
```

and

```kotlin
val f = { x: Int -> x + 1 }
val g = { x: Int -> x * 2 }

listOf(1, 2, 3).map { f(g(it)) } // [3, 5, 7]
listOf(1, 2, 3).map(g).map(f) // [3, 5, 7]
```


## Pointed Functor

An object with an `of` function that puts *any* single value into it.

*Kotlin* provides `*of` functions for `List` for example:

```kotlin
listOf(1)
```

> In other languages `of` might be called `just`.


## Lift

TODO: this is a mess, fix it

Lifting is when you take a value and put it into an object like a [functor](#pointed-functor).
If you lift a function into an [Applicative Functor](#applicative-functor) then you can make it work on
values that are also in that functor.

Some implementations have a function called `lift`, or `liftA2` to make it easier to run functions on functors.

```js
const liftA2 = (f) => (a, b) => a.map(f).ap(b) // note it's `ap` and not `map`.

const mult = a => b => a * b

const liftedMult = liftA2(mult) // this function now works on functors like array

liftedMult([1, 2], [3]) // [3, 6]
liftA2(a => b => a + b)([1, 2], [3, 4]) // [4, 5, 5, 6]
```

Lifting a one-argument function and applying it does the same thing as `map`.

```js
const increment = (x) => x + 1

lift(increment)([2]) // [3]
;[2].map(increment) // [3]
```


## Referential Transparency

An expression that can be replaced with its value without changing the
behavior of the program is said to be referentially transparent.

Say we have function greet:

```kotlin
val greet = { "Hello World!" }
```

Any invocation of `greet()` can be replaced with `Hello World!` hence `greet` is
referentially transparent.


## Lambda

An anonymous function that can be treated like a value.

```kotlin
{ a: Int -> a + 1 }
```
Lambdas are often passed as arguments to Higher-Order functions.

```kotlin
listOf(1, 2).map { a: Int -> a + 1 } // [2, 3]
```

You can assign a lambda to a variable.

```kotlin
val add1 = { a: Int -> a + 1 }
```


## Lambda Calculus

A branch of mathematics that uses functions to create a [universal model of computation](https://en.wikipedia.org/wiki/Lambda_calculus).
[This](http://palmstroem.blogspot.com/2012/05/lambda-calculus-for-absolute-dummies.html) article explains it in depth
and it is also very easy to understand.


## Lazy evaluation

Lazy evaluation is a call-by-need evaluation mechanism that delays the evaluation of an expression until its value
is needed. In functional languages, this allows for structures like infinite lists, which would not normally be
available in an imperative language where the sequencing of commands is significant.

> Note that in *Kotlin* we use `Sequence`s for generating a potentially infinite sequence of values, like
in the example below and `lazy` for lazy initialization.

```kotlin
val rand = {
    sequence {
        while (true) {
            yield(Random.nextInt())
        }
    }
}
```

```kotlin
rand().take(10).toList()
```


## Monoid

An object with a function that "combines" that object with another of the same type.

One simple monoid is the addition of numbers:

```kotlin
1 + 1 // 2
```
In this case number is the object and `+` is the function.

An "identity" value must also exist that when combined with a value doesn't change it.

The identity value for addition is `0`.

```kotlin
1 + 0 // 1
```

It's also required that the grouping of operations will not affect the result (associativity):

```kotlin
1 + (2 + 3) == (1 + 2) + 3 // true  
```

Adding `List`s together also forms a monoid:

```kotlin
listOf(1, 2) + listOf(3, 4) // [1, 2, 3, 4]
```

The identity value is an empty `List`: `listOf<Int>()`

```kotlin
listOf(1, 2) + listOf() // [1, 2]
```

If identity and compose functions are provided, functions themselves form a monoid:

```kotlin
val identity = { x: Any -> x }

typealias Function<T, R> = (T) -> R

// we have to call this plus instead of combine to overload the + operator
operator fun <T, U, R> Function<T, U>.plus(fn: Function<U, R>): Function<T, R> {
    return { t ->
        fn(this(t))
    }
}
```

`foo` is any function that takes one argument.

```
foo + identity ≍ identity + foo ≍ foo
```


## Monad

A monad is an object with [`of`](#pointed-functor) and `flatMap` functions. `flatMap` is like [`map`](#functor)
except it un-nests the resulting nested object.

```kotlin
// Implementation
fun <T, R> Iterable<T>.flatMap(f: (T) -> Iterable<R>): Iterable<R> {
    return this.fold(listOf()) { acc, next ->
        acc + f(next)
    }
}

// Usage
listOf("cat,dog", "fish,bird").flatMap { 
    it.split(",")
} // [cat, dog, fish, bird]

// Contrast to map
listOf("cat,dog", "fish,bird").map {
    it.split(",")
} // [[cat, dog], [fish, bird]]
```

> `of` is also known as `just` in other languages.
> `flatMap` is also known as `bind` in other languages.


## Comonad

An object that has `extract` and `extend` functions.

```kotlin
class CoIdentity<T>(val value: T) {

    fun extract() = value

    fun extend(f: (T) -> T) = CoIdentity(f(value))
}
```

*Extract* takes a value out of a functor.

```kotlin
CoIdentity(1).extract() // 1
```

*Extend* runs a function on the comonad. The function should return the same type as the comonad.

```kotlin
CoIdentity(1).extend { it + 1 } // CoIdentity(2)
```


## Applicative Functor

An applicative functor is an object with an `ap` function. `ap` applies a function in the object to a value in another
object of the same type.

```kotlin
// Implementation
fun <T, R> Iterable<Function<T, R>>.ap(elements: Iterable<T>): Iterable<R> {
    return this.fold(listOf()) { acc, next ->
        acc + elements.map(next)
    }
}

// Example usage
listOf({a: Int -> a + 1}).ap(listOf(1)) // [2]
```

This is *useful* if you have two objects and you want to apply a binary function to their contents.

```kotlin
// Lists that you want to combine
val arg0 = listOf(1, 3)
val arg1 = listOf(4, 5)

// combining function - must be curried for this to work

val add = { x: Int -> { y: Int -> x + y } }

val partiallyAppliedAdds = listOf(add).ap(arg0) // [(y) -> 1 + y, (y) -> 3 + y]
```

This gives you an array of functions that you can call `ap` on to get the result:

```kotlin
partiallyAppliedAdds.ap(arg1) // [5, 6, 7, 8]
```


## Morphism

A transformation function.


### Endomorphism

A function where the input type is the same as the output.

```kotlin
// (String) -> String
val uppercase = { str: String -> str.toUpperCase() }

// (Int) -> Int
val decrement = { x: Int -> x - 1 }
```


### Isomorphism

A pair of transformations between 2 types of objects that is structural in nature and no data is lost.

For example, 2D coordinates could be stored as an array `[2,3]` or object `{x: 2, y: 3}`.

```js
// Providing functions to convert in both directions makes them isomorphic.
val pairToCoords = { (x, y): Pair<Int, Int> -> Coords(x, y) }

val coordsToPair = { (x, y): Coords -> x to y }

coordsToPair(pairToCoords(1 to 2)) // (1, 2)

pairToCoords(coordsToPair(Coords(1, 2))) // Coords(x=1, y=2)
```


### Homomorphism

A homomorphism is a structure preserving `map`. In fact, a [functor](#functor) is just a homomorphism between
[categories](#category) as it preserves the original category's structure under the mapping.

```kotlin
A.of(f).ap(A.of(x)) == A.of(f(x))

listOf(uppercase).ap(listOf("oreos")) == listOf(uppercase("oreos"))
```


### Catamorphism

A `reduceRight` function that applies a function against an accumulator and each value of the array (from right-to-left)
to reduce it to a single value.

```kotlin
val sum = { values: List<Int> -> values.reduceRight{ x, acc -> acc + x}}

sum(listOf(1, 2, 3, 4, 5)) // 15
```

## Semigroup

An object that has a `concat` function that combines it with another object of the same type. Note that
a semigroup is different from a [monoid](#monoid) because it doesn't require an `identity` function.

```kotlin
listOf(1) + listOf(2) // [1, 2]
```


## Foldable

An object that has a `reduce` function that applies a function against an accumulator and each element in the array
(from left to right) to reduce it to a single value.

```js
const sum = (list) => list.reduce((acc, val) => acc + val, 0)
sum([1, 2, 3]) // 6
```


## Lens

A lens is a structure (often an object or function) that pairs a getter and a non-mutating setter for some other data
structure.

```js
// Using [Ramda's lens](http://ramdajs.com/docs/#lens)
const nameLens = R.lens(
  // getter for name property on an object
  (obj) => obj.name,
  // setter for name property
  (val, obj) => Object.assign({}, obj, {name: val})
)
```

Having the pair of get and set for a given data structure enables a few key features.

```js
const person = {name: 'Gertrude Blanch'}

// invoke the getter
R.view(nameLens, person) // 'Gertrude Blanch'

// invoke the setter
R.set(nameLens, 'Shafi Goldwasser', person) // {name: 'Shafi Goldwasser'}

// run a function on the value in the structure
R.over(nameLens, uppercase, person) // {name: 'GERTRUDE BLANCH'}
```

Lenses are also composable. This allows easy immutable updates to deeply nested data.

```js
// This lens focuses on the first item in a non-empty array
const firstLens = R.lens(
  // get first item in array
  xs => xs[0],
  // non-mutating setter for first item in array
  (val, [__, ...xs]) => [val, ...xs]
)

const people = [{name: 'Gertrude Blanch'}, {name: 'Shafi Goldwasser'}]

// Despite what you may assume, lenses compose left-to-right.
R.over(compose(firstLens, nameLens), uppercase, people) // [{'name': 'GERTRUDE BLANCH'}, {'name': 'Shafi Goldwasser'}]
```

Other implementations:
* [partial.lenses](https://github.com/calmm-js/partial.lenses) - Tasty syntax sugar and a lot of powerful features
* [nanoscope](http://www.kovach.me/nanoscope/) - Fluent-interface

## Algebraic data type

A composite type made from putting other types together. Two common classes of algebraic types are [sum](#sum-type)
and [product](#product-type).


### Sum type

A Sum type is the combination of two types together into another one. It is called sum because the number of possible
values in the result type is the sum of the input types.

JavaScript doesn't have types like this but we can use `Set`s to pretend:
```js
// imagine that rather than sets here we have types that can only have these values
const bools = new Set([true, false])
const halfTrue = new Set(['half-true'])

// The weakLogic type contains the sum of the values from bools and halfTrue
const weakLogicValues = new Set([...bools, ...halfTrue])
```

Sum types are sometimes called union types, discriminated unions, or tagged unions.

There's a [couple](https://github.com/paldepind/union-type) [libraries](https://github.com/puffnfresh/daggy)
in JS which help with defining and using union types.

Flow includes [union types](https://flow.org/en/docs/types/unions/) and TypeScript has 
[Enums](https://www.typescriptlang.org/docs/handbook/enums.html) to serve the same role.


### Product type

A **product** type combines types together in a way you're probably more familiar with:

```js
// point :: (Number, Number) -> {x: Number, y: Number}
const point = (x, y) => ({ x, y })
```
It's called a product because the total possible values of the data structure is the product of the different values.
Many languages have a tuple type which is the simplest formulation of a product type.

See also [Set theory](https://en.wikipedia.org/wiki/Set_theory).


## Option

Option is a [sum type](#sum-type) with two cases often called `Some` and `None`.

Option is useful for composing functions that might not return a value.

```js
// Naive definition

const Some = (v) => ({
  val: v,
  map (f) {
    return Some(f(this.val))
  },
  chain (f) {
    return f(this.val)
  }
})

const None = () => ({
  map (f) {
    return this
  },
  chain (f) {
    return this
  }
})

// maybeProp :: (String, {a}) -> Option a
const maybeProp = (key, obj) => typeof obj[key] === 'undefined' ? None() : Some(obj[key])
```
Use `chain` to sequence functions that return `Option`s
```js

// getItem :: Cart -> Option CartItem
const getItem = (cart) => maybeProp('item', cart)

// getPrice :: Item -> Option Number
const getPrice = (item) => maybeProp('price', item)

// getNestedPrice :: cart -> Option a
const getNestedPrice = (cart) => getItem(cart).chain(getPrice)

getNestedPrice({}) // None()
getNestedPrice({item: {foo: 1}}) // None()
getNestedPrice({item: {price: 9.99}}) // Some(9.99)
```

`Option` is also known as `Maybe`. `Some` is sometimes called `Just`. `None` is sometimes called `Nothing`.


## Function

A **function** `f :: A => B` is an expression - often called arrow or lambda expression - with **exactly one
(immutable)** parameter of type `A` and **exactly one** return value of type `B`. That value depends entirely on the
argument, making functions context-independent, or [referentially transparent](#referential-transparency).
What is implied here is that a function must not produce any hidden [side effects](#side-effects) - a function is
always [pure](#purity), by definition. These properties make functions pleasant to work with: they are entirely
deterministic and therefore predictable. Functions enable working with code as data, abstracting over behaviour:

```js
// times2 :: Number -> Number
const times2 = n => n * 2

[1, 2, 3].map(times2) // [2, 4, 6]
```


## Partial function

A partial function is a [function](#function) which is not defined for all arguments - it might return an unexpected
result or may never terminate. Partial functions add cognitive overhead, they are harder to reason about and can lead
to runtime errors. Some examples:

```js
// example 1: sum of the list
// sum :: [Number] -> Number
const sum = arr => arr.reduce((a, b) => a + b)
sum([1, 2, 3]) // 6
sum([]) // TypeError: Reduce of empty array with no initial value

// example 2: get the first item in list
// first :: [A] -> A
const first = a => a[0]
first([42]) // 42
first([]) // undefined
//or even worse:
first([[42]])[0] // 42
first([])[0] // Uncaught TypeError: Cannot read property '0' of undefined

// example 3: repeat function N times
// times :: Number -> (Number -> Number) -> Number
const times = n => fn => n && (fn(n), times(n - 1)(fn))
times(3)(console.log)
// 3
// 2
// 1
times(-1)(console.log)
// RangeError: Maximum call stack size exceeded
```


### Dealing with partial functions

Partial functions are dangerous as they need to be treated with great caution. You might get an unexpected (wrong)
result or run into runtime errors. Sometimes a partial function might not return at all. Being aware of and treating
all these edge cases accordingly can become very tedious.

Fortunately a partial function can be converted to a regular (or total) one. We can provide default values or use guards
to deal with inputs for which the (previously) partial function is undefined.

Utilizing the [`Option`](#Option) type, we can yield either `Some(value)` or `None` where we would otherwise
have behaved unexpectedly:

```js
// example 1: sum of the list
// we can provide default value so it will always return result
// sum :: [Number] -> Number
const sum = arr => arr.reduce((a, b) => a + b, 0)
sum([1, 2, 3]) // 6
sum([]) // 0

// example 2: get the first item in list
// change result to Option
// first :: [A] -> Option A
const first = a => a.length ? Some(a[0]) : None()
first([42]).map(a => console.log(a)) // 42
first([]).map(a => console.log(a)) // console.log won't execute at all
//our previous worst case
first([[42]]).map(a => console.log(a[0])) // 42
first([]).map(a => console.log(a[0])) // won't execte, so we won't have error here
// more of that, you will know by function return type (Option)
// that you should use `.map` method to access the data and you will never forget
// to check your input because such check become built-in into the function

// example 3: repeat function N times
// we should make function always terminate by changing conditions:
// times :: Number -> (Number -> Number) -> Number
const times = n => fn => n > 0 && (fn(n), times(n - 1)(fn))
times(3)(console.log)
// 3
// 2
// 1
times(-1)(console.log)
// won't execute anything
```

Making your partial functions total ones, these kinds of runtime errors can be prevented. Always returning a value
will also make for code that is both easier to maintain as well as to reason about.


## Functional Programming Libraries for Kotlin

Right now the de facto tool for functional programming in Kotlin is [Arrow](https://arrow-kt.io/). It has
everything you might need and comes with batteries included.
