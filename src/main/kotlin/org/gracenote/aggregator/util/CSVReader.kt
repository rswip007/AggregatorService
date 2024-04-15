package org.gracenote.aggregator.util

import org.apache.commons.csv.CSVFormat
import org.gracenote.aggregator.model.Action
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStreamReader

class CSVReader {

    fun readActions(resourcePath: String): List<Action> {
        val actions = mutableListOf<Action>()
        val url = this::class.java.getResource(resourcePath)
        if (url == null || !File(url.toURI()).exists()) {
            throw FileNotFoundException("CSV file not found at path: $resourcePath")
        }
        InputStreamReader(url.openStream()).use { inReader ->
            CSVFormat.EXCEL.withFirstRecordAsHeader().parse(inReader).forEach { record ->
                val action = Action(
                    actionId = record.get("n_actionid").toInt(),
                    competition = record.get("c_competition"),
                    matchId = record.get("n_Matchid").toInt(),
                    date = record.get("d_date"),
                    actionType = record.get("c_Action"),
                    period = record.get("c_Period"),
                    startTime = record.get("n_StartTime").toIntOrNull() ?: 0,
                    endTime = record.get("n_Endtime").toIntOrNull() ?: 0,
                    homeOrAway = record.get("c_HomeOrAway"),
                    teamId = record.get("n_TeamID").toInt(),
                    team = record.get("c_Team"),
                    personId = record.get("n_personid").toInt(),
                    person = record.get("c_person"),
                    function = record.get("c_Function"),
                    shirtNumber = record.get("n_ShirtNr").toIntOrNull() ?: 0,
                    actionReason = record.get("c_ActionReason"),
                    actionInfo = record.get("c_actionInfo"),
                    subPersonId = record.get("n_Subpersonid").toIntOrNull(),
                    subPerson = record.get("c_Subperson")
                )
                actions.add(action)
            }
        }
        return actions
    }
}