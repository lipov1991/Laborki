package pl.lipov.laborki.presentation.map

import android.graphics.Point
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.android.ext.android.inject
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.ActivityMapBinding

class MapActivity: AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerDragListener {

    private lateinit var binding : ActivityMapBinding
    private val mapViewModel by inject<MapViewModel>()
    var category: String = "random pin"


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


    }

    override fun onMapReady(
        googleMap: GoogleMap
    ) {
            mapViewModel.setUpMap(googleMap)

            setMapLongClick(googleMap)

        with(googleMap){
            setOnMarkerDragListener(this@MapActivity)
        }

        }


    private fun onFABButtonClick() {
        Toast.makeText(this, "Kategoria MRKET", Toast.LENGTH_SHORT).show()

        this.category = "market"

    }
    private fun onFABButtonClick2() {
        Toast.makeText(this, "Kategoria RESTAURACJA", Toast.LENGTH_SHORT).show()

        this.category = "restauracja"

    }
    private fun onFABButtonClick3() {
        Toast.makeText(this, "Kategoria BANK", Toast.LENGTH_SHORT).show()

        this.category = "bank"

    }

    private fun setMapLongClick(map: GoogleMap) {
        map.setOnMapLongClickListener { latLng ->
            map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(category)
                    .draggable(true) // allows to drag & drop marker

            )
        }
    }




    private fun overlap(point: Point, imgview: ImageView): Boolean {
        val imgCoords = IntArray(2)

        imgview.getLocationOnScreen(imgCoords)
        val overlapX =
            point.x < imgCoords[0] + imgview.width && point.x > imgCoords[0] - imgview.width
        val overlapY =
            point.y < imgCoords[1] + imgview.height && point.y > imgCoords[1] - imgview.width
        return overlapX && overlapY
    }


    override fun onMarkerDragStart(p0: Marker?) {
        //Toast.makeText(this, "onMarkerDragStart", Toast.LENGTH_SHORT).show()

        // show trash FAB
        binding.deleteMarkerBtn.visibility=View.VISIBLE
    }

    override fun onMarkerDrag(p0: Marker?) {
        //Toast.makeText(this, "onMarkerDrag", Toast.LENGTH_SHORT).show()
    }

    override fun onMarkerDragEnd(marker : Marker) {

// pass somehow map or in other way to overlap function
//        val markerScreenPosition = map.projection.toScreenLocation(marker.position)
//        if (overlap(markerScreenPosition, binding.deleteMarkerBtn)){
//
//            Toast.makeText(this, "dziala!", Toast.LENGTH_SHORT).show()
            //marker.remove()
        
        println("POS")
        println(marker.position)
        binding.deleteMarkerBtn.visibility=View.INVISIBLE
    }
}
