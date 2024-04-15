package org.gracenote.aggregator.org.gracenote.aggregator.service

import org.gracenote.aggregator.model.*

class AggregatorService(private val actions: List<Action>) {
    private val leagueTable = mutableMapOf<String, TeamStats>()
    private val playerStats = mutableMapOf<String, PlayerStats>()

    init {
        processActions()
    }

    private fun processActions() {
        for (action in actions) {
            when (action.actionType) {
                "Goal" -> {
                    leagueTable.getOrPut(action.team) { TeamStats() }.goalsFor++
                    playerStats.getOrPut(action.person) { PlayerStats() }.goals++
                }
                "Foul committed" -> {
                    playerStats.getOrPut(action.person) { PlayerStats() }.fouls++
                }
            }
        }
    }

    fun getMatchDetails(matchId: Int): Match? {
        val matchActions = actions.filter { it.matchId == matchId }
        val homeTeam = matchActions.firstOrNull { it.homeOrAway == "Home" }?.team
        val awayTeam = matchActions.firstOrNull { it.homeOrAway == "Away" }?.team
        val homeLineUp = matchActions.filter { it.actionType == "Line-up" && it.homeOrAway == "Home" }
            .map { Player(it.personId, it.person, it.function, it.shirtNumber, it.team) }
        val awayLineUp = matchActions.filter { it.actionType == "Line-up" && it.homeOrAway == "Away" }
            .map { Player(it.personId, it.person, it.function, it.shirtNumber, it.team) }
        val homeSubstitutions = matchActions.filter { it.actionType == "Substitution" && it.homeOrAway == "Home" }
            .map { Substitution(Player(it.personId, it.person, it.function, it.shirtNumber, it.team), Player(it.subPersonId ?: 0, it.subPerson ?: "", "", 0, ""), it.startTime) }
        val awaySubstitutions = matchActions.filter { it.actionType == "Substitution" && it.homeOrAway == "Away" }
            .map { Substitution(Player(it.personId, it.person, it.function, it.shirtNumber, it.team), Player(it.subPersonId ?: 0, it.subPerson ?: "", "", 0, ""), it.startTime) }
        val homeScore = matchActions.count { it.actionType == "Goal" && it.homeOrAway == "Home" }
        val awayScore = matchActions.count { it.actionType == "Goal" && it.homeOrAway == "Away" }

        return if (homeTeam != null && awayTeam != null) {
            Match(
                id = matchId,
                competition = matchActions.first().competition,
                date = matchActions.first().date,
                homeTeam = homeTeam,
                awayTeam = awayTeam,
                homeLineUp = homeLineUp,
                awayLineUp = awayLineUp,
                homeSubstitutions = homeSubstitutions,
                awaySubstitutions = awaySubstitutions,
                homeScore = homeScore,
                awayScore = awayScore
            )
        } else {
            null
        }
    }

    fun getLeagueTable(): Map<String, TeamStats> {
        val leagueTable = mutableMapOf<String, TeamStats>()
        actions.forEach { action ->
            val teamStats = leagueTable.getOrDefault(action.team, TeamStats())
            when (action.actionType) {
                "Goal" -> teamStats.goalsFor++
                "Foul committed" -> teamStats.fouls++
            }
            leagueTable[action.team] = teamStats
        }
        return leagueTable
    }

    fun getPlayerStatistics(player: String): PlayerStats? {
        return playerStats[player]
    }

    fun printLeagueTable() {
        println("League table:")
        leagueTable.forEach { (team, stats) ->
            println("$team: $stats")
        }
    }

    fun getGameScores(matchId: Int): GameScore? {
        val matchActions = actions.filter { it.matchId == matchId }
        val homeTeam = matchActions.firstOrNull { it.homeOrAway == "Home" }?.team
        val awayTeam = matchActions.firstOrNull { it.homeOrAway == "Away" }?.team
        val homeScore = matchActions.count { it.actionType == "Goal" && it.homeOrAway == "Home" }
        val awayScore = matchActions.count { it.actionType == "Goal" && it.homeOrAway == "Away" }

        return if (homeTeam != null && awayTeam != null) {
            GameScore(
                homeTeam = homeTeam,
                awayTeam = awayTeam,
                homeScore = homeScore,
                awayScore = awayScore
            )
        } else {
            null
        }
    }

    fun getTopScorers(n: Int): Map<String, Int> {
        return playerStats.entries.sortedByDescending { it.value.goals }.take(n).associateBy({ it.key }, { it.value.goals })
    }

    fun getMostAssists(n: Int): Map<String, Int> {
        return playerStats.entries.sortedByDescending { it.value.assists }.take(n).associateBy({ it.key }, { it.value.assists })
    }
}