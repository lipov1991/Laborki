package pl.lipov.laborki.presentation.map

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.ActivityMapBinding
import java.util.*

class MapActivity: AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener{

    private lateinit var binding : ActivityMapBinding
    private val mapViewModel by inject<MapViewModel>()
    private val REQUEST_CODE = 1
    private var btnFlag = false

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

        mapViewModel.getGallery()

        binding.floatingBtn4.setOnClickListener{
            BottomSheetGalleryFragment(mapViewModel.galleryList, mapViewModel.ifUploadClick).show(supportFragmentManager, "Galleries")
            mapViewModel.removeAllMarkers()
            if (!btnFlag) {
                binding.floatingBtn3.visibility = View.VISIBLE
                binding.floatingBtn.visibility = View.VISIBLE
                binding.floatingBtn2.visibility = View.VISIBLE
                btnFlag = true
            }
            else
            {
                binding.floatingBtn3.visibility = View.INVISIBLE
                binding.floatingBtn.visibility = View.INVISIBLE
                binding.floatingBtn2.visibility = View.INVISIBLE
                btnFlag = false
            }
        }

        binding.uploadButton.setOnClickListener{
            mapViewModel.uploadBtn(this)
        }
        binding.micButton.setOnClickListener{
            listenSpeech(this,REQUEST_CODE)
        }

        mapViewModel.currentGalleryPosition.observe(this, Observer {

            val name = mapViewModel.galleryList[it].name

            val coordinates = LatLng(mapViewModel.galleryList[it].lat, mapViewModel.galleryList[it].lng)
            val crowd = mapViewModel.galleryList[it].overcrowdingLevel

            mapViewModel.googleMap?.let { it1 ->
                mapViewModel.setCameraOnPosition(coordinates,
                    it1
                )
                mapViewModel.addHeatMap(this, it1, coordinates, crowd)

            }
        mapViewModel.removeAllMarkers()
            mapViewModel.categoryMarker = "Market"
            mapViewModel.ifUploadClick = false
        }
        )

    }

    override fun onMapReady(
        googleMap: GoogleMap
    ) {
            mapViewModel.setUpMap(googleMap, this)
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

    override fun onDestroy() {
        super.onDestroy()
        mapViewModel.disposableclear()
    }

    override fun onBackPressed() {
        if (mapViewModel.ifUploadClick){
            super.onBackPressed()
        }
        else{
            MaterialDialog(this).show {
                title(text = "Wyjscie z aplikacji")
                message(text = "Czy na pewno chcesz wyjsc z aplikacji")
                positiveButton(text = "Tak") {
                    super.onBackPressed()
                }
                negativeButton(text = "Nie")
            }
        }
    }
    fun listenSpeech(
        activity: Activity,
        requestCode: Int
    ){
        val recognizeSpeechInt = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply{
            putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Podaj kategorię")
        }
        try{
            activity.startActivityForResult(recognizeSpeechInt, requestCode)
        }catch (exception: ActivityNotFoundException){
            Toast.makeText(activity, "Twoje urzadzenie nie obsługuje STT", Toast.LENGTH_SHORT).show()
        }

    }
    fun handleSpeechResult(
        data: Intent,
        context: Context
    ){

        data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            ?.firstOrNull()?.let { recognizeResult ->

                mapViewModel.categoryType = recognizeResult
                mapViewModel.galeriaLevelList[mapViewModel.activeLevel!!].setCategoryVisibility(recognizeResult)

                Toast.makeText(context, recognizeResult, Toast.LENGTH_LONG).show()
            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                data?.let { handleSpeechResult(data, this) }
            }
        }
    }





}
