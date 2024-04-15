package org.gracenote.aggregator.util

import org.gracenote.aggregator.model.Action
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.FileNotFoundException

class CSVReaderTest {
    private val csvReader = CSVReader()

    @Test
    fun `readActions returns list of Action objects when CSV file is found`() {
        val actions = csvReader.readActions("/Dataset-2rounds-Eredivie-20172018.csv")

        assertTrue(actions.isNotEmpty())
        assertTrue(actions.all { it is Action })
    }

    @Test
    fun `readActions throws FileNotFoundException when CSV file is not found`() {
        assertThrows<FileNotFoundException> {
            csvReader.readActions("/invalid/path/to/csv/file.csv")
        }
    }
}