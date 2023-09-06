package cz.livesport.business

interface ViewStateFactory<in MODEL : Any, in STATE : Any, out VIEW_STATE : Any> {
    fun create(model: MODEL, state: STATE): VIEW_STATE
}