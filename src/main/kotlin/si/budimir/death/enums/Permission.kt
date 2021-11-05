package si.budimir.death.enums

enum class Permission(private val value: String) {
    ADMIN("admin");

    fun getPerm(): String {
        return "death.$value"
    }
}