package cz.livesport.rps

interface UIComponentModel<CONFIGURATION : Any> {
    val configuration: CONFIGURATION?
}

data class MatchRowComponentModel(
    val participantA: Participant,
    val participantB: Participant,
    val rounds: String,
    val isLive: Boolean,
    override val configuration: Unit? = null
): UIComponentModel<Unit> {

    data class Participant(
        val name: String,
        val score: String,
        val flag: String,
        val isWinner: Boolean,
    )
}
