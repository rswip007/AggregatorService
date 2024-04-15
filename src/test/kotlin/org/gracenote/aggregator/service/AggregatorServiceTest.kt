package org.gracenote.aggregator.service

import org.gracenote.aggregator.model.Action
import org.gracenote.aggregator.org.gracenote.aggregator.service.AggregatorService
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class AggregatorServiceTest {
    private lateinit var aggregatorService: AggregatorService

    @BeforeEach
    fun setUp() {
        val actions = listOf(
            Action(actionId = 1, competition = "Eredivisie", matchId = 2174508, date = "2017-08-11", actionType = "Goal", period = "1", startTime = 10, endTime = 20, homeOrAway = "Home", teamId = 1, team = "Ajax", personId = 1, person = "Player 1", function = "Player", shirtNumber = 10, actionReason = "Shot", actionInfo = "Goal", subPersonId = null, subPerson = null),
            Action(actionId = 2, competition = "Eredivisie", matchId = 2174508, date = "2017-08-11", actionType = "Foul committed", period = "1", startTime = 30, endTime = 40, homeOrAway = "Away", teamId = 2, team = "PSV", personId = 2, person = "Player 2", function = "Player", shirtNumber = 9, actionReason = "Foul", actionInfo = "Yellow card", subPersonId = null, subPerson = null)
        )
        aggregatorService = AggregatorService(actions)
    }

    @Test
    fun `getLeagueTable returns correct league table`() {
        val leagueTable = aggregatorService.getLeagueTable()

        assertEquals(2, leagueTable.size)
        assertEquals(1, leagueTable["Ajax"]?.goalsFor)
        assertEquals(0, leagueTable["PSV"]?.goalsFor)
    }

    @Test
    fun `getPlayerStatistics returns correct player statistics`() {
        val player1Stats = aggregatorService.getPlayerStatistics("Player 1")
        val player2Stats = aggregatorService.getPlayerStatistics("Player 2")

        assertEquals(1, player1Stats?.goals)
        assertEquals(0, player1Stats?.fouls)

        assertEquals(0, player2Stats?.goals)
        assertEquals(1, player2Stats?.fouls)
    }
}