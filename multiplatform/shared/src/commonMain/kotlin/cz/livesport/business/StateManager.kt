package cz.livesport.business

import kotlinx.coroutines.flow.Flow

interface StateManager<out STATE : Any, in VIEW_EVENT : Any> {
    val state: Flow<STATE>
    fun changeState(viewEvent: VIEW_EVENT)
}