package year20

import java.io.File

@JvmInline
value class FileName(val name: String)

@JvmInline
value class FilePath(val path: String)

fun readLines(sampleData: Boolean = false): List<String> = readLines(FileName(if (sampleData) "sample" else "input"))

fun readLines(fileName: FileName): List<String> {
    val (year, day) = packages()
    return readLines(year, day, fileName.name)
}

fun readLines(year: String, day: String, fileName: String) = readLines(FilePath("src/$year/$day/$fileName.txt"))

fun readLines(filePath: FilePath): List<String> = File(filePath.path).readLines()

fun packages(): List<String> = Exception().stackTrace.last().className.split(".").dropLast(1)
