package pl.lipov.laborki.presentation.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.ActivityMapBinding

class MapActivity : AppCompatActivity(), OnMapReadyCallback {

    private val viewModel: MapViewModel by viewModel()
    private lateinit var binding: ActivityMapBinding

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

        binding.fab1.setOnClickListener {
            viewModel.markerCategory = MarkerCategory.market
        }
        binding.fab2.setOnClickListener {
            viewModel.markerCategory = MarkerCategory.restauracja
        }
        binding.fab3.setOnClickListener {
            viewModel.markerCategory = MarkerCategory.bank
        }

//        val galleries = listOf(
//            Gallery(
//                "https://upload.wikimedia.org/wikipedia/commons/8/8d/Centrum_Handlowe_Warszawa_Wile%C5%84ska_2015.JPG",
//                "Galeria Wileńska"
//            ),
//            Gallery(
//                "https://pl.wikipedia.org/wiki/Plik:Centrum_Handlowe_Arkadia_w_Warszawie_2014.JPG",
//                "Arkadia"
//                )
//            )
//
//        binding.galleries.run {
//            adapter = GalleryAdapter(galleries)
//            val itemDecoration = DividerItemDecoration(
//                context,
//                LinearLayoutManager.HORIZONTAL
//            )
//            addItemDecoration(itemDecoration)
//        }
    }

    override fun onMapReady(
        googleMap: GoogleMap
    ) {
        viewModel.setUpMap(googleMap)
        viewModel.addLocation(googleMap)
        viewModel.removeMarker(googleMap)
        viewModel.indoorBuildingMarkerManagement(
            googleMap,
            binding.fab1,
            binding.fab2,
            binding.fab3
        )
    }
}

