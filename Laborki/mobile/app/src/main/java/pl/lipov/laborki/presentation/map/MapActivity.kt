package pl.lipov.laborki.presentation.map

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.ActivityMainBinding
import pl.lipov.laborki.databinding.ActivityMapBinding
import pl.lipov.laborki.presentation.MainViewModel
import com.google.android.gms.maps.OnMapReadyCallback

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
    }

    override fun onMapReady(p0: GoogleMap?) {
        TODO("Not yet implemented")
    }


}