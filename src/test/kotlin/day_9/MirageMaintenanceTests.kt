package day_9

import kotlin.test.assertEquals
import org.junit.jupiter.api.Test

class MirageMaintenanceTests {

    @Test
    fun `missing last sequence number`() {
        val mirageMaintenance = MirageMaintenance("src/test/resources/mirage-maintenance-input")

        val missingNumbers = mirageMaintenance.missingLastNumbers()

        assertEquals(listOf(18,28,68), missingNumbers)
    }

    @Test
    fun `missing last sequence number with sequence with negative numbers`() {
        val mirageMaintenance = MirageMaintenance("src/test/resources/mirage-maintenance-input-2")

        val missingNumbers = mirageMaintenance.missingLastNumbers()

        assertEquals(listOf(-18), missingNumbers)
    }

    @Test
    fun `missing last sequence number - real file`() {
        val mirageMaintenance = MirageMaintenance("src/main/resources/mirage-maintenance-input")

        val missingNumbers = mirageMaintenance.missingLastNumbers()

        println(missingNumbers.sum())
    }

    @Test
    fun `missing first sequence number`() {
        val mirageMaintenance = MirageMaintenance("src/test/resources/mirage-maintenance-input")

        val missingNumbers = mirageMaintenance.missingFirstNumbers()

        assertEquals(listOf(-3, 0, 5), missingNumbers)
    }

    @Test
    fun `missing first sequence number with sequence with negative numbers - real file`() {
        val mirageMaintenance = MirageMaintenance("src/main/resources/mirage-maintenance-input")

        val missingNumbers = mirageMaintenance.missingFirstNumbers()

        println(missingNumbers.sum())
    }
}