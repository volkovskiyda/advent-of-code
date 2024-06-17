package year20.day1

import util.readLines

fun main() {
    val lines = readLines()

    val values = lines.map { it.toInt() }

    println("part 1: ${part1(values)}")
    println("part 2: ${part2(values)}")
}

private fun part1(values: List<Int>): Int {
    var result = 0
    values.forEachIndexed { index1, value1 ->
        values.drop(index1 + 1).forEachIndexed { index2, value2 ->
            if (value1 + value2 == 2020) {
                result = value1 * value2
            }
        }
    }
    return result
}

private fun part2(values: List<Int>): Int {
    var result = 0
    values.forEachIndexed { index1, value1 ->
        values.drop(index1 + 1).forEachIndexed { index2, value2 ->
            values.drop(index2 + 1).forEach { value3 ->
                if (value1 + value2 + value3 == 2020) {
                    result = value1 * value2 * value3
                }
            }
        }
    }
    return result
}