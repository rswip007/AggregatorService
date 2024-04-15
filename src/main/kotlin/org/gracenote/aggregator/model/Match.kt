package org.gracenote.aggregator.model

data class Match(
    val id: Int,
    val competition: String,
    val date: String,
    val homeTeam: String,
    val awayTeam: String,
    val homeLineUp: List<Player>,
    val awayLineUp: List<Player>,
    val homeSubstitutions: List<Substitution>,
    val awaySubstitutions: List<Substitution>,
    val homeScore: Int,
    val awayScore: Int
){
    override fun toString(): String {
        return "Match(id=$id, competition='$competition', date='$date', homeTeam='$homeTeam', awayTeam='$awayTeam', homeLineUp=$homeLineUp, awayLineUp=$awayLineUp, homeSubstitutions=$homeSubstitutions, awaySubstitutions=$awaySubstitutions)"
    }
}

data class LeagueTable(
    val teams: List<Team>
)

data class Team(
    val name: String,
    val matchesPlayed: Int,
    val wins: Int,
    val draws: Int,
    val losses: Int,
    val goalsFor: Int,
    val goalsAgainst: Int,
    val points: Int
)

data class Substitution(
    val playerOut: Player,
    val playerIn: Player,
    val minute: Int
) {
    override fun toString(): String {
        return "Out: ${playerOut.name}, In: ${playerIn.name}, Minute: $minute"
    }
}

data class Player(
    var id: Int,
    var name: String,
    var function: String,
    var shirtNumber: Int,
    var team: String
) {
    override fun toString(): String {
        return name
    }
}

data class TeamStats(
    var matchesPlayed: Int = 0,
    var wins: Int = 0,
    var draws: Int = 0,
    var losses: Int = 0,
    var goalsFor: Int = 0,
    var goalsAgainst: Int = 0,
    var fouls: Int = 0
)

data class PlayerStats(
    var matchesPlayed: Int = 0,
    var goals: Int = 0,
    var assists: Int = 0,
    var fouls: Int = 0
)

data class GameScore(
    val homeTeam: String,
    val awayTeam: String,
    val homeScore: Int,
    val awayScore: Int
) {
    override fun toString(): String {
        return "$homeTeam: $homeScore, $awayTeam: $awayScore"
    }
}
