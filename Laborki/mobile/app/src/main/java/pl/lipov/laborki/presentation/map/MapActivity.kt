package pl.lipov.laborki.presentation.map

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import org.koin.android.ext.android.inject
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.ActivityMapBinding
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker

class MapActivity : AppCompatActivity(),OnMapReadyCallback,GoogleMap.OnMarkerClickListener{

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
            binding.buttonBin.visibility = View.INVISIBLE
        }
        binding.buttonRestauracja.setOnClickListener {
            viewModel.markercategory = MarkerCategory.Restauracja
            binding.buttonBin.visibility = View.INVISIBLE
        }
        binding.buttonBank.setOnClickListener {
            viewModel.markercategory = MarkerCategory.Bank
            binding.buttonBin.visibility = View.INVISIBLE
        }

        binding.buttonBin.setOnClickListener {
            viewModel.focusedMarker?.remove()

            viewModel.focusedMarker = null
            binding.buttonBin.visibility = View.INVISIBLE
        }

    }

    override fun onMapReady(
            googleMap: GoogleMap
    ) {
        viewModel.setUpMap(googleMap,this)
        viewModel.addMarket(googleMap)

        googleMap.setOnMarkerClickListener(this)

        googleMap.setOnMapClickListener {
            binding.buttonBin.visibility = View.INVISIBLE
            viewModel.focusedMarker = null
        }

    }

    override fun onMarkerClick(marker: Marker?): Boolean {

        if(viewModel.checkMarkerGallery(marker)) return false

        if(marker?.isInfoWindowShown == false) binding.buttonBin.visibility = View.VISIBLE

        viewModel.focusedMarker = marker

        return false

    }



}