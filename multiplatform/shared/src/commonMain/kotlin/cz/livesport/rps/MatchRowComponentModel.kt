package cz.livesport.rps

import cz.livesport.business.EmptyConfiguration
import cz.livesport.business.UIComponentModel

data class MatchRowComponentModel(
    val participantA: Participant,
    val participantB: Participant,
    val rounds: String,
    val isLive: Boolean,
    override val configuration: EmptyConfiguration? = null
) : UIComponentModel<EmptyConfiguration> {

    data class Participant(
        val name: String,
        val score: String,
        val flag: String,
        val isWinner: Boolean,
    )
}
