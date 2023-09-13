package cz.livesport.rps

import cz.livesport.networking.MatchDTO
import cz.livesport.networking.MatchParticipantDTO
import cz.livesport.networking.MatchesDTO
import cz.livesport.util.FlagProvider
import cz.livesport.util.FlagProviderImpl
import cz.livesport.util.Language
import cz.livesport.util.Project

internal interface ViewStateFactory<MODEL : Any, STATE : Any, VIEW_STATE : Any> {
    fun create(model: MODEL, state: STATE): VIEW_STATE
}

data class RPSViewState(
    val title: String,
    val rows: List<MatchRowComponentModel>,
    val language: Language,
    val project: Project,
)

internal class RPSViewStateFactory(
    private val flagProvider: FlagProvider = FlagProviderImpl(),
): ViewStateFactory<MatchesDTO, RPSStateManager.State, RPSViewState> {

    override fun create(model: MatchesDTO, state: RPSStateManager.State): RPSViewState =
        RPSViewState(
            title = model.title,
            rows = model.matches.map { createRow(it to model.roundLabel) },
            language = state.language,
            project = state.project,
        )

    private fun createRow(dataModel: Pair<MatchDTO, String>): MatchRowComponentModel {
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
    ) = MatchRowComponentModel.Participant(
        name = model.name.replaceFirstChar { it.uppercase() },
        score = model.score.toString(),
        flag = flagProvider.getFlag(model.name.hashCode()),
        isWinner = (model.score > opponentScore) && isFinished,
    )
}
