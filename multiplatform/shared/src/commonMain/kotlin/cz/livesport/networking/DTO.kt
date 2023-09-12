package cz.livesport.networking

import kotlinx.serialization.Serializable

@Serializable
internal data class MatchesDTO(
    val matches: List<MatchDTO>,
    val title: String,
    val roundLabel: String,
)

@Serializable
internal data class MatchDTO(
    val participantA: MatchParticipantDTO,
    val participantB: MatchParticipantDTO,
    val rounds: Int,
    val isFinished: Boolean,
)

@Serializable
internal data class MatchParticipantDTO(
    val name: String,
    val score: Int,
)
