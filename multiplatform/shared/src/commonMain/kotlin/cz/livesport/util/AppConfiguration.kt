package cz.livesport.util

data class AppConfiguration(
    val languages: List<Language>,
    val projects: List<Project>,
) {

    companion object {
        val default: AppConfiguration = AppConfiguration(
            languages = listOf(
                Language(code = "EN", name = "🇬🇧"),
                Language(code = "CZ", name = "🇨🇿"),
            ),
            projects = listOf(
                Project(code = "GL", name = "Global"),
                Project(code = "CZ", name = "Czech"),
                Project(code = "BR", name = "Brazil"),
            ),
        )
    }
}
