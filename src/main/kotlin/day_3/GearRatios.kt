package day_3

import java.io.File

class GearRatios(val filepath: String) {

    fun partNumbers(): List<Int> {
        val lines = File(filepath).readLines()

        return lines.flatMapIndexed { lineNumber, line ->
            val numbers = """(?<numbers>[0-9]+)""".toRegex().findAll(line)

            numbers.map { matcher ->
                val number = matcher.value

                val startIndexForNumber = matcher.range.first
                val endIndex =
                    if (matcher.range.last < line.length - 1) matcher.range.last + 2 else matcher.range.last + 1

                val startIndex = if (startIndexForNumber > 0) startIndexForNumber - 1 else 0

                val symbolsInSameLine = line.hasPartNumber(startIndex, endIndex)

                val symbolsInLineBelow = lines.getOrNull(lineNumber + 1)?.hasPartNumber(startIndex, endIndex) ?: false

                val symbolsInLineAbove = lines.getOrNull(lineNumber - 1)?.hasPartNumber(startIndex, endIndex) ?: false

                Pair(number, symbolsInSameLine || symbolsInLineAbove || symbolsInLineBelow)
            }
                .filter { it.second }
                .map { it.first.toInt() }
        }
    }

    private fun String.hasPartNumber(startIndex: Int, endIndex: Int): Boolean =
        this.substring(startIndex, endIndex)
            .any { !it.isDigit() && it != '.' && it != '\n' }


    operator fun invoke(): List<Int> {
        val lines = File(filepath).readLines()

        return lines.flatMapIndexed { lineNumber, line ->
            val gears = """\*""".toRegex().findAll(line)

            gears.map { gearMatcher ->
                val numbersLineAbove = lines.getOrNull(lineNumber - 1)?.let {
                    it.adjacentNumbers(gearMatcher)
                } ?: emptyList()

                val numbersSameLine = lines.getOrNull(lineNumber)?.let {
                    it.adjacentNumbers(gearMatcher)
                } ?: emptyList()

                val numbersLineBelow = lines.getOrNull(lineNumber + 1)?.let {
                    it.adjacentNumbers(gearMatcher)
                } ?: emptyList()

                val numbers = numbersLineAbove + numbersSameLine + numbersLineBelow

                if (numbers.size == 2) numbers.let { it.first().times(it.last()) }
                else 0
            }
        }.filter { it != 0 }
    }

    private val numbersRegex = """(?<numbers>[0-9]+)""".toRegex()

    private fun String.adjacentNumbers(gearMatcher: MatchResult) = numbersRegex.findAll(this).filter { number ->
        gearMatcher.range.first >= number.range.first - 1 && gearMatcher.range.first <= number.range.last + 1
    }.map { it.value.toInt() }.toList()
}
