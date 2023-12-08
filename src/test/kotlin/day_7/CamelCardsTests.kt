package day_7

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test


class CamelCardsTests {

    @Test
    fun `camel cards ranks`() {
        val camelCards = CamelCards("src/test/resources/camel-cards-input")

        val ranks = camelCards.ranks()

        assertEquals(mapOf(
            "32T3K" to 1,
            "KTJJT" to 2,
            "KK677" to 3,
            "T55J5" to 4,
            "QQQJA" to 5
            ), ranks)
    }

    @Test
    fun `camel cards ranks with Joker`() {
        val camelCards = CamelCards("src/test/resources/camel-cards-input")

        val ranks = camelCards.ranksWithJoker()

        assertEquals(mapOf(
            "32T3K" to 1,
            "KK677" to 2,
            "T55J5" to 3,
            "QQQJA" to 4,
            "KTJJT" to 5,
        ), ranks)
    }

    @Test
    fun `camel cards total winnings`() {
        val camelCards = CamelCards("src/test/resources/camel-cards-input")

        val winnings = camelCards.totalWinnings()

        assertEquals(6440, winnings)
    }

    @Test
    fun `camel cards total winnings with joker`() {
        val camelCards = CamelCards("src/test/resources/camel-cards-input")

        val winnings = camelCards.totalWinningsWithJoker()

        assertEquals(5905, winnings)
    }

    @Test
    fun `camel cards total winnings with joker 2`() {
        val camelCards = CamelCards("src/test/resources/camel-cards-input-2")

        val winnings = camelCards.totalWinningsWithJoker()

        assertEquals(6839, winnings)
    }

    @Test
    fun `camel cards total winnings - real file`() {
        val camelCards = CamelCards("src/main/resources/camel-cards-input")

        val winnings = camelCards.totalWinnings()

        println(winnings)
    }

    @Test
    fun `camel cards total winnings with joker - real file`() {
        val camelCards = CamelCards("src/main/resources/camel-cards-input")

        val winnings = camelCards.totalWinningsWithJoker()

        println(winnings)
    }
}