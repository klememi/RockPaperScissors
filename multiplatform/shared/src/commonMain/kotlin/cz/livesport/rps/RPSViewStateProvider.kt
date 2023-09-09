package cz.livesport.rps

import cz.livesport.networking.MatchesDTO
import cz.livesport.persistence.Repository
import cz.livesport.persistence.RepositoryImpl
import cz.livesport.util.AppConfiguration
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import org.mobilenativefoundation.store.store5.StoreReadResponse

interface ViewStateProvider<out VIEW_STATE : Any, in VIEW_EVENT : Any> {
    fun changeState(event: VIEW_EVENT)
    fun getViewState(): Flow<VIEW_STATE>
}

class RPSViewStateProvider internal constructor(
    private val repository: Repository,
    private val viewStateFactory: ViewStateFactory<MatchesDTO, RPSStateManager.State, RPSViewState>,
    private val stateManager: StateManager<RPSStateManager.State, RPSStateManager.Event>,
): ViewStateProvider<StoreReadResponse<RPSViewState>, RPSStateManager.Event> {

    constructor() : this(
        repository = RepositoryImpl(),
        viewStateFactory = RPSViewStateFactory(),
        stateManager = RPSStateManager(
            RPSStateManager.State(
                AppConfiguration.default.languages.first(),
                AppConfiguration.default.projects.first()
            )
        )
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getViewState(): Flow<StoreReadResponse<RPSViewState>> =
        periodicUpdater().flatMapLatest {
            stateManager.state
                .flatMapLatest { state ->
                    repository.matches(state.language.code, state.project.code)
                        .map { matchesResponse -> matchesResponse to state }
                }.mapNotNull { (response, state) ->
                    when (response) {
                        is StoreReadResponse.Data -> StoreReadResponse.Data(
                            viewStateFactory.create(response.value, state),
                            response.origin
                        )

                        else -> null
                    }
                }
        }

    override fun changeState(event: RPSStateManager.Event) {
        stateManager.changeState(event)
    }

    private fun periodicUpdater() = flow {
            while (true) {
                emit(Unit)
                delay(4000)
            }
        }
}
