package cz.livesport.rps

import cz.livesport.util.Language
import cz.livesport.util.Project
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal interface StateManager<out STATE : Any, in VIEW_EVENT : Any> {
    val state: Flow<STATE>
    fun changeState(viewEvent: VIEW_EVENT)
}

class RPSStateManager(
    state: State
): StateManager<RPSStateManager.State, RPSStateManager.Event> {

    private val mutableState = MutableStateFlow(state)

    override val state: Flow<State> = mutableState.asStateFlow()

    override fun changeState(viewEvent: Event) {
        when (viewEvent) {
            is Event.SetLanguage -> mutableState.value = mutableState.value.copy(language = viewEvent.language)
            is Event.SetProject -> mutableState.value = mutableState.value.copy(project = viewEvent.project)
        }
    }

    data class State(
        val language: Language,
        val project: Project,
    )

    sealed interface Event {
        data class SetLanguage(val language: Language) : Event
        data class SetProject(val project: Project) : Event
    }
}
