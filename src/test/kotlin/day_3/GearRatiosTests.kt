package day_3

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class GearRatiosTests {
    @Test
    fun `sum part numbers`() {
        val gearRatios = GearRatios("src/test/resources/gear-ratios-input")

        val partNumbers = gearRatios.partNumbers()

        assertEquals(listOf(467,35,633,617,592,755,664,598), partNumbers)
        assertEquals(4361, partNumbers.sum())
    }

    @Test
    fun `sum part numbers 2`() {
        val gearRatios = GearRatios("src/test/resources/gear-ratios-input-2")

        val partNumbers = gearRatios.partNumbers()

        println(partNumbers)

        assertEquals(40, partNumbers.sum())
    }

    @Test
    fun `sum part numbers - real file`() {
        val gearRatios = GearRatios("src/main/resources/gear-ratios-input")

        val partNumbers = gearRatios.partNumbers()

        println(partNumbers)
        println(partNumbers.sum())
    }

    @Test
    fun `gear ratios`() {
        val gearRatios = GearRatios("src/test/resources/gear-ratios-input")

        val ratios = gearRatios()

        assertEquals(listOf(16345, 451490), ratios)
        assertEquals(467835, ratios.sum())
    }

    @Test
    fun `gear ratios - real file`() {
        val gearRatios = GearRatios("src/main/resources/gear-ratios-input")

        val ratios = gearRatios()

        println(ratios.sum())
    }
}
