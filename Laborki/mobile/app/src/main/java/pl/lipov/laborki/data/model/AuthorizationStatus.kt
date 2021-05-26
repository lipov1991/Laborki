package pl.lipov.laborki.data.model

enum class AuthorizationStatus(
    val value: String
) {
    SUCCESS("Login correct"),
    UNSUCCESS("Login incorrect")
}