package year20.day09

import util.readLines

fun main() {
    val lines = readLines()

    val values = lines.map(String::toLong)

    println("part 1: ${part1(values)}")
    println("part 2: ${part2(values)}")
}

private fun part1(values: List<Long>): Long = transmit(values, 25)

private fun part2(values: List<Long>): Long {
    val transmit = part1(values)
    var shift = 0
    while (true) {
        values.drop(++shift).foldByValue(transmit)?.let { return it }
    }
}

private fun transmit(values: List<Long>, preamble: Int): Long {
    values.drop(preamble).forEachIndexed { index, value ->
        val transmit = values.drop(index).take(preamble).findTransmit(value)
        if (transmit.not()) return value
    }
    throw IllegalStateException("No transmit found")
}

private fun List<Long>.findTransmit(value: Long): Boolean {
    val map = associateBy { value - it }
    return any { map[it] != null }
}

private fun List<Long>.foldByValue(transmit: Long): Long? = foldIndexed(0L) { index, acc, value ->
    val result = acc + value
    when {
        result == transmit -> return take(index).run { min() + max() }
        result > transmit -> return null
        else -> result
    }
}
