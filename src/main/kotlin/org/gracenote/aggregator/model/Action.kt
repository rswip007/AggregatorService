package org.gracenote.aggregator.model

data class Action(
    var actionId: Int,
    var competition: String,
    var matchId: Int,
    var date: String,
    var actionType: String,
    var period: String,
    var startTime: Int,
    var endTime: Int,
    var homeOrAway: String,
    var teamId: Int,
    var team: String,
    var personId: Int,
    var person: String,
    var function: String,
    var shirtNumber: Int,
    var actionReason: String?,
    var actionInfo: String?,
    var subPersonId: Int?,
    var subPerson: String?
)