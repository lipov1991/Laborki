package pl.lipov.laborki.data.repository.api.dto

data class GalleriesDto(
    val lat: Double,
    val lng: Double,
    val name: String,
    val overcrowdingLevel: Int,
    val url: String
)
