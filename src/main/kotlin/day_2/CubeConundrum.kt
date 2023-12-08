package day_2

import java.io.File

class CubeConundrum(private val filepath: String) {

    fun goodGamesForBag(bag: Bag): List<Int> {
        val games = File(filepath).readLines()

        return games.map { game ->
            val gameNumber = """\b([1-9]|[1-9][0-9]|100)\b: """.toRegex().find(game)?.value?.split(":")?.first()
                ?: error("bad format")
            val sets = """Game \b([1-9]|[1-9][0-9]|100)\b: """.toRegex().replace(game, "")
                .split("; ")

            val setsValidity = sets.map { balls ->
                val red =
                    """\b([1-9]|[1-9][0-9]|100)\b red""".toRegex().find(balls)?.value?.split(" ")?.first()?.toInt() ?: 0
                val green =
                    """\b([1-9]|[1-9][0-9]|100)\b green""".toRegex().find(balls)?.value?.split(" ")?.first()?.toInt()
                        ?: 0
                val blue =
                    """\b([1-9]|[1-9][0-9]|100)\b blue""".toRegex().find(balls)?.value?.split(" ")?.first()?.toInt()
                        ?: 0

                Bag(red, green, blue)
            }.map {
                !(it.redCubes == 0 && it.greenCubes == 0 && it.blueCubes == 0) &&
                        (it.redCubes == 0 || it.redCubes <= bag.redCubes) &&
                        (it.greenCubes == 0 || it.greenCubes <= bag.greenCubes) &&
                        (it.blueCubes == 0 || it.blueCubes <= bag.blueCubes)
            }

            gameNumber to setsValidity.all { it }
        }.filter {
            it.second
        }.map { it.first.toInt() }
    }

    fun minimalBagPerGame(): List<Bag> {
        val games = File(filepath).readLines()

        return games.map { game ->
            val sets = """Game \b([1-9]|[1-9][0-9]|100)\b: """.toRegex().replace(game, "")
                .split("; ")

            val bagsPerSet = sets.map { balls ->
                val red =
                    """\b([1-9]|[1-9][0-9]|100)\b red""".toRegex().find(balls)?.value?.split(" ")?.first()?.toInt() ?: 0
                val green =
                    """\b([1-9]|[1-9][0-9]|100)\b green""".toRegex().find(balls)?.value?.split(" ")?.first()?.toInt()
                        ?: 0
                val blue =
                    """\b([1-9]|[1-9][0-9]|100)\b blue""".toRegex().find(balls)?.value?.split(" ")?.first()?.toInt()
                        ?: 0

                Bag(red, green, blue)
            }

            bagsPerSet.fold(Bag(0,0,0)) { minimalBag, bag ->
                val minimalRedCubesNumber = minimalNumberOfCubes(minimalBag.redCubes, bag.redCubes)
                val minimalGreenCubesNumber = minimalNumberOfCubes(minimalBag.greenCubes, bag.greenCubes)
                val minimalBlueCubesNumber = minimalNumberOfCubes(minimalBag.blueCubes, bag.blueCubes)

                Bag(minimalRedCubesNumber, minimalGreenCubesNumber, minimalBlueCubesNumber)
            }
        }
    }

    fun sumOfPowerOfCubes(bags: List<Bag>):Int = bags.sumOf { it.redCubes * it.greenCubes * it.blueCubes }

    private fun minimalNumberOfCubes(currentMinimalNumberCubes: Int, numberOfCubes: Int) =
        if (currentMinimalNumberCubes < numberOfCubes)
            numberOfCubes
        else currentMinimalNumberCubes

}

data class Bag(val redCubes: Int, val greenCubes: Int, val blueCubes: Int)
