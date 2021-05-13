package pl.lipov.laborki.presentation.map

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.list.customListAdapter
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import io.reactivex.Single
import pl.lipov.laborki.R
import pl.lipov.laborki.common.utils.MapUtils
import pl.lipov.laborki.data.repository.LoginRepository
import pl.lipov.laborki.data.repository.api.dto.GalleryDto

class MapViewModel(
    private val mapUtils: MapUtils,
    private val loginRepository: LoginRepository
) : ViewModel() {

    var markerCategory: MarkerCategory? = null
    var markerMarket: Marker? = null
    var markerRestaurant: Marker? = null
    var markerBank: Marker? = null
    private var markerList = mutableListOf<Marker>()
    private var currentFloor: Int = 0
    private var isPlanBtnClicked: Boolean = false
    private var currentGallery: String = "Galeria Wileńska"
    private var galleryDialog: MaterialDialog? = null

    //    private var heatmapTileProvider: HeatmapTileProvider? = null
    private var currentMarker = "Market"
    var galleries: MutableList<GalleryDto> = mutableListOf()

    fun setUpMap(
        googleMap: GoogleMap,
        context: Context
    ) {
        mapUtils.setUpMap(googleMap)
//        addHeatMap(googleMap, context)
    }

    fun addLocation(
        googleMap: GoogleMap
    ) {
        googleMap.setOnMapLongClickListener { latLng ->

            if (markerCategory == MarkerCategory.market) {
                if (markerMarket != null) {
                    markerMarket?.remove()
                }
                markerMarket = googleMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(markerCategory.toString())
                        .draggable(true)
                )
                markerMarket!!.tag = currentFloor
                markerList.add(markerMarket!!)
                isPlanBtnClicked = false
            }

            if (markerCategory == MarkerCategory.restauracja) {

                if (markerRestaurant != null) {
                    markerRestaurant?.remove()
                }
                markerRestaurant = googleMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(markerCategory.toString())
                        .draggable(true)
                )
                markerRestaurant!!.tag = currentFloor
                markerList.add(markerRestaurant!!)
                isPlanBtnClicked = false
            }

            if (markerCategory == MarkerCategory.bank) {
                if (markerBank != null) {
                    markerBank?.remove()
                }
                markerBank = googleMap.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .title(markerCategory.toString())
                        .draggable(true)
                )
                markerBank!!.tag = currentFloor
                markerList.add(markerBank!!)
                isPlanBtnClicked = false
            }
        }
    }

    fun removeMarker(
        googleMap: GoogleMap
    ) {
        googleMap.setOnInfoWindowLongClickListener {
            if (markerCategory == MarkerCategory.market)
                markerMarket?.remove()
            if (markerCategory == MarkerCategory.restauracja)
                markerRestaurant?.remove()
            if (markerCategory == MarkerCategory.bank)
                markerBank?.remove()
        }
    }

    fun indoorBuildingMarkerManagement(
        googleMap: GoogleMap,
        marketButton: FloatingActionButton?,
        restaurantButton: FloatingActionButton?,
        bankButton: FloatingActionButton?
    ) {

        googleMap.setOnIndoorStateChangeListener(object : GoogleMap.OnIndoorStateChangeListener {

            override fun onIndoorBuildingFocused() {
                googleMap.setOnCameraMoveListener {
                    if (googleMap.focusedBuilding == null) {
                        marketButton?.visibility = View.GONE
                        restaurantButton?.visibility = View.GONE
                        bankButton?.visibility = View.GONE
                    } else {
                        marketButton?.visibility = View.VISIBLE
                        restaurantButton?.visibility = View.VISIBLE
                        bankButton?.visibility = View.VISIBLE
                    }
                }
            }

            override fun onIndoorLevelActivated(p0: IndoorBuilding) {
                val levels = p0.levels
                val activeLevel = p0.activeLevelIndex
                currentFloor = levels[activeLevel].name.toInt()

                for (m: Marker in markerList) {
                    if (m.tag != currentFloor) {
                        m.isVisible = false
                    } else {
                        m.isVisible = true
                    }
                }
            }
        })
    }

    fun setUpGallery(
        googleMap: GoogleMap,
        markerName: String,
        galleryLatLng: LatLng
    ) {

        val cameraPos = CameraPosition.Builder()
            .target(galleryLatLng)
            .zoom(18f)
            .build()

        currentMarker = "Sklep"
        currentGallery = markerName
        markerList.clear()
        googleMap.clear()

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPos))
        googleMap.addMarker(
            MarkerOptions()
                .position(galleryLatLng)
                .title(markerName)
        )
    }

    fun getGalleries(): Single<List<GalleryDto>> {
        return loginRepository.getGalleries()
    }

    fun showGalleryList(
        context: Context,
        button: FloatingActionButton,
        googleMap: GoogleMap
    ) {
        button.setOnClickListener {
            galleryDialog = MaterialDialog(context, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
                title(R.string.galleries_warsaw)
                customListAdapter(
                    GalleryAdapter(
                        galleries,
                        this@MapViewModel,
                        googleMap
                    )
                )
            }
            if (!isPlanBtnClicked && markerList.count() != 0) {
                MaterialDialog(context).show {
                    title(text = currentGallery)
                    message(R.string.end_plan)
                    positiveButton(text = "Tak") {
                        it.dismiss()
                        markerMarket?.remove()
                        markerRestaurant?.remove()
                        markerBank?.remove()
                        currentMarker = "Market"
                    }
                    negativeButton(text = "Nie") {
                        it.dismiss()
                        galleryDialog!!.dismiss()
                    }
                }
            }
        }
    }

    fun sendBuildingPlans(
        context: Context,
        button: FloatingActionButton
    ) {
        button.setOnClickListener {
            if (markerList.count() != 0) {
                Toast.makeText(context, R.string.send_plan, Toast.LENGTH_LONG).show()
                markerMarket?.remove()
                markerRestaurant?.remove()
                markerBank?.remove()
                markerList.clear()
                currentMarker = "Market"
                isPlanBtnClicked = true
            } else {
                Toast.makeText(context, R.string.send_plan_empty, Toast.LENGTH_LONG).show()
            }
        }
    }

//    private fun addHeatMap(
//        map: GoogleMap,
//        context: Context
//    ) {
//        try {
//            val policeStations = getPoliceStations(context)
//            val weightedLatLngs = policeStations.map { policeStation ->
//                val latlng = LatLng(policeStation.lat, policeStation.lng)
//                WeightedLatLng(latlng, policeStation.weight)
//            }
//            val provider = HeatmapTileProvider.Builder()
//                .weightedData(weightedLatLngs)
//                .build()
//            heatmapTileProvider?.setRadius(50)
//            map.addTileOverlay(TileOverlayOptions().tileProvider(provider))
//        } catch (e: JSONException) {
//            Toast.makeText(context, "Problem reading list of locations.", Toast.LENGTH_LONG)
//                .show()
//        }
//    }
//
//    @Throws(JSONException::class)
//    private fun getPoliceStations(context: Context): List<PoliceStation> {
//        val inputStream = context.resources.openRawResource(R.raw.police_stations)
//        val json = Scanner(inputStream).useDelimiter("\\A").next()
//        val itemType = object : TypeToken<List<PoliceStation>>() {}.type
//        return Gson().fromJson<List<PoliceStation>>(json, itemType)
//    }

}
