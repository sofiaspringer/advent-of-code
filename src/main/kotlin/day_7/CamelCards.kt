package day_7

import day_7.CamelCards.HandType.*
import java.io.File

class CamelCards(filepath: String) {
    private val handsToBidMap: Map<String, Int> = File(filepath).readLines().map {
        val handAndBid = it.split(" ")
        handAndBid.first() to handAndBid.last().toInt()
    }.toMap()

    private val cardsToStrength = setOf('A', 'K', 'Q', 'J', 'T', '9', '8', '7', '6', '5', '4', '3', '2')
    private val cardsWithJokerToStrength = setOf('A', 'K', 'Q', 'T', '9', '8', '7', '6', '5', '4', '3', '2', 'J')

    fun ranks(): Map<String, Int> = handsToBidMap.keys
        .map { it to it.toHandType() }
        .sortedWith(
            compareBy<Pair<String, HandType>> { it.second.strength }
                .then(cardStrengthComparator)
        )
        .mapIndexed { index, it -> it.first to index + 1 }
        .toMap()

    fun totalWinnings(): Int = handsToBidMap.map { it.value * ranks()[it.key]!! }.sum()

    fun ranksWithJoker(): Map<String, Int> {
        val bla = handsToBidMap.map { it.key }
            .map { it to it.replaceJokerWithMostFrequentCard().toHandType() }
            .sortedWith(cardsWithJokerStrengthComparator)
        return bla
            .mapIndexed { index, it -> it.first to index + 1 }
            .toMap()
    }

    fun totalWinningsWithJoker(): Int = handsToBidMap.map { it.value * ranksWithJoker()[it.key]!! }.sum()

    private fun String.toHandType(): HandType {
        val handArray = this.toCharArray().sortedArray()
        val mostFrequentCard = handArray.getMostFrequestCard()
        val highestNumberSimilarCards = handArray.filter { it == mostFrequentCard }.size

        return when (highestNumberSimilarCards) {
            5 -> FIVE_OF_A_KIND
            4 -> FOUR_OF_A_KIND
            3 -> {
                val card = handArray.first { it != mostFrequentCard }

                if (handArray.all { it == mostFrequentCard || it == card })
                    FULL_HOUSE
                else THREE_OF_A_KIND
            }

            2 -> {
                val remainingNumberTypes = handArray.filter { it != mostFrequentCard }.toSet().size

                if (remainingNumberTypes == 2)
                    TWO_PAIR
                else ONE_PAIR
            }

            else -> HIGH_CARD
        }
    }

    private fun String.replaceJokerWithMostFrequentCard(): String {
        val handArray = this.toCharArray()

        if (handArray.none { it == 'J' }) return this
        if (handArray.all { it == 'J' }) return this

        val mostFrequentCard = handArray.getMostFrequentCardNotJoker()


        return handArray.fold("".toCharArray()) { currentHand, char ->
            if (char == 'J') currentHand + mostFrequentCard
            else currentHand + char
        }.concatToString()
    }

    private fun CharArray.getMostFrequestCard(): Char {
        return this.foldIndexed(this.first()) { index, mostFrequentCard, card ->
            val numberSimilarCards = this.filter { it == card }.size
            val currentHighestNumberSimilarCard = this.filter { it == mostFrequentCard }.size

            if (numberSimilarCards > currentHighestNumberSimilarCard) card
            else mostFrequentCard
        }
    }

    private fun CharArray.getMostFrequentCardNotJoker(): Char {
        val handWithoutJokerCard = this.filterNot { it == 'J' }
        return handWithoutJokerCard.foldIndexed(handWithoutJokerCard.first()) { index, mostFrequentCard, card ->
            val numberSimilarCards = this.filter { it == card }.size
            val currentHighestNumberSimilarCard = this.filter { it == mostFrequentCard }.size

            if (numberSimilarCards > currentHighestNumberSimilarCard) card
            else mostFrequentCard
        }
    }

    private val cardStrengthComparator = Comparator<Pair<String, HandType>> { hand1, hand2 ->
        val cardsStrength1 = hand1.first.toCharArray().map { cardsToStrength.indexOf(it) }
        val cardsStrength2 = hand2.first.toCharArray().map { cardsToStrength.indexOf(it) }

        cardsStrength1.indices.forEach { index ->
            val strengthDiffCard = cardsStrength2[index] - cardsStrength1[index]

            if (strengthDiffCard != 0)
                return@Comparator strengthDiffCard
        }

        return@Comparator 0
    }

    private val cardsWithJokerStrengthComparator = Comparator<Pair<String, HandType>> { hand1, hand2 ->
        val strengthDiffHandType = hand1.second.strength - hand2.second.strength

        if (strengthDiffHandType != 0) return@Comparator strengthDiffHandType

        val cardsStrength1 = hand1.first.toCharArray().map { cardsWithJokerToStrength.indexOf(it) }
        val cardsStrength2 = hand2.first.toCharArray().map { cardsWithJokerToStrength.indexOf(it) }

        cardsStrength1.indices.forEach { index ->
            val strengthDiffCard = cardsStrength2[index] - cardsStrength1[index]

            if (strengthDiffCard != 0)
                return@Comparator strengthDiffCard
        }

        return@Comparator 0
    }

    private enum class HandType(val strength: Int) {
        FIVE_OF_A_KIND(7),
        FOUR_OF_A_KIND(6),
        FULL_HOUSE(5),
        THREE_OF_A_KIND(4),
        TWO_PAIR(3),
        ONE_PAIR(2),
        HIGH_CARD(1)
    }

}
