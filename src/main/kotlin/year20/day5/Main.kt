package year20.day5

import util.readLines

fun main() {
    val lines = readLines()

    val values = lines.map { line ->
        line.map { char -> if (char == 'B' || char == 'R') "1" else "0" }.joinToString("").toInt(2)
    }

    println("part 1: ${part1(values)}")
    println("part 2: ${part2(values)}")
}

private fun part1(seats: List<Int>): Int = seats.max()

private fun part2(seats: List<Int>): Int =
    (seats.min()..seats.max()).toList().first { seat -> seat !in seats }
