package cz.livesport.rps

interface UIComponentModel

data class MatchRowComponentModel(
    val participantA: Participant,
    val participantB: Participant,
    val rounds: String,
    val isLive: Boolean,
): UIComponentModel {

    data class Participant(
        val name: String,
        val score: String,
        val flag: String,
        val isWinner: Boolean,
    )
}
