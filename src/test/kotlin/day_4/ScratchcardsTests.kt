package day_4

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class ScratchcardsTests {

    @Test
    fun `Scratchcards points`() {
        val scratchcards = Scratchcards("src/test/resources/scratchcards-input")

        val points = scratchcards.points()

        assertEquals(13, points)
    }

    @Test
    fun `Scratchcards points 2`() {
        val scratchcards = Scratchcards("src/test/resources/scratchcards-input-2")

        val points = scratchcards.points()

        assertEquals(512, points)
    }

    @Test
    fun `Total scratchcards`() {
        val scratchcards = Scratchcards("src/test/resources/scratchcards-input")

        val cards = scratchcards.total()

        assertEquals(30, cards.size)
    }

    @Test
    fun `Scratchcards points - real file`() {
        val scratchcards = Scratchcards("src/main/resources/scratchcards-input")

        val points = scratchcards.points()

        println(points)
    }
    @Test
    fun `Total Scratchcards - real file`() {
        val scratchcards = Scratchcards("src/main/resources/scratchcards-input")

        val points = scratchcards.total()

        println(points.size)
    }


    @Test
    fun `Total Scratchcards 3`() {
        val scratchcards = Scratchcards("src/test/resources/scratchcards-input-3")

        val points = scratchcards.total()

        println(points.size)
    }
}