package pl.lipov.laborki.data.model

enum class AuthorizationSuccess (
        val value: String
) {
    SUCCESS("Login correct"),
    UNSUCCESS("Login incorrect")
}