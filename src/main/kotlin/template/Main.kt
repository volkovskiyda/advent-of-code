package template

import util.readLines

fun main() {
    val lines = readLines(sampleData = true)

    println("part 1: ${part1(lines)}")
    println("part 2: ${part2(lines)}")
}

private fun part1(values: List<String>): Int = values.size

private fun part2(values: List<String>): Int = values.size
