package year20.day10

import util.readLines

fun main() {
    val lines = readLines()

    val values = (lines.map(String::toInt) + 0).sorted()

    println("part 1: ${part1(values)}")
    println("part 2: ${part2(values)}")
}

private fun part1(values: List<Int>): Int {
    var diffOne = 0
    var diffThree = 1
    values.windowed(2, 1) { (first, second) ->
        when (second - first) {
            1 -> diffOne++
            3 -> diffThree++
            else -> Unit
        }
    }
    return diffOne * diffThree
}

private fun part2(values: List<Int>): Long {
    val possibilities = mapOf(
        3 to 2,
        4 to 4,
        5 to 7,
        6 to 14,
    )
    var counter = 0
    val repeats = mutableListOf<Int>()
    values.zipWithNext { a, b ->
        if (b - a == 1) counter++
        else {
            if (counter > 1) repeats.add((counter+1))
            counter = 0
        }
    }
    if (counter > 1) repeats.add((counter+1))
    return repeats.map { requireNotNull(possibilities[it]) }.fold(1) { acc, i -> acc * i }
}

/**
 * https://en.wikipedia.org/wiki/Combination#Number_of_k-combinations_for_all_k
 * Possible variants for 3 digit sequence = 2
 * 123
 * 13
 *
 * Possible variants for 4 digit sequence = 4
 * 1234
 * 134
 * 14
 * 124
 *
 * Possible variants for 5 digit sequence = 7
 * 12345
 * 1235
 * 1245
 * 125
 * 1345
 * 145
 * 135
 *
 * Possible variants for 6 digit sequence = 14
 * 123456
 * 12346
 * 12356
 * 1236
 * 12456
 * 1246
 * 1256
 * 13456
 * 1346
 * 1356
 * 136
 * 1456
 * 146
 * 126
 */
