package org.gracenote.aggregator

import org.gracenote.aggregator.util.CSVReader
import org.gracenote.aggregator.org.gracenote.aggregator.service.AggregatorService

class AggregatorServiceApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            val reader = CSVReader()
            val resourcePath = "/Dataset-2rounds-Eredivie-20172018.csv"
            val actions = reader.readActions(resourcePath)

            val aggregatorService = AggregatorService(actions)

            // Print league table
            aggregatorService.printLeagueTable()

            println("-------------------------")

            // Print match details
            val matchId = 2174508 // replace with the actual match ID
            val matchDetails = aggregatorService.getMatchDetails(matchId)
            println("Match $matchId details: $matchDetails")

            println("-------------------------")

            val matchIDtoDisplay = 2174508 // replace with the actual match ID
            val gameScores = aggregatorService.getGameScores(matchIDtoDisplay)
            println("Game scores for match $matchIDtoDisplay: $gameScores")

            println("-------------------------")

            // Print player statistics
            val player = "Jeffry Fortes" // replace with the actual player name
            val playerStats = aggregatorService.getPlayerStatistics(player)
            println("Player $player statistics: $playerStats")

            println("-------------------------")

            // Top n Scorers
            val topScorers = aggregatorService.getTopScorers(2)
            println("Top Scorers: $topScorers")

            println("-------------------------")

            // Most n Most Assists
            val mostAssists = aggregatorService.getMostAssists(2)
            println("Most Assists: $mostAssists")

            println("-------------------------")
        }
    }
}