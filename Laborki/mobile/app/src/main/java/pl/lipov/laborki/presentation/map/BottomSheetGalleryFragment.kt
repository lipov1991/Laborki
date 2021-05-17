package pl.lipov.laborki.presentation.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.Gallery


class BottomSheetGalleryFragment(

    private val galleries: List<Gallery>,
    private val ifUploadClick : Boolean

): BottomSheetDialogFragment() {

    var galleryList : RecyclerView? = null
    private val viewModel by inject<GalleryViewModel>()

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

                 if (!ifUploadClick){
                     MaterialDialog(context).show {
                         title(text = galleries[position].name)
                         message(text = "Czy na pewno chcesz zakończyć plan zagospodarowania bez wysłania go na serwer?")
                         positiveButton(text = "Tak") {
                             viewModel.currentGalleryPosition.postValue(position)
                         }
                         negativeButton(text = "Nie")
                     }

                 }
                    else{
                     viewModel.currentGalleryPosition.postValue(position)

                 }
                    dismiss()

                }
            })
            val itemDecoration = DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL)
            addItemDecoration(itemDecoration)
        }
    }

}