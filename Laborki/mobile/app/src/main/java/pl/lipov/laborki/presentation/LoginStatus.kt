package pl.lipov.laborki.presentation

enum class LoginStatus(
        private val value: Int
) {

    CORRECT(1),
    UNCORRECT(2),
    BLOCKED(3)

}