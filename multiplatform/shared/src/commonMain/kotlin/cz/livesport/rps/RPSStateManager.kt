package cz.livesport.rps

import cz.livesport.business.StateManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RPSStateManager(
    state: State
) : StateManager<RPSStateManager.State, RPSStateManager.Event> {

    private val mutableState = MutableStateFlow(
        state
    )

    override val state: Flow<State> = mutableState.asStateFlow()

    override fun changeState(viewEvent: Event) {
        when (viewEvent) {
            is Event.SetLang -> mutableState.value = mutableState.value.copy(lang = viewEvent.lang)
            is Event.SetProject -> mutableState.value = mutableState.value.copy(project = viewEvent.project)
        }
    }

    data class State(
        val lang: String,
        val project: String,
    )

    sealed interface Event {
        data class SetLang(val lang: String) : Event
        data class SetProject(val project: String) : Event
    }
}
