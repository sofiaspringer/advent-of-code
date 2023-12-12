package day_8

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class HauntedWastelandTests {

    @Test
    fun `nodes from start to end`() {
        val hauntedWasteland = HauntedWasteland("src/test/resources/haunted-wasteland-input")

        val nodes = hauntedWasteland.nodes()

        assertEquals(
            listOf(
                "AAA", "CCC", "ZZZ"
            ), nodes
        )
    }

    @Test
    fun `number of steps from start to end`() {
        val hauntedWasteland = HauntedWasteland("src/test/resources/haunted-wasteland-input")

        val numberSteps = hauntedWasteland.steps()

        assertEquals(2, numberSteps)
    }

    @Test
    fun `number steps - real file`() {
        val hauntedWasteland = HauntedWasteland("src/main/resources/haunted-wasteland-input")

        val nodes = hauntedWasteland.nodes()
        val steps = hauntedWasteland.steps()

        println(steps)
    }

    @Test
    fun `minimum steps - file 2`() {
        val hauntedWasteland = HauntedWasteland("src/test/resources/haunted-wasteland-input-2")

        val minimumSteps = hauntedWasteland.minimumStepsGhosts()

        assertEquals(6, minimumSteps)
    }

    @Test
    fun `minimum steps - real file`() {
        val hauntedWasteland = HauntedWasteland("src/main/resources/haunted-wasteland-input")

        val minimumSteps = hauntedWasteland.minimumStepsGhosts()

        println(minimumSteps)
    }
}