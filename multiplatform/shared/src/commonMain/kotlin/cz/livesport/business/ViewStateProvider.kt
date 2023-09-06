package cz.livesport.business

import kotlinx.coroutines.flow.Flow

interface ViewStateProvider<out VIEW_STATE : Any, in VIEW_EVENT : Any> {
    fun changeState(event: VIEW_EVENT)
    fun getViewState(): Flow<VIEW_STATE>
}