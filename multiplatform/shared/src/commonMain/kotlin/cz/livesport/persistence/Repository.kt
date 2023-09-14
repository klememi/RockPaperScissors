package cz.livesport.persistence

import cz.livesport.networking.API
import cz.livesport.networking.APIImpl
import cz.livesport.networking.MatchesDTO
import cz.livesport.util.Language
import cz.livesport.util.Project
import kotlinx.coroutines.flow.Flow
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.MemoryPolicy
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import kotlin.time.Duration.Companion.seconds

internal interface Repository {
    fun matches(language: Language, project: Project): Flow<StoreReadResponse<MatchesDTO>>
}

internal data class RPSMatchesKey(
    val lang: Language,
    val project: Project,
)

internal class RepositoryImpl(
    api: API = APIImpl(),
    private val store: Store<RPSMatchesKey, MatchesDTO> = StoreBuilder
        .from(
            fetcher = Fetcher.of { key: RPSMatchesKey ->
                api.getMatches(key.project, key.lang)
            }
        ).disableCache().build()
): Repository {

    override fun matches(language: Language, project: Project): Flow<StoreReadResponse<MatchesDTO>> =
        store.stream(
            StoreReadRequest.fresh(RPSMatchesKey(language, project))
        )
}
