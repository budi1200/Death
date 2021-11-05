package si.budimir.death.enums

enum class Lang(private val path: String) {
    DEATH_MESSAGE("death-message"),
    MISSING_PERMISSION("missing-permission");

    fun getPath(): String {
        return "lang.$path"
    }
}