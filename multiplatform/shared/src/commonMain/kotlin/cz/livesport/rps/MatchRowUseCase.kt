package cz.livesport.rps

import cz.livesport.business.UseCase
import cz.livesport.networking.MatchDTO
import cz.livesport.networking.MatchParticipantDTO

internal class MatchRowUseCase(
    private val flagProvider: FlagProvider = FlagProviderImpl(),
): UseCase<MatchDTO, MatchRowComponentModel> {

    override fun createModel(dataModel: MatchDTO): MatchRowComponentModel =
        MatchRowComponentModel(
            participantA = createParticipant(dataModel.participantA, dataModel.participantB.score, dataModel.isFinished),
            participantB = createParticipant(dataModel.participantB, dataModel.participantA.score, dataModel.isFinished),
            rounds = "ROUND ${dataModel.rounds}",
            isLive = dataModel.isFinished.not(),
        )

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