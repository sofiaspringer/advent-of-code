package day_1

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class TrebuchetTests {

    @Test
    fun `trebuchet sum`() {
        val trebuchet = Trebuchet("src/test/resources/trebuchet-input")

        val sum = trebuchet()

        assertEquals(142, sum)
    }

    @Test
    fun `real file - 1`(){
        val trebuchet = Trebuchet("src/main/resources/trebuchet-input")

        val sum = trebuchet()

        println(sum)
    }

    @Test
    fun `sum with spelled digits`() {
        val trebuchet = Trebuchet("src/test/resources/trebuchet-input-spelled-digits")

        val sum = trebuchet()

        assertEquals(281, sum)
    }
}



