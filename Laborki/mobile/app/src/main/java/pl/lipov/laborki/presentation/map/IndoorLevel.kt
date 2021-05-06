package pl.lipov.laborki.presentation.map

import com.google.android.gms.maps.model.Marker

data class IndoorLevel(
    val level: Int,
) {
    var markerMarket: Marker? = null
    var markerRestauracja: Marker? = null
    var markerBank: Marker? = null

    fun getMarker(
        markerCategory: MarkerCategory?
    ): Marker?{
        if (markerCategory == MarkerCategory.Market) return markerMarket
        if (markerCategory == MarkerCategory.Restauracja) return markerRestauracja
        if (markerCategory == MarkerCategory.Bank) return markerBank

        return null
    }

    fun setMarket(
        marker: Marker,
        markerCategory: MarkerCategory?
    ){
        if (markerCategory == MarkerCategory.Market) markerMarket = marker
        if (markerCategory == MarkerCategory.Restauracja) markerRestauracja = marker
        if (markerCategory == MarkerCategory.Bank) markerBank = marker
    }

    fun setVisibility(
        visibility: Boolean
    ){
        markerMarket?.isVisible = visibility
        markerRestauracja?.isVisible = visibility
        markerBank?.isVisible = visibility
    }

    fun clearMarkers(){
        markerMarket?.remove()
        markerRestauracja?.remove()
        markerBank?.remove()

        markerMarket = null
        markerRestauracja = null
        markerBank = null
    }

}