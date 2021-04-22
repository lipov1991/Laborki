package pl.lipov.laborki.presentation.map

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.android.ext.android.inject
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.ActivityMapBinding

class MapActivity: AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMapBinding
    private val viewModel by inject<MapViewModel>()
    var category: String = "None"

    var mapMarket: Marker? = null
    var mapBank: Marker? = null
    var mapRestauracja: Marker? = null

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.BankBtn.setOnClickListener{
            onBankBtnClick()
        }
        binding.ResBtn.setOnClickListener{
            onResBtnClick()
        }
        binding.MarketBtn.setOnClickListener{
            onMarketBtnClick()
        }


    }

    override fun onMapReady(
        googleMap: GoogleMap
    ) {
        viewModel.setUpMap(googleMap)
        addMarker(googleMap)

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

    fun addMarker(googleMap: GoogleMap){
        googleMap.setOnMapLongClickListener { latLng ->
            if(category == "Market")
            {
                if(mapMarket != null)
                {
                    mapMarket?.remove()
                    mapMarket = null
                }
                else{
                    mapMarket = googleMap.addMarker(MarkerOptions().position(latLng).title(category).draggable(true)) }
        }
            if(category == "Bank")
            {
                if(mapBank != null)
                {
                    mapBank?.remove()
                    mapBank = null
                }
                else{
                    mapBank = googleMap.addMarker(MarkerOptions().position(latLng).title(category).draggable(true)) }
            }
            if(category == "Restauracja")
            {
                if(mapRestauracja != null)
                {
                    mapRestauracja?.remove()
                    mapRestauracja = null
                }
                else{
                    mapRestauracja = googleMap.addMarker(MarkerOptions().position(latLng).title(category).draggable(true)) }
            }
        }
    }



}