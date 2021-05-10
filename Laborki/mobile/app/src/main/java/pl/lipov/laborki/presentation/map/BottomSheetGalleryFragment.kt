package pl.lipov.laborki.presentation.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.Gallery

class BottomSheetGalleryFragment(

    private val galleries: List<Gallery>

): BottomSheetDialogFragment() {

    var galleryList : RecyclerView? = null
    private val viewModdel by inject<GalleryViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_gallery,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        galleryList = view.findViewById(R.id.gallries)

        galleryList?.run{
            adapter = GalleryAdapter(galleries, object: OnItemClickListner{
                override fun onItemClick(position: Int) {

                    viewModdel.currentGalleryPosition.postValue(position)

                    //val name = galleries[position].name
                    dismiss() // znika dialog


                }
            })
            val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL)
            addItemDecoration(itemDecoration)
        }
    }

}