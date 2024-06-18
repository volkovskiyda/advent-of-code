package year20.day06

import util.readLines

fun main() {
    val lines = readLines()

    println("part 1: ${part1(lines)}")
    println("part 2: ${part2(lines)}")
}

private fun part1(lines: List<String>): Int {
    var current = ""
    val values = lines.fold(mutableListOf<String>()) { acc, line ->
        if (line.isEmpty()) {
            acc.add(current)
            current = ""
        } else current += line
        acc
    }
    values.add(current)
    return values.sumOf { value -> value.toSet().size }
}

private fun part2(lines: List<String>): Int {
    var current = ('a'..'z').toList()
    val values = lines.fold(mutableListOf<List<Char>>()) { acc, line ->
        if (line.isEmpty()) {
            acc.add(current)
            current = ('a'..'z').toList()
        } else current = current.intersect(line.toList().toSet()).toList()
        acc
    }
    values.add(current)
    return values.sumOf { value -> value.size }
}

