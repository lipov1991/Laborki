package pl.lipov.laborki.presentation.map

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.gms.maps.GoogleMap
import org.koin.android.ext.android.inject
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.ActivityMapBinding
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
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

        viewModel.getGalleries()

        //viewModel.listenForSpeechRecognize(this,REQUEST_CODE)

//        viewModel.setUpCompass(this){ rotation->
//            binding.compass.rotation = rotation
//        }


        viewModel.run {

            ifDevelopmentBuilding.observe(this@MapActivity, Observer {
                ifPlanBuilding = it
            })

            actualGallery.observe(this@MapActivity, Observer {
                val gallery = galleries?.get(it)
                val name = gallery?.name
                Toast.makeText(this@MapActivity, "Wybrano $name na pozycji $it", Toast.LENGTH_LONG).show()
                googleMap?.let { it1 ->
                    if (gallery != null) {
                        if (name != null) {
                            setGalleryPosition(LatLng(gallery.lat,gallery.lng),name, it1)
                            //addHeatMap(it1,LatLng(gallery.lat,gallery.lng),gallery.overcrowdingLevel)
                        }
                    }
                }

                clearAllMarkers()
                markercategory = MarkerCategory.Market
                ifDevelopmentBuilding.postValue(false)


            })
        }

        binding.buttonPlan.setOnClickListener {
            Toast.makeText(this,"Plan zagospodarowania został wysłany na serwer.", Toast.LENGTH_LONG).show()
            viewModel.clearAllMarkers()
            viewModel.markercategory = MarkerCategory.Market
            viewModel.ifDevelopmentBuilding.postValue(true)
        }


        binding.buttonGalleries.setOnClickListener {
            viewModel.galleries?.let { it1 -> GalleriesDialogFragment(it1).show(supportFragmentManager,"Galleries") }
            if(viewModel.galleries == null){
                Toast.makeText(this,"Nie udalo sie pobrac liste galeri",Toast.LENGTH_LONG).show()
            }
        }

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


        //PoliceStationDialogFragment().show(supportFragmentManager,"policeStations")

        //viewModel.addHeatMap(googleMap,this)

        viewModel.addMarket(googleMap)

        googleMap.setOnMarkerClickListener(this)

        googleMap.setOnMapClickListener {
            binding.buttonBin.visibility = View.INVISIBLE
            viewModel.focusedMarker = null
        }

        viewModel.setUpIndoor(googleMap)

    }

    override fun onBackPressed() {
        if (viewModel.ifDevelopmentBuilding.value!!){
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


    override fun onMarkerClick(marker: Marker?): Boolean {

        if(viewModel.checkMarkerGallery(marker)) return false

        if(marker?.isInfoWindowShown == false) binding.buttonBin.visibility = View.VISIBLE

        viewModel.focusedMarker = marker

        return false

    }

//    override fun onActivityResult(
//            requestCode: Int,
//            resultCode: Int,
//            data: Intent?
//    ) {
//        if(requestCode == REQUEST_CODE_SPEECH_RECOGNIZE){
//            viewModel.handleActivityResult(resultCode,data,this)
//        }else {
//            /// TODO
//            super.onActivityResult(requestCode, resultCode, data)
//        }
//    }


    override fun onDestroy() {
        viewModel.disposableclear()
        super.onDestroy()
    }


}