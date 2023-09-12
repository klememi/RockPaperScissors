package cz.livesport.rps

import cz.livesport.networking.MatchDTO
import cz.livesport.networking.MatchesDTO
import cz.livesport.util.Language
import cz.livesport.util.Project

internal interface ViewStateFactory<in MODEL : Any, in STATE : Any, out VIEW_STATE : Any> {
    fun create(model: MODEL, state: STATE): VIEW_STATE
}

data class RPSViewState(
    val title: String,
    val rows: List<MatchRowComponentModel>,
    val language: Language,
    val project: Project,
)

internal class RPSViewStateFactory(
    private val useCase: UseCase<Pair<MatchDTO, String>, MatchRowComponentModel> = MatchRowUseCase()
): ViewStateFactory<MatchesDTO, RPSStateManager.State, RPSViewState> {

    override fun create(model: MatchesDTO, state: RPSStateManager.State): RPSViewState =
        RPSViewState(
            title = model.title,
            rows = model.matches.map { useCase.createModel(it to model.roundLabel) },
            language = state.language,
            project = state.project,
        )
}
