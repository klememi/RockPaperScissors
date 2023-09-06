package cz.livesport.business

interface UseCase<in INPUT, out OUTPUT> {
    fun createModel(dataModel: INPUT): OUTPUT
}