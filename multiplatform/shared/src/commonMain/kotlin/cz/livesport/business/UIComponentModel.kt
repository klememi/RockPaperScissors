package cz.livesport.business

interface UIComponentModel<CONFIGURATION : Any> {
    val configuration: CONFIGURATION?
}

typealias EmptyConfiguration = Unit