package cz.livesport.persistence

import cz.livesport.networking.API
import cz.livesport.networking.MatchesDTO
import kotlinx.coroutines.flow.Flow
import org.mobilenativefoundation.store.store5.Fetcher
import org.mobilenativefoundation.store.store5.MemoryPolicy
import org.mobilenativefoundation.store.store5.Store
import org.mobilenativefoundation.store.store5.StoreBuilder
import org.mobilenativefoundation.store.store5.StoreReadRequest
import org.mobilenativefoundation.store.store5.StoreReadResponse
import kotlin.time.Duration.Companion.seconds

data class Key(
    val lang: String,
    val project: String
)

internal class Repository(
    api: API = API(),
    private val store: Store<Key, MatchesDTO> = StoreBuilder
        .from(
            fetcher = Fetcher.of { key: Key ->
                api.getMatches(key.project, key.lang)
            }
        ).cachePolicy(
            MemoryPolicy.builder<Any, Any>()
                .setExpireAfterAccess(2.seconds)
                .build()
        ).build()
) {

    fun matches(lang: String, project: String): Flow<StoreReadResponse<MatchesDTO>> = store.stream(
        StoreReadRequest.cached(Key(lang, project), false)
    )
}
