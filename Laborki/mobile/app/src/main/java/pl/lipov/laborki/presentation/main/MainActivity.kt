package pl.lipov.laborki.presentation.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import org.koin.android.ext.android.inject
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    companion object {
        private const val REQUEST_CODE_SPEECH_RECOGNIZE = 100
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel by inject<MainViewModel>()

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        viewModel.listenForSpeechRecognize(this, REQUEST_CODE_SPEECH_RECOGNIZE)
        viewModel.setUpCompass()
        viewModel.rotationChange.observe(::getLifecycle) { rotation ->
            binding.compass.rotation = rotation
        }
        Log.d("test", "test")
    }

    override fun onMapReady(
        map: GoogleMap
    ) {
        viewModel.setUpMap(map, this)
        PoliceStationsDialogFragment().show(supportFragmentManager, "policeStations")
    }

    @Suppress("deprecation")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == REQUEST_CODE_SPEECH_RECOGNIZE) {
            viewModel.handleSpeechRecognizeResult(resultCode, data, this)
        } else {
            // TODO replace it when new mechanism will be completed
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
