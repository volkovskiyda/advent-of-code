package year20.day04

import util.readLines

fun main() {
    val lines = readLines()

    var current = ""
    val values = lines.fold(mutableListOf<String>()) { acc, line ->
        if (line.isEmpty()) {
            acc.add(current.dropLast(1))
            current = ""
        } else current += "$line "
        acc
    }
    values.add(current.dropLast(1))

    println("part 1: ${part1(values)}")
    println("part 2: ${part2(values)}")
}

private fun part1(values: List<String>): Int {
    val requiredFields = listOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

    val fields = values.map { line ->
        line.split(" ").map { fields ->
            val split = fields.split(":")
            split[0]
        }
    }
    val result = fields.count { line -> line.containsAll(requiredFields) }
    return result
}

private fun part2(values: List<String>): Int {
    val fields = values.map { line ->
        line.split(" ").associate { fields ->
            val split = fields.split(":")
            split[0] to split[1]
        }
    }

    val hclExpr = "#[0-9a-f]{6}".toRegex()
    val ecl = listOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")

    val result = fields.count { line ->
        (line["byr"] ?: return@count false).toInt() in (1920..2002) &&
        (line["iyr"] ?: return@count false).toInt() in (2010..2020) &&
        (line["eyr"] ?: return@count false).toInt() in (2020..2030) &&
        (line["hgt"] ?: return@count false).isValidHeight() &&
        (line["hcl"] ?: return@count false).matches(hclExpr) &&
        (line["ecl"] ?: return@count false) in ecl &&
        (line["pid"] ?: return@count false).length == 9

    }
    return result

}

private fun String.isValidHeight(): Boolean = when {
    endsWith("cm") -> dropLast(2).toInt() in 150..193
    endsWith("in") -> dropLast(2).toInt() in 59..76
    else -> false
}
