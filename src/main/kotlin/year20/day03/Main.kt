package year20.day03

import util.readLines

fun main() {
    val lines = readLines()

    println("part 1: ${part1(lines)}")
    println("part 2: ${part2(lines)}")
}

private fun part1(lines: List<String>): Int {
    var result = 0
    val lineLength = lines[0].length
    lines.forEachIndexed { index, line ->
        if (index != 0 && line[(index * 3) % lineLength] == '#') result++
    }
    return result
}

private fun part2(lines: List<String>): Long {
    val trees = intArrayOf(0, 0, 0, 0, 0)
    val lineLength = lines[0].length
    lines.forEachIndexed { index, line ->
        if (index == 0) Unit else {
            if (line[(index * 1) % lineLength] == '#') trees[0]++
            if (line[(index * 3) % lineLength] == '#') trees[1]++
            if (line[(index * 5) % lineLength] == '#') trees[2]++
            if (line[(index * 7) % lineLength] == '#') trees[3]++
            if (index % 2 == 0 && line[(index / 2) % lineLength] == '#') trees[4]++
        }
    }
    val result = trees.filter { it > 0 }.fold(1L) { result, tree -> result * tree }
    println(trees.map { "$it" })
    return result
}