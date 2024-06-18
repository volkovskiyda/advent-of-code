package year20.day08

import util.readLines

data class Accumulator(
    val value: Int, val result: Result
) {
    sealed interface Result {
        data object Success: Result
        data class Failure(val commands: Map<Int, String>): Result
    }
}

fun main() {
    val lines = readLines()

    println("part 1: ${part1(lines)}")
    println("part 2: ${part2(lines)}")
}

private fun part1(lines: List<String>): Int {
    return accumulator(lines).value
}

private fun part2(lines: List<String>): Int {
    var accumulator = accumulator(lines)
    when (accumulator.result) {
        is Accumulator.Result.Success -> return accumulator.value
        is Accumulator.Result.Failure -> {
            val commands = (accumulator.result as Accumulator.Result.Failure).commands
                .filterValues { it.startsWith("jmp") || it.startsWith("nop") }.toMutableMap()
            commands.forEach { (line, command) ->
                accumulator = accumulator(lines, line)
                if (accumulator.result is Accumulator.Result.Success) {
                    return accumulator.value
                }
            }
        }
    }
    return accumulator.value
}

private fun accumulator(lines: List<String>, numLineToChange: Int = -1): Accumulator {
    var accumulator = 0
    var command = 0 to lines.first()
    val execCommands = mutableMapOf(command)

    while (true) {
        if (command.second == "jmp +0" || command.second == "jmp -0") return Accumulator(accumulator, Accumulator.Result.Failure(emptyMap()))
        val jmpCommand = if (command.first == numLineToChange) "nop " else "jmp "
        val nextLineIndexShift =
            if (command.second.startsWith(jmpCommand)) command.second.removePrefix(jmpCommand).toInt() else 1
        if (command.second.startsWith("acc")) accumulator += command.second.removePrefix("acc ").toInt()
        val nextLineIndex = command.first + nextLineIndexShift
        command = nextLineIndex to lines[nextLineIndex]
//            when {
//            command.second.startsWith("acc") -> {
//                accumulator += command.second.removePrefix("acc ").toInt()
//                val nextLineIndex = command.first + 1
//                command = nextLineIndex to lines[nextLineIndex]
//            }
//            command.second.startsWith("jmp") -> {
//                val nextLineIndex = command.first + command.second.removePrefix("jmp ").toInt()
//                command = nextLineIndex to lines[nextLineIndex]
//            }
//            command.second.startsWith("nop") -> {
//                val nextLineIndex = command.first + 1
//                command = nextLineIndex to lines[nextLineIndex]
//            }
//        }
        if (execCommands.contains(command.first))
            return Accumulator(accumulator, Accumulator.Result.Failure(execCommands))
        if (command.first + 1 == lines.size) {
            if (command.second.startsWith("acc"))
                accumulator += command.second.removePrefix("acc ").toInt()
            return Accumulator(accumulator, Accumulator.Result.Success)
        }
        execCommands[command.first] = command.second
    }
}
