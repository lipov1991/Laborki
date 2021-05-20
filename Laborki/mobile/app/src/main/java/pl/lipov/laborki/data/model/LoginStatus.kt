package pl.lipov.laborki.data.model

enum class LoginStatus(
    val value: String
) {
    SUCCESS("Password correct"),
    BLOCK("You've been blocked"),
    UNSUCCESS("Password incorrect. Attempts Left: ")
}