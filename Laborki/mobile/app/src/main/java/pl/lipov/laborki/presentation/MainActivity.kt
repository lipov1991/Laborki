package pl.lipov.laborki.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.run {
            onAccelerometerNotDetected.observe(this@MainActivity) {
                binding.info.text = getString(R.string.no_accelerometer_detected)
            }
            onGestureEvent.observe(this@MainActivity) {
                binding.info.text = it.name
            }
            onSensorEvent.observe(this@MainActivity) {
                binding.info.text = it.name
            }
        }
    }
}
