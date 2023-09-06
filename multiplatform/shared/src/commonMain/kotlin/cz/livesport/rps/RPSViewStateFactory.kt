package cz.livesport.rps

import cz.livesport.business.UseCase
import cz.livesport.business.ViewStateFactory
import cz.livesport.networking.MatchDTO
import cz.livesport.networking.MatchesDTO

data class RPSViewState(
    val title: String,
    val rows: List<MatchRowComponentModel>,
)

internal class RPSViewStateFactory(
    private val useCase: UseCase<MatchDTO, MatchRowComponentModel> = MatchRowUseCase()
): ViewStateFactory<MatchesDTO, Unit, RPSViewState> {

    override fun create(model: MatchesDTO, state: Unit): RPSViewState =
        RPSViewState(
            title = model.title,
            rows = model.matches.map { useCase.createModel(it) }
        )
}
