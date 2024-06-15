package year20.day7

import util.readLines

class Parsed(line: String) {
    private val regex = Regex("(.+) bags contain (.+)")
    private val groups: List<String> = regex.matchEntire(line)?.groupValues.orEmpty().drop(1)

    val parent = groups[0]
    val children = groups[1]
}

class ParsedBags(line: String, childrenCount: Int) {
    private val regex = Regex("(\\d+) (.+) ba.+")
    private val lines = line.split(", ")
    private val emptyBag = "no other bags"

    val bags: Map<String, Int> = if (line.contains(emptyBag)) emptyMap() else lines.associate { line ->
        val groups = regex.matchEntire(line)?.groupValues.orEmpty().drop(1)
        groups[1] to groups[0].toInt() * childrenCount
    }
}

fun main() {
    val lines = readLines()

    val values = lines.map(::Parsed)

    println("part 1: ${part1(values)}")
    println("part 2 not working: ${part2NotWorking(values)}")
    println("part 2: ${part2(lines)}")
}

private fun part1(values: List<Parsed>): Int {
    val requestedBag = "shiny gold"
    val parents = mutableSetOf(requestedBag)
    while (true) {
        val filtered = values.filter { it.children.contains(Regex(parents.joinToString(separator = "|", prefix = "(", postfix = ")"))) }
        if (parents.addAll(filtered.map(Parsed::parent)).not()) break
    }
    return parents.size - 1
}

private fun part2NotWorking(values: List<Parsed>): Int {
    val parsedBags = mutableMapOf("shiny gold" to 1)
    var counter = 0
    val parsedValues = values.toMutableList()
    while (true) {
        val filtered = parsedValues.filter { parsed -> parsed.parent in parsedBags.keys }
        parsedValues.removeAll(filtered)

        val bags = filtered.map { parsed ->
            val childrenCount = parsedBags[parsed.parent] ?: 1
            ParsedBags(parsed.children, childrenCount)
        }.fold(mutableMapOf<String, Int>()) { map, parsed -> map.mergeMap(parsed.bags) { old, new -> old + new } }

        counter += bags.values.sum()
        parsedBags.mergeMap(bags) { old, new -> old + new }
        if (filtered.isEmpty() || parsedValues.isEmpty()) break
    }
    return counter
}

private fun <K, V : Any> MutableMap<K, V>.mergeMap(
    anotherMap: Map<K, V>,
    operation: (oldValue: V, newValue: V) -> V
): MutableMap<K, V> = apply {
    anotherMap.forEach { (key, value) -> merge(key, value) { old, new -> operation(old, new) } }
}

private fun part2(lines: List<String>): Int {
    val rules = lines.associate { line ->
        val (parent, children) = line.replace(Regex("bags?\\.?"), "")
            .split("contain")
            .map(String::trim)
        val rules = if (children.contains("no other")) emptySet()
        else children.split(",").map(String::trim).toSet()
        parent to rules
    }
    return rules.getChildrenCount("shiny gold")
}

private val digits = Regex("\\d+")

private fun Map<String, Set<String>>.getChildrenCount(color: String): Int {
    val children = getOrDefault(color, emptySet())
    if (children.isEmpty()) return 0
    var total = 0
    for (child in children) {
        val count = digits.findAll(child).first().value.toInt()
        val bag = digits.replace(child, "").trim()
        total += count + count * getChildrenCount(bag)
    }
    return total
}
