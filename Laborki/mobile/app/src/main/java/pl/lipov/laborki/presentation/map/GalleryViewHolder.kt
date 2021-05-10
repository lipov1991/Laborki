package pl.lipov.laborki.presentation.map

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.lipov.laborki.R

class GalleryViewHolder(
    view: View,
    private val listener: OnItemClickListner
) : RecyclerView.ViewHolder(view), View.OnClickListener {

    //val image = view.findViewById<ImageView>(R.id.image)
    val image: ImageView = view.findViewById(R.id.image) // ^ to samo
    val name: TextView = view.findViewById(R.id.name)

    init{
        view.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (adapterPosition != RecyclerView.NO_POSITION){
            listener.onItemClick(adapterPosition)
        }
    }
}

