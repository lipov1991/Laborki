package pl.krzysztof.lipka.laborki.model

import androidx.annotation.DrawableRes

data class Resource(
    var id: Long,
    var name: String,
    var specification: String,
    @DrawableRes var categoryIconResId: Int,
    @DrawableRes var imageResId: Int,
    var resourceType: ResourceType,
    var isActive: Boolean = false
)
