package pl.lipov.laborki.common.utils

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker


class MapUtils {

    val currentGalleryPosition = MutableLiveData<Int>()

    fun setCameraOnPosition(latLng: LatLng, googleMap: GoogleMap){ // , name: String

        val cameraPosition = CameraPosition.Builder()
            .target(latLng)
            .zoom(16f)
            .build()
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

    }




}