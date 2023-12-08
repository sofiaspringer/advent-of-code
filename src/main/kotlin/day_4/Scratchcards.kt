package day_4

import java.io.File
import kotlin.math.pow

class Scratchcards(val filepath: String) {
    private val numbersRegex = """(?<numbers>[0-9]+)""".toRegex()

    fun points(): Int {
        val lines = File(filepath).readLines()

        return lines.sumOf { line ->
            val numbers = """(?<numbers>[0-9]+): """.toRegex().replace(line, "")
                .split(" | ")


            val winningNumbers = numbers.first().let {
                numbersRegex.findAll(it)
            }.map { it.value.toInt() }.toList()

            val elfNumbers = numbers.last().let {
                numbersRegex.findAll(it)
            }.map { it.value.toInt() }.toList()

            val elfWinningNumbers = winningNumbers.filter { elfNumbers.contains(it) }

            val points =
                if (elfWinningNumbers.isEmpty()) 0.0
                else 2.0.pow(elfWinningNumbers.size - 1)

            points
        }.toInt()
    }

    fun total(): List<Card> {
        val lines = File(filepath).readLines()

        val cards = lines.map { line ->
            val numbers = """(?<numbers>[0-9]+): """.toRegex().replace(line, "")
                .split(" | ")

            val winningNumbers = numbers.first().let {
                numbersRegex.findAll(it)
            }.map { it.value.toInt() }.toList()

            val elfNumbers = numbers.last().let {
                numbersRegex.findAll(it)
            }.map { it.value.toInt() }.toList()

            val cardNumber = """(?<numbers>[0-9]+)""".toRegex().find(line)?.value?.toInt() ?: 0

            Card(cardNumber, winningNumbers, elfNumbers)
        }

        return matchingCards(cards, cards)
    }

    data class Card(val cardNumber: Int, val winningNumbers: List<Int>, val elfNumbers: List<Int>) {
        val numberCopies = winningNumbers.filter { elfNumbers.contains(it) }.size
    }

    private fun matchingCards(cards: List<Card>, allCards: List<Card>): List<Card> {
        return cards.flatMapIndexed { index, card ->
            val cardCopies = mutableListOf<Card>()
            for (i in 1..card.numberCopies) {
                if (card.cardNumber + i <= allCards.size)
                    cardCopies.add(allCards[card.cardNumber + i - 1])
                else break
            }

            if (cardCopies.isEmpty()) listOf(card)
            else listOf(card) + matchingCards(cardCopies, allCards)
        }
    }
}