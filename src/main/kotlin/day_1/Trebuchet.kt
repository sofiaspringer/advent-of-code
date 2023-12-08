package day_1

import java.io.File

class Trebuchet(filepath: String) {
    private val file = File(filepath)

    operator fun invoke(): Int {
        val lines = file.readLines()
        val digits = lines
            .map { it.toSpelledNumbers() }
            .map { it.filter { char -> char.isDigit() } }
            .map { it.first().toString() + it.last().toString() }
            .map { it.toInt() }

        return digits.sum()
    }


    private fun String.toSpelledNumbers():String =
        spelledNumbersMap.keys.fold(this) { acc, spelledNumber ->
            if (acc.contains(spelledNumber)) {
                val separator = spelledNumber.first() + spelledNumbersMap[spelledNumber].toString() + spelledNumber.last()
                acc.split(spelledNumber).joinToString(separator) { it }
            } else
                acc
        }

    private val spelledNumbersMap = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9
    )
}
