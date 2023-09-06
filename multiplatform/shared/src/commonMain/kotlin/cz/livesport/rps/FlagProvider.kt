package cz.livesport.rps

internal interface FlagProvider {

    fun getFlag(seed: Int): String
}

internal class FlagProviderImpl: FlagProvider {

    private val availableFlags = listOf(
        "🇦🇩",
        "🇦🇱",
        "🇦🇷",
        "🇦🇿",
        "🇦🇺",
        "🇧🇩",
        "🇧🇸",
        "🇧🇷",
        "🇧🇹",
        "🇰🇭",
        "🇧🇦",
        "🇹🇩",
        "🇨🇦",
        "🇨🇨",
        "🇩🇰",
        "🇨🇿",
        "🇭🇷",
        "🇫🇷",
        "🇬🇷",
        "🇬🇱",
        "🇫🇯",
        "🇯🇵",
        "🇯🇲",
        "🇸🇰",
        "🇿🇼",
    )

    override fun getFlag(seed: Int): String = availableFlags[(3 * seed).mod(availableFlags.count())]
}