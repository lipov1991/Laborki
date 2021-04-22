package pl.lipov.laborki.presentation.map

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.ActivityMapBinding


class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapLongClickListener,
    GoogleMap.OnMarkerDragListener {

    private val viewModel: MapViewModel by viewModel()
    private lateinit var binding: ActivityMapBinding
    private lateinit var myMap: GoogleMap

    public override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.galleryList.adapter = ... TODO


        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)

        binding.floatingBtnMarket.setOnClickListener {
            viewModel.changeMarker("Sklep")
            Toast.makeText(this, viewModel.currentMarkerType, Toast.LENGTH_LONG).show()
        }

        binding.floatingBtnBank.setOnClickListener {
            viewModel.changeMarker("Bank")
            Toast.makeText(this, viewModel.currentMarkerType, Toast.LENGTH_LONG).show()
        }

        binding.floatingBtnRes.setOnClickListener {
            viewModel.changeMarker("Restauracja")
            Toast.makeText(this, viewModel.currentMarkerType, Toast.LENGTH_LONG).show()
        }
    }

    override fun onMapReady(
        googleMap: GoogleMap
    ) {
        myMap = googleMap
        viewModel.setUpMap(googleMap, getString(R.string.wilenska_galeria))
        googleMap.setOnMapLongClickListener(this)
        googleMap.setOnMarkerDragListener(this)
    }

    override fun onMapLongClick(p0: LatLng) {
        viewModel.checkMarker().apply {
            if (this) {
                viewModel.addMarker(myMap, viewModel.currentMarkerType, p0)
            } else {
                Toast.makeText(this@MapActivity, "Ten marker już istnieje! ", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onMarkerDragEnd(p0: Marker?) {}
    override fun onMarkerDragStart(p0: Marker?) {}
    override fun onMarkerDrag(p0: Marker?) {}
}