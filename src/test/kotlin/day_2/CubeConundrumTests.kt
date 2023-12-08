package day_2

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class CubeConundrumTests {

    @Test
    fun `get valid game for bag`() {
        val cubeConundrum = CubeConundrum("src/test/resources/cube-conundrum-input")

        val goodGames = cubeConundrum.goodGamesForBag(Bag(redCubes = 12, greenCubes = 13, blueCubes = 14))

        assertEquals(listOf(1, 2, 5), goodGames)

        println(goodGames.sum())
    }


    @Test
    fun `get valid game for bag - real file`() {
        val cubeConundrum = CubeConundrum("src/main/resources/cube-conundrum-input")

        val goodGames = cubeConundrum.goodGamesForBag(Bag(redCubes = 12, greenCubes = 13, blueCubes = 14))

        println(goodGames.sum())

    }

    @Test
    fun `minimal bag per game`() {
        val cubeConundrum = CubeConundrum("src/test/resources/cube-conundrum-input")

        val bagsPerGame = cubeConundrum.minimalBagPerGame()

        assertEquals(
            listOf(
                Bag(4, 2, 6),
                Bag(1, 3, 4),
                Bag(20, 13, 6),
                Bag(14, 3, 15),
                Bag(6, 3, 2)
            ),
            bagsPerGame
        )

        assertEquals(2286, cubeConundrum.sumOfPowerOfCubes(bagsPerGame))
    }

    @Test
    fun `minimal bag per game - real file`() {
        val cubeConundrum = CubeConundrum("src/main/resources/cube-conundrum-input")

        val bagsPerGame = cubeConundrum.minimalBagPerGame()

        println(cubeConundrum.sumOfPowerOfCubes(bagsPerGame))
    }

}
