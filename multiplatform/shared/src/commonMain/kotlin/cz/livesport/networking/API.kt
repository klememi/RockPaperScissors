package cz.livesport.networking

import cz.livesport.util.Language
import cz.livesport.util.Project
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

internal interface API {
    suspend fun getMatches(project: Project, lang: Language): MatchesDTO
}

internal class APIImpl(
    private val client: HttpClient = HttpClient(),
    private val urlStringBuilder: (String, String) -> String = { project, lang ->
        "http://0.0.0.0:8080/games/$project/$lang"
    },
): API {

    override suspend fun getMatches(project: Project, lang: Language): MatchesDTO =
        Json.decodeFromString(
            client.get(
                urlStringBuilder(project.code, lang.code)
            ).bodyAsText()
        )
}
