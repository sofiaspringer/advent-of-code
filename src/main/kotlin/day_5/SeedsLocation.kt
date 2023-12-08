package day_5

import java.io.File
import kotlin.math.max
import kotlin.math.min

class SeedsLocation(val filepath: String) {
    private val numbersRegex = """(?<numbers>[0-9]+)""".toRegex()

    fun minimumLocation(): Long {
        val lines = File(filepath).readLines().joinToString(",", prefix = "") { it }

        val maps =
            """soil-to-fertilizer|seed-to-soil|fertilizer-to-water|water-to-light|light-to-temperature|temperature-to-humidity|humidity-to-location""".toRegex()
                .replace(lines, "")
                .split("map:")

        val seedsNumbers = maps[0].let { numbersRegex.findAll(it) }.map { it.value.toLong() }.toList()
        val seedRanges = (seedsNumbers.indices step 2).map { index ->
            Range(seedsNumbers[index], seedsNumbers[index] + seedsNumbers[index + 1] - 1)
        }
        val seedsToSoil = maps[1].transformToAlmanacItems()
        val soilToFertilizer = maps[2].transformToAlmanacItems()
        val fertilizerToWater = maps[3].transformToAlmanacItems()
        val waterToLight = maps[4].transformToAlmanacItems()
        val lightToTemperature = maps[5].transformToAlmanacItems()
        val temperatureToHumidity = maps[6].transformToAlmanacItems()
        val humidityToLocation = maps[7].transformToAlmanacItems()


        val soilRanges = seedRanges.toDestinationRanges(seedsToSoil).toMap()
        val fertilizerRanges = soilRanges.values.toDestinationRanges(soilToFertilizer).toMap()
        val waterRanges = fertilizerRanges.values.toDestinationRanges(fertilizerToWater).toMap()
        val lightRanges = waterRanges.values.toDestinationRanges(waterToLight).toMap()
        val temperatureRanges = lightRanges.values.toDestinationRanges(lightToTemperature).toMap()
        val humidityRanges = temperatureRanges.values.toDestinationRanges(temperatureToHumidity).toMap()
        val locationRanges = humidityRanges.values.toDestinationRanges(humidityToLocation).toMap()

        return locationRanges.minOf { it.value.start }
    }

    fun location(): Long {
        val lines = File(filepath).readLines().joinToString(",", prefix = "") { it }

        val maps =
            """soil-to-fertilizer|seed-to-soil|fertilizer-to-water|water-to-light|light-to-temperature|temperature-to-humidity|humidity-to-location""".toRegex()
                .replace(lines, "")
                .split("map:")

        val seedsNumbers = maps[0].let { numbersRegex.findAll(it) }.map { it.value.toLong() }.toList()
        val seedsToSoil = maps[1].transformToAlmanacItems()
        val soilToFertilizer = maps[2].transformToAlmanacItems()
        val fertilizerToWater = maps[3].transformToAlmanacItems()
        val waterToLight = maps[4].transformToAlmanacItems()
        val lightToTemperature = maps[5].transformToAlmanacItems()
        val temperatureToHumidity = maps[6].transformToAlmanacItems()
        val humidityToLocation = maps[7].transformToAlmanacItems()

        return seedsNumbers.fold(Long.MAX_VALUE) { minimumLocation, seedNumber ->
            val soil = seedNumber.toDestination(seedsToSoil)
            val fertilizer = soil.toDestination(soilToFertilizer)
            val water = fertilizer.toDestination(fertilizerToWater)
            val light = water.toDestination(waterToLight)
            val temperature = light.toDestination(lightToTemperature)
            val humidity = temperature.toDestination(temperatureToHumidity)
            val location = humidity.toDestination(humidityToLocation)

            if (minimumLocation > location) location
            else minimumLocation
        }
    }

    private fun Collection<Range>.toDestinationRanges(sourceToDestinationAlmanac: List<AlmanacItem>): List<Pair<Range, Range>> =
        this.flatMap { range ->
            val innerRanges = sourceToDestinationAlmanac.filter { it.source + it.range > range.start && it.source  <= range.end}
                .map {
                    val sourceRange = Range(max(range.start, it.source), min(it.source + it.range - 1, range.end))
                    val destinationRange = Range(
                        start = it.destination + sourceRange.start - it.source,
                        end = it.destination + sourceRange.start - it.source + sourceRange.end - sourceRange.start
                    )
                    sourceRange to destinationRange
                }.sortedBy { it.first.start }


            if (innerRanges.isEmpty()) listOf(range to range)
            else innerRanges.foldIndexed(innerRanges) { index, ranges, currentRange ->
                if (currentRange != innerRanges.last()) {
                    if (currentRange.first.end < innerRanges[index + 1].first.start - 1) {
                        ranges + listOf(
                            Range(currentRange.first.end + 1, innerRanges[index + 1].first.start - 1) to Range(currentRange.first.end + 1, innerRanges[index + 1].first.start - 1)
                        )
                    } else ranges
                } else if (currentRange.first.end < range.end) {
                    ranges + listOf(Range(currentRange.first.end + 1, range.end) to Range(currentRange.first.end + 1, range.end))
                } else ranges

            }


        }.let {
            val minRange = it.minOf { it.first.start }
            val expectedMinRange = this.minOf { it.start }

            if(minRange > expectedMinRange) {
                it + listOf(Range(expectedMinRange, minRange - 1) to Range(expectedMinRange, minRange - 1))
            } else it
        }.let {
            it.ifEmpty { this.map {range -> range to range }}
        }


    private fun String.transformToAlmanacItems() = split(",").filter { it.isNotBlank() }.map {
        val numbers = numbersRegex.findAll(it).map { number -> number.value.toLong() }.toList()
        AlmanacItem(destination = numbers[0], source = numbers[1], range = numbers[2])
    }

    private fun Long.toDestination(items: List<AlmanacItem>): Long = items.singleOrNull {
        this >= it.source && this < it.source + it.range
    }?.let {
        it.destination + this - it.source
    } ?: this
}

data class Range(val start: Long, val end: Long)
data class AlmanacItem(val destination: Long, val source: Long, val range: Long)