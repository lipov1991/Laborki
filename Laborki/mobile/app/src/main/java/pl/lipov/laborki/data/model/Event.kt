package pl.lipov.laborki.data.model

enum class Event(
        private val value: Int
) {
    LONG_CLICK(1),
    DOUBLE_TAP(2),
    ACCELERATION_CHANGE(3)
}
