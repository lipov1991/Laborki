package pl.lipov.laborki.presentation.map

import android.os.Bundle
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
import com.google.maps.android.heatmaps.HeatmapTileProvider
import com.google.maps.android.heatmaps.WeightedLatLng
import org.json.JSONArray
import org.koin.android.ext.android.inject
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapBinding
    private val viewModel by inject<MapViewModel>()
    var category: String = "Market"

    var mapMarket: Marker? = null
    var mapBank: Marker? = null
    var mapRestauracja: Marker? = null

    var Floor: Int = 0

    val dataBase = Firebase.database
    val dbRef = dataBase.getReference()

    private var galeriesNames = mutableListOf<String>()
    private var galeriesLatLng = mutableListOf<LatLng>()
    private var galeriesOverCrowding = mutableListOf<WeightedLatLng>()

    var provider: HeatmapTileProvider? = null
    var currentGallery: String = "Wilenska"

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.BankBtn.setOnClickListener {
            onBankBtnClick()
        }
        binding.ResBtn.setOnClickListener {
            onResBtnClick()
        }
        binding.MarketBtn.setOnClickListener {
            onMarketBtnClick()
        }

        binding.UploadPlan.setOnClickListener {
            onUploadBtnClick()
        }

        dbRef.child("galeries").get().addOnSuccessListener {
            var array = JSONArray(Gson().toJson(it.value))
            for (i in 0 until array.length()) {
                var name = array.getJSONObject(i).get("name")
                var lat = array.getJSONObject(i).get("lat")
                var lng = array.getJSONObject(i).get("lng")
                var overcrowding = array.getJSONObject(i).get("overcrowdingLevel")
                galeriesNames.add(name as String)
                galeriesLatLng.add(LatLng(lat as Double, lng as Double))
                galeriesOverCrowding.add(WeightedLatLng(LatLng(lat.toDouble(),lng.toDouble()), overcrowding.toString().toDouble()))

            }
        }


    }

    override fun onMapReady(
        googleMap: GoogleMap
    ) {
        viewModel.setUpMap(googleMap)
        addMarker(googleMap)
        viewModel.addHeatMap(googleMap, this)

        binding.galeriesNames.setOnClickListener {

            fun galleryChange(){
                MaterialDialog(this, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
                    listItems(items = galeriesNames) { _, index, _ ->
                        googleMap?.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                galeriesLatLng[index],
                                18f
                            ), 2000, null
                        )
                        Toast.makeText(
                            this@MapActivity,
                            galeriesNames[index],
                            Toast.LENGTH_SHORT
                        ).show()


                        category = "Market"
                        currentGallery = galeriesNames[index]

                    }

                }

            }

            if(mapBank == null && mapRestauracja == null  && mapMarket == null){
                galleryChange()
            }
            else{
                MaterialDialog(this).show{
                    title(text = currentGallery)
                    message(text= "Czy na pewno chcesz zakończyć plan zagospodarowania bez wysłania go na serwer?")
                    positiveButton(text = "TaK"){
                        galleryChange()
                        mapBank?.remove()
                        mapBank = null
                        mapMarket?.remove()
                        mapMarket = null
                        mapBank?.remove()
                        mapBank = null
                        category= "Market"
                    }
                    negativeButton(text ="Nie"){
                        dialogBehavior.onDismiss()
                    }
                }

            }
            provider = HeatmapTileProvider.Builder().weightedData(galeriesOverCrowding).build()
            provider?.setRadius(50)
            googleMap.addTileOverlay(TileOverlayOptions().tileProvider(provider))
        }

    }


    override fun onBackPressed() {
        MaterialDialog(this).show {
            title(text = "Wyjscie z aplikacji")
            message(text = "Czy na pewno chcesz wyjść z aplikacji")
            positiveButton(text = "tak") { super.onBackPressed() }
            negativeButton(text = "Nie") {dialogBehavior.onDismiss()}
        }

    }


    private fun onBankBtnClick() {
        Toast.makeText(this, "Bank", Toast.LENGTH_SHORT).show()
        this.category = "Bank"

    }

    private fun onResBtnClick() {
        Toast.makeText(this, "Restauracja", Toast.LENGTH_SHORT).show()
        this.category = "Restauracja"

    }

    private fun onMarketBtnClick() {
        Toast.makeText(this, "Market", Toast.LENGTH_SHORT).show()
        this.category = "Market"

    }

    private fun onUploadBtnClick(){
        Toast.makeText(this, "Plan zagospodarowania został wysłany na serwer", Toast.LENGTH_SHORT).show()
        if (mapBank != null) {
            mapBank?.remove()
            mapBank = null
        }
        if (mapMarket != null) {
            mapMarket?.remove()
            mapMarket = null
        }
        if (mapBank != null) {
            mapBank?.remove()
            mapBank = null
        }
        this.category = "Market"

    }
    fun addMarker(googleMap: GoogleMap) {
        googleMap.setOnIndoorStateChangeListener(object : GoogleMap.OnIndoorStateChangeListener {
            override fun onIndoorBuildingFocused() {
            }

            override fun onIndoorLevelActivated(indoorBuilding: IndoorBuilding) {
                Floor = indoorBuilding.levels[indoorBuilding.activeLevelIndex].name.toInt()
                if (mapMarket?.snippet.toString() != Floor.toString()) {
                    mapMarket?.isVisible = false
                } else {
                    mapMarket?.isVisible = true
                }

                if (mapBank?.snippet.toString() != Floor.toString()) {
                    mapBank?.isVisible = false
                } else {
                    mapBank?.isVisible = true
                }

                if (mapRestauracja?.snippet.toString() != Floor.toString()) {
                    mapRestauracja?.isVisible = false
                } else {
                    mapRestauracja?.isVisible = true
                }

            }

        })
        googleMap.setOnCameraMoveListener() {
            if (googleMap.focusedBuilding == null) {
                binding.BankBtn.hide()
                binding.ResBtn.hide()
                binding.MarketBtn.hide()
                category = ""

            } else {
                binding.BankBtn.show()
                binding.ResBtn.show()
                binding.MarketBtn.show()
            }

        }
        googleMap.setOnMapLongClickListener { latLng ->
            if (category == "Market") {

                if (mapMarket != null) {
                    mapMarket?.remove()
                    mapMarket = null
                } else {
                    mapMarket = googleMap.addMarker(
                        MarkerOptions().position(latLng).title(category).draggable(true)
                            .snippet(Floor.toString())
                    )

                }
            }
            if (category == "Bank") {

                if (mapBank != null) {
                    mapBank?.remove()
                    mapBank = null
                } else {
                    mapBank = googleMap.addMarker(
                        MarkerOptions().position(latLng).title(category).draggable(true)
                            .snippet(Floor.toString())
                    )
                }
            }
            if (category == "Restauracja") {

                if (mapRestauracja != null) {
                    mapRestauracja?.remove()
                    mapRestauracja = null
                } else {
                    mapRestauracja = googleMap.addMarker(
                        MarkerOptions().position(latLng).title(category).draggable(true)
                            .snippet(Floor.toString())
                    )
                }
            }
        }
    }


}