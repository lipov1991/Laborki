package pl.lipov.laborki.presentation.map

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.android.ext.android.inject
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.ActivityMapBinding

class MapActivity: AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding : ActivityMapBinding
    private val mapViewModel by inject<MapViewModel>()
    var category: String = "random pin"


    override fun onCreate(
        savedInstanceState: Bundle?
    ){
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)

        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        binding.floatingBtn.setOnClickListener{
            onFABButtonClick()
        }
        binding.floatingBtn2.setOnClickListener{
            onFABButtonClick2()
        }
        binding.floatingBtn3.setOnClickListener{
            onFABButtonClick3()
        }


    }

    override fun onMapReady(
        googleMap: GoogleMap
    ) {
            mapViewModel.setUpMap(googleMap)

            setMapLongClick(googleMap)
        }


    private fun onFABButtonClick() {
        Toast.makeText(this, "Kategoria MRKET", Toast.LENGTH_SHORT).show()

        this.category = "market"

    }
    private fun onFABButtonClick2() {
        Toast.makeText(this, "Kategoria RESTAURACJA", Toast.LENGTH_SHORT).show()

        this.category = "restauracja"

    }
    private fun onFABButtonClick3() {
        Toast.makeText(this, "Kategoria BANK", Toast.LENGTH_SHORT).show()

        this.category = "bank"

    }

    private fun setMapLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(category)
                    .draggable(true) // allows to drag & drop marker
            )
        }
    }


}
