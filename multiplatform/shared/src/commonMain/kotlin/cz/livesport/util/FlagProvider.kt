package cz.livesport.util

internal interface FlagProvider {

    fun getFlag(seed: Int): String
}

internal class FlagProviderImpl: FlagProvider {

    private val availableFlags = listOf(
        "🇦🇩",
        "🇦🇱",
        "🇦🇷",
        "🇦🇿",
        "🇧🇩",
        "🇧🇸",
        "🇧🇷",
        "🇧🇹",
        "🇰🇭",
        "🇧🇦",
        "🇨🇨",
        "🇭🇷",
        "🇬🇷",
        "🇬🇱",
        "🇫🇯",
        "🇯🇵",
        "🇯🇲",
        "🇿🇼",
    )

    override fun getFlag(seed: Int): String = availableFlags[(seed * seed).mod(availableFlags.count())]
}