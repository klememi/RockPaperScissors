package cz.livesport.networking

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

internal class API(
    private val client: HttpClient = HttpClient(),
    private val urlString: (String, String) -> String = { project, lang ->
        "http://0.0.0.0:8080/games/$project/$lang"
    },
) {

    suspend fun getMatches(project: String, lang: String): MatchesDTO =
        Json.decodeFromString(client.get(urlString(project, lang)).bodyAsText())
}
