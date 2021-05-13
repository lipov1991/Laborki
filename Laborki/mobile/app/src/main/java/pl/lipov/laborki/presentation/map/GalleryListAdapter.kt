package pl.lipov.laborki.presentation.map

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.squareup.picasso.Picasso
import pl.lipov.laborki.R
import pl.lipov.laborki.common.utils.MapUtils
import pl.lipov.laborki.data.repository.api.dto.GalleryDto

class GalleryListAdapter(
    private val galleries: List<GalleryDto>,
    private val mapUtils: MapUtils,
    private val myMap: GoogleMap,
    private val context: Context
) : RecyclerView.Adapter<GalleryViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gallery, parent, false)
        return GalleryViewHolder(view)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val gallery = galleries[position]
        val latLngGallery = LatLng(gallery.lat, gallery.lng)
        holder.name.text = gallery.name
        Picasso.get().load(gallery.url).into(holder.image)

        holder.itemView.setOnClickListener {
            if (mapUtils.areaButttonAlreadyPressed) {
                mapUtils.areaButttonAlreadyPressed = false
                mapUtils.setUpGallery(myMap, gallery.name, latLngGallery)
            } else {
                MaterialDialog(context).show {
                    title(text = gallery.name)
                    message(text = "Czy na pewno chcesz zakończyć plan zagospodarowania bez wysłania go na serwer?")
                    positiveButton(text = "Tak") {
                        it.cancel()
                        mapUtils.setUpGallery(myMap, gallery.name, latLngGallery)
                    }
                    negativeButton(text = "Nie") {
                        it.cancel()
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int = galleries.size
}

