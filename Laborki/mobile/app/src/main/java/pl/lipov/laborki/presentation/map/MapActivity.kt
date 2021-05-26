package pl.lipov.laborki.presentation.map

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.list.listItems
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.maps.android.heatmaps.Gradient
import com.google.maps.android.heatmaps.HeatmapTileProvider
import com.google.maps.android.heatmaps.WeightedLatLng
import org.json.JSONArray
import org.koin.android.ext.android.inject
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.ActivityMapBinding
import java.util.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback {
    private lateinit var binding: ActivityMapBinding
    private val viewModel by inject<MapViewModel>()
    private var map: GoogleMap? = null

    private var galNameList = mutableListOf<String>()
    private var galLatLngList = mutableListOf<LatLng>()
    private var galIntensityList = mutableListOf<WeightedLatLng>()

    private val warsaw = LatLng(52.229676, 21.012229)
    private var currentCat: String = "Market"
    private var marketCount: Int = 0
    private var restaurantCount: Int = 0
    private var bankCount: Int = 0
    private var isRemoving: Int = 0
    private var currentFloor: Int = 0
    private var markerArray = mutableListOf<Marker>()
    private var currentGallery = ""
    private val REQUEST_CODE_STT = 1
    private var recognizedText = ""

    private lateinit var markerMarket: Marker
    private lateinit var markerRestaurant: Marker
    private lateinit var markerBank: Marker

    val database = Firebase.database
    val dbRef = database.getReference()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.marketButton.visibility = View.GONE
        binding.restaurantButton.visibility = View.GONE
        binding.bankButton.visibility = View.GONE
        binding.removeButton.visibility = View.GONE
        binding.sendPlan.visibility = View.GONE
        binding.sttButton.visibility = View.GONE

        binding.marketButton.setOnClickListener {
            currentCat = "Market"
            isRemoving = 0
        }

        binding.restaurantButton.setOnClickListener {
            currentCat = "Restaurant"
            isRemoving = 0
        }

        binding.bankButton.setOnClickListener {
            currentCat = "Bank"
            isRemoving = 0
        }

        binding.sendPlan.setOnClickListener {
            if (markerArray.count() == 0) {
                Toast.makeText(this, "Brak punktów do wysłania!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Plan zagospodarowania został wysłany na serwer!", Toast.LENGTH_SHORT).show()
                clear()
            }
        }

        binding.removeButton.setOnClickListener {
            isRemoving = 1
        }

        binding.sttButton.setOnClickListener {
            val sttIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            sttIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ROOT)
            sttIntent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Mów teraz!")
            try {
                startActivityForResult(sttIntent, REQUEST_CODE_STT)
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Toast.makeText(this, "Twoje urządzenie nie obsługuje STT.", Toast.LENGTH_SHORT).show()
            }
        }

        dbRef.child("galeries").get().addOnSuccessListener {
            var array = JSONArray(Gson().toJson(it.value))
            for (i in 0 until array.length()) {
                var name = array.getJSONObject(i).getString("name")
                var lat = array.getJSONObject(i).getDouble("lat")
                var lng = array.getJSONObject(i).getDouble("lng")
                var intensity = array.getJSONObject(i).getDouble("overcrowdingLevel")
                galNameList.add(name)
                galLatLngList.add(LatLng(lat, lng))

                if (intensity != 0.0) {
                    galIntensityList.add(WeightedLatLng(LatLng(lat, lng), intensity))
                }
            }
        }
    }

    private fun recognizeAction(){
        if (recognizedText == "markety") {
            currentCat = "Market"
            isRemoving = 0
        } else if (recognizedText == "banki") {
            currentCat = "Bank"
            isRemoving = 0
        } else if (recognizedText == "fast food") {
            currentCat = "Restaurant"
            isRemoving = 0
        } else {
            Toast.makeText(this, "Nie rozpoznano kategorii!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun clear() {
        for (marker in markerArray) {
            marker.remove()
        }
        markerArray.clear()
        marketCount = 0
        restaurantCount = 0
        bankCount = 0
        currentCat = "Market"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_STT -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    if (!result.isNullOrEmpty()) {
                        recognizedText = result[0]
                        recognizeAction()
                    }
                }
            }
        }
    }

    override fun onBackPressed() {
        if (markerArray.count() != 0) {
            MaterialDialog(this).show {
                title(text = "Wyjście z aplikacji")
                message(text = "Czy na pewno chcesz zakończyć plan zagospodarowania bez wysłania go na serwer?")
                positiveButton(text = "Tak") { dialog ->
                    dialog.dismiss()
                    super.onBackPressed()
                }
                negativeButton(text = "Nie") { dialog ->
                    dialog.dismiss()
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        if (map != null) {
            map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(warsaw, 12f))
            map!!.setMaxZoomPreference(12f)

            var colors = listOf(Color.parseColor("#faac7c"), Color.parseColor("#3e1e70")).toIntArray()
            var startPoints = listOf<Float>(0.2f, 1f).toFloatArray()
            var gradient = Gradient(colors, startPoints)

            binding.placeButton.setOnClickListener {
                if (markerArray.count() != 0) {
                    MaterialDialog(this).show {
                        title(text = currentGallery)
                        message(text = "Czy na pewno chcesz zakończyć plan zagospodarowania bez wysłania go na serwer?")
                        positiveButton(text = "Tak") { dialog ->
                            clear()
                            dialog.dismiss()
                        }
                        negativeButton(text = "Nie") { dialog ->
                            dialog.dismiss()
                        }
                    }
                } else if (markerArray.count() == 0) {
                    MaterialDialog(this, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
                        listItems(items = galNameList) { _, index, _ ->
                            map?.setMaxZoomPreference(18f)
                            map?.animateCamera(CameraUpdateFactory.newLatLngZoom(galLatLngList[index], 18f), 2000, null)
                            var heatMapProvider = HeatmapTileProvider.Builder().weightedData(listOf(galIntensityList[index])).radius(50).maxIntensity(5.0).gradient(gradient).build()
                            map!!.addTileOverlay(TileOverlayOptions().tileProvider(heatMapProvider))
                            currentGallery = galNameList[index]
                            clear()
                        }
                    }
                }
            }

            map!!.setOnMapLongClickListener {
                if (currentCat == "Market" && marketCount == 0) {
                    markerMarket = googleMap.addMarker(
                            MarkerOptions().position(it).title(currentCat).draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                    )
                    markerMarket.tag = currentFloor
                    markerArray.add(markerMarket)
                    marketCount += 1
                }
                if (currentCat == "Restaurant" && restaurantCount == 0) {
                    markerRestaurant = googleMap.addMarker(
                            MarkerOptions().position(it).title(currentCat).draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                    )
                    markerRestaurant.tag = currentFloor
                    markerArray.add(markerRestaurant)
                    restaurantCount += 1
                }
                if (currentCat == "Bank" && bankCount == 0) {
                    markerBank = googleMap.addMarker(
                            MarkerOptions().position(it).title(currentCat).draggable(true).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    )
                    markerBank.tag = currentFloor
                    markerArray.add(markerBank)
                    bankCount += 1
                }

            }

            map!!.setOnMarkerClickListener { marker ->
                if (isRemoving == 1) {
                    if (marker.title == "Market") {
                        marketCount = 0
                    }
                    if (marker.title == "Restaurant") {
                        restaurantCount = 0
                    }
                    if (marker.title == "Bank") {
                        bankCount = 0
                    }
                    marker.remove()
                } else {
                    marker.showInfoWindow()
                }
                true
            }

            map!!.setOnCameraMoveListener() {
                if (googleMap.focusedBuilding == null) {
                    binding.marketButton.visibility = View.GONE
                    binding.restaurantButton.visibility = View.GONE
                    binding.bankButton.visibility = View.GONE
                    binding.removeButton.visibility = View.GONE
                    binding.sendPlan.visibility = View.GONE
                    binding.sttButton.visibility = View.GONE
                } else {
                    binding.marketButton.visibility = View.VISIBLE
                    binding.restaurantButton.visibility = View.VISIBLE
                    binding.bankButton.visibility = View.VISIBLE
                    binding.removeButton.visibility = View.VISIBLE
                    binding.sendPlan.visibility = View.VISIBLE
                    binding.sttButton.visibility = View.VISIBLE
                }
            }

            googleMap.setOnIndoorStateChangeListener(object : GoogleMap.OnIndoorStateChangeListener {

                override fun onIndoorBuildingFocused() {}

                override fun onIndoorLevelActivated(indoorBuilding: IndoorBuilding) {
                    val levels = indoorBuilding.levels

                    val level = indoorBuilding.activeLevelIndex
//                    Log.d("Tag", "Level Index: $level") // For testing
                    currentFloor = levels[level].name.toInt()
//                    Log.d("Tag", "Level: $currentFloor") // For testing

                    for (marker: Marker in markerArray) {
                        if (marker.tag != currentFloor) {
                            marker.isVisible = false
                        } else {
                            marker.isVisible = true
                        }
                    }
                }
            })
        }
    }
}