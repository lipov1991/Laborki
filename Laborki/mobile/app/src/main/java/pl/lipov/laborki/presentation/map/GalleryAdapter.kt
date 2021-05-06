package pl.lipov.laborki.presentation.map

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.squareup.picasso.Picasso
import pl.lipov.laborki.R
import pl.lipov.laborki.data.repository.api.dto.GalleryDto

class GalleryAdapter(
    private val galleries: List<GalleryDto>,
    private val viewModel: MapViewModel,
    private val googleMap: GoogleMap
) : RecyclerView.Adapter<GalleryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_gallery, parent, false)
        return GalleryViewHolder(view)
    }

    override fun getItemCount(): Int = galleries.size

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val gallery = galleries[position]
        val galleryLatLng = LatLng(gallery.lat, gallery.lng)
        holder.name.text = gallery.name
        Picasso.get().load(gallery.url).into(holder.image)

        holder.itemView.setOnClickListener {
            viewModel.setUpGallery(googleMap, gallery.name, galleryLatLng)
        }
    }
}