package cz.livesport.rps

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import cz.livesport.business.StateManager
import cz.livesport.business.ViewStateFactory
import cz.livesport.business.ViewStateProvider
import cz.livesport.networking.MatchesDTO
import cz.livesport.persistence.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import org.mobilenativefoundation.store.store5.StoreReadResponse

class RPSViewStateProvider internal constructor(
    private val repository: Repository,
    private val viewStateFactory: ViewStateFactory<MatchesDTO, Unit, RPSViewState>,
    private val stateManager: StateManager<RPSStateManager.State, RPSStateManager.Event>,
    private val updateFrequency: Long = 4000L,
) : ViewStateProvider<StoreReadResponse<RPSViewState>, RPSStateManager.Event> {

    constructor(lang: String, project: String) : this(
        Repository(),
        RPSViewStateFactory(),
        RPSStateManager(RPSStateManager.State(lang, project))
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    @NativeCoroutines
    override fun getViewState(): Flow<StoreReadResponse<RPSViewState>> =
        combine(periodicUpdater(), stateManager.state) { _, state ->
            state
        }.flatMapLatest {
            repository.matches(it.lang, it.project)
        }.map {
            when (it) {
                is StoreReadResponse.Data -> StoreReadResponse.Data(
                    viewStateFactory.create(it.value, Unit),
                    it.origin
                )

                is StoreReadResponse.Error -> StoreReadResponse.Error.Message(
                    it.errorMessageOrNull() ?: "",
                    it.origin
                )

                is StoreReadResponse.NoNewData -> StoreReadResponse.NoNewData(it.origin)

                is StoreReadResponse.Loading -> StoreReadResponse.Loading(it.origin)
            }
        }

    override fun changeState(event: RPSStateManager.Event) {
        stateManager.changeState(event)
    }

    private fun periodicUpdater() = channelFlow {
            while (true) {
                send(Unit)
                delay(updateFrequency)
            }
        }.flowOn(Dispatchers.IO)
}
