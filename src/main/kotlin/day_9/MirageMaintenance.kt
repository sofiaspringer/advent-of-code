package day_9

import java.io.File

class MirageMaintenance(val filepath:String) {
    private val numbersRegex = """(-|)(?<numbers>[0-9]+)""".toRegex()

    fun missingLastNumbers(): List<Int> {
        val lines = File(filepath).readLines()

        return lines.map { line ->
            val sequence = numbersRegex.findAll(line).map { it.value.toInt() }.toList()

            sequence.missingLastSequenceNumber()
        }
    }

    private fun List<Int>.missingLastSequenceNumber():Int {
        val sequenceDifference = foldIndexed(emptyList<Int>()) { index, sequeceOfDiff, number ->
            if (index == size - 1)
                return@foldIndexed sequeceOfDiff

            sequeceOfDiff  + listOf(this[index + 1] - number)
        }

        return if (sequenceDifference.all { it == 0 })
            this.first()
        else
            this.last() + sequenceDifference.missingLastSequenceNumber()
    }

    private fun List<Int>.missingFirstSequenceNumber():Int {
        val sequenceDifference = foldIndexed(emptyList<Int>()) { index, sequeceOfDiff, number ->
            if (index == size - 1)
                return@foldIndexed sequeceOfDiff

            sequeceOfDiff  + listOf(this[index + 1] - number)
        }

        return if (sequenceDifference.all { it == 0 })
            this.first()
        else
            this.first() - sequenceDifference.missingFirstSequenceNumber()
    }

    fun missingFirstNumbers(): List<Int> {
        val lines = File(filepath).readLines()

        return lines.map { line ->
            val sequence = numbersRegex.findAll(line).map { it.value.toInt() }.toList()

            sequence.missingFirstSequenceNumber()
        }
    }
}