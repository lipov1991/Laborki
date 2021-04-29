package pl.lipov.laborki.presentation.map

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.lipov.laborki.R

class GalleryViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {
//    przechowywanie referencji do kontrolek

    val image = view.findViewById<ImageView>(R.id.image)
    val name = view.findViewById<TextView>(R.id.name)

}