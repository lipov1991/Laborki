package pl.lipov.laborki.presentation.map

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.lipov.laborki.R

class GalleryViewHolder(
        view: View
): RecyclerView.ViewHolder(view) {

    val image: ImageView = view.findViewById(R.id.image)
    val name: TextView = view.findViewById(R.id.name)

}