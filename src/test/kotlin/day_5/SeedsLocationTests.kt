package day_5

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class SeedsLocationTests {

    @Test
    fun `location per seed`(){
        val seedsLocation = SeedsLocation("src/test/resources/seeds-location-input")

        val location = seedsLocation.location()

        assertEquals(35, location)
    }

    @Test
    fun `location per seed - with range of seeds`(){
        val seedsLocation = SeedsLocation("src/test/resources/seeds-location-input")

        val location = seedsLocation.minimumLocation()

        assertEquals(46, location)
    }

    @Test
    fun `location per seed - real file`(){
        val seedsLocation = SeedsLocation("src/main/resources/seeds-location-input")

        val seeds = seedsLocation.minimumLocation()

        println(seeds)
    }

}