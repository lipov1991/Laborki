package pl.lipov.laborki.presentation.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import org.koin.android.ext.android.inject
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.ActivityMapBinding
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MapActivity : AppCompatActivity(),OnMapReadyCallback {

    private lateinit var binding: ActivityMapBinding
    private val viewModel by inject<MapViewModel>()

    override fun onCreate(
            savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        binding.buttonMarket.setOnClickListener {
            viewModel.markercategory = MarkerCategory.Market
        }
        binding.buttonRestauracja.setOnClickListener {
            viewModel.markercategory = MarkerCategory.Restauracja
        }
        binding.buttonBank.setOnClickListener {
            viewModel.markercategory = MarkerCategory.Bank
        }
    }

    override fun onMapReady(
            googleMap: GoogleMap
    ) {
        viewModel.setUpMap(googleMap)
        viewModel.addMarket(googleMap)
    }

}