//package pl.lipov.laborki.presentation.map
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.view.GestureDetectorCompat
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.CameraPosition
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.MarkerOptions
//import pl.lipov.laborki.R
//import pl.lipov.laborki.databinding.ActivityMainBinding
//import pl.lipov.laborki.presentation.LoginFirstScreen
//import pl.lipov.laborki.presentation.MainActivity
//import pl.lipov.laborki.presentation.MainViewModel
//
//class MapActivity : AppCompatActivity(), OnMapReadyCallback() {
//
//
//    private lateinit var binding: ActivityMainBinding
//    private val viewModel: MainViewModel by inject<MapViewModel>
//
//
//    override fun onCreate(
//        savedInstanceState: Bundle?
//    ) {
//        super.onCreate(savedInstanceState)
//        setTheme(R.style.SplashTheme)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//
//
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
//
//
//    }
//
//    override fun onMapReady(googleMap: GoogleMap?) {
//        viewModel.setupMap()
//    }
//
//
//}

// aktywnosc nie zachowuje stanu
// viewModel zapisuje stan