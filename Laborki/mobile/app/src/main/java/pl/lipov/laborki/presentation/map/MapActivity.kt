package pl.lipov.laborki.presentation.map

import android.graphics.Point
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.ActivityMapBinding

class MapActivity: AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private lateinit var binding : ActivityMapBinding
    private val mapViewModel by inject<MapViewModel>()


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
        binding.deleteMarker.setOnClickListener{
            mapViewModel.pin?.remove()
            mapViewModel.pin = null
            binding.deleteMarker.visibility = View.INVISIBLE
        }



    }

    override fun onMapReady(
        googleMap: GoogleMap
    ) {
            mapViewModel.setUpMap(googleMap)
            mapViewModel.setMarker(googleMap)
            mapViewModel.indoor(googleMap, this)

        googleMap.setOnMapClickListener {
            binding.deleteMarker.visibility = View.INVISIBLE
            mapViewModel.pin = null
        }
        googleMap.setOnMarkerClickListener(this)
    }


    private fun onFABButtonClick() {
        Toast.makeText(this, "Kategoria MARKET", Toast.LENGTH_SHORT).show()
        mapViewModel.categoryMarker = "market"
        binding.deleteMarker.visibility = View.INVISIBLE
    }
    private fun onFABButtonClick2() {
        Toast.makeText(this, "Kategoria RESTAURACJA", Toast.LENGTH_SHORT).show()
        mapViewModel.categoryMarker = "restauracja"
        binding.deleteMarker.visibility = View.INVISIBLE

    }
    private fun onFABButtonClick3() {
        Toast.makeText(this, "Kategoria BANK", Toast.LENGTH_SHORT).show()
        mapViewModel.categoryMarker = "bank"
        binding.deleteMarker.visibility = View.INVISIBLE
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        if(p0 == mapViewModel.pin_gallery) return false
        binding.deleteMarker.visibility = View.VISIBLE
        mapViewModel.pin = p0

        return false
    }


}
