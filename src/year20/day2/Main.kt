package year20.day2

import util.readLines

class Parsed(line: String) {
    private val groups: List<String> =
        Regex("(\\d+)-(\\d+) (\\w): (\\w+)").matchEntire(line)?.groupValues.orEmpty().drop(1)

    val first = groups[0].toInt()
    val second = groups[1].toInt()
    val symbol = groups[2][0]
    val pass = groups[3]
}

fun main() {
    val lines = readLines()

    val values = lines.map(::Parsed)

    println("part 1: ${part1(values)}")
    println("part 2: ${part2(values)}")
}

private fun part1(values: List<Parsed>): Int = values.count { parsed: Parsed ->
        parsed.pass.count { it  == parsed.symbol } in parsed.first..parsed.second
    }

private fun part2(values: List<Parsed>): Int = values.count { parsed: Parsed ->
    (parsed.pass[parsed.first-1] == parsed.symbol) xor (parsed.pass[parsed.second-1] == parsed.symbol)
}