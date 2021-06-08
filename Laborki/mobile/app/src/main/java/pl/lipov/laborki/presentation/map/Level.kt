package pl.lipov.laborki.presentation.map

import com.google.android.gms.maps.model.Marker

data class Level (
    val level: Int,
    ) {
        var markerMarket: Marker? = null
        var markerRestauracja: Marker? = null
        var markerBank: Marker? = null

        fun getMarker(
            markerCategory: String?
        ): Marker?{
            if (markerCategory == "market") return markerMarket
            if (markerCategory == "restauracja") return markerRestauracja
            if (markerCategory == "bank") return markerBank

            return null
        }

        fun setMarket(
            marker: Marker,
            markerCategory: String?
        ){
            if (markerCategory == "market") markerMarket = marker
            if (markerCategory == "restauracja") markerRestauracja = marker
            if (markerCategory == "bank") markerBank = marker
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
    fun setCategoryVisibility(
        category: String?,
    ){
        if(category == "market"){
            markerMarket?.isVisible = true
            markerBank?.isVisible = false
            markerRestauracja?.isVisible= false
        }
        if(category == "bank"){
            markerMarket?.isVisible = false
            markerBank?.isVisible = true
            markerRestauracja?.isVisible= false
        }
        if(category == "restauracja"){
            markerMarket?.isVisible = false
            markerBank?.isVisible = false
            markerRestauracja?.isVisible= true
        }
    }
}