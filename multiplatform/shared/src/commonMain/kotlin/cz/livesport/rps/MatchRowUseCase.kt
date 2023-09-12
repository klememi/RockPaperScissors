package cz.livesport.rps

import cz.livesport.networking.MatchDTO
import cz.livesport.networking.MatchParticipantDTO
import cz.livesport.util.FlagProvider
import cz.livesport.util.FlagProviderImpl

internal interface UseCase<in INPUT, out OUTPUT> {
    fun createModel(dataModel: INPUT): OUTPUT
}

internal class MatchRowUseCase(
    private val flagProvider: FlagProvider = FlagProviderImpl(),
): UseCase<Pair<MatchDTO, String>, MatchRowComponentModel> {

    override fun createModel(dataModel: Pair<MatchDTO, String>): MatchRowComponentModel {
        val match = dataModel.first
        val roundLabel = dataModel.second
        return MatchRowComponentModel(
            participantA = createParticipant(match.participantA, match.participantB.score, match.isFinished),
            participantB = createParticipant(match.participantB, match.participantA.score, match.isFinished),
            rounds = "${roundLabel.uppercase()} ${match.rounds}",
            isLive = match.isFinished.not(),
        )
    }

    private fun createParticipant(
        model: MatchParticipantDTO,
        opponentScore: Int,
        isFinished: Boolean,
    ): MatchRowComponentModel.Participant =
        MatchRowComponentModel.Participant(
            name = model.name.replaceFirstChar { it.uppercase() },
            score = model.score.toString(),
            flag = flagProvider.getFlag(model.name.hashCode()),
            isWinner = (model.score > opponentScore) && isFinished,
        )
}