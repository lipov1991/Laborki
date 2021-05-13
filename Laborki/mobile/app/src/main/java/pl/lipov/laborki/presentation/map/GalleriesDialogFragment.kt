package pl.lipov.laborki.presentation.map

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.Gallery

class GalleriesDialogFragment(
        private val galleries: List<Gallery>
): BottomSheetDialogFragment() {

    private var recyclerView: RecyclerView? = null
    private val viewModel by inject<GalleryDialogViewModel>()


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_galleries,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.galleries)

        viewModel.run {
            ifDevelopmentBuilding.observe(viewLifecycleOwner, Observer {
                ifPlanBuilding = it
            })
        }

        recyclerView?.run {
            adapter = GalleryListAdapter(galleries,object : OnItemClickListener{
                override fun onItemClick(position: Int) {

                    if(!viewModel.ifPlanBuilding){
                        MaterialDialog(context).show {
                            title(text = galleries[viewModel.actualGallery.value!!].name)
                            message(text = "Czy na pewno chcesz zakończyć plan zagospodarowania bez wysłania go na serwer?")
                            positiveButton(text = "Tak") {
                                viewModel.setActualGallery(position)
                            }
                            negativeButton(text = "Nie")
                        }
                    }
                    else{
                        viewModel.setActualGallery(position)
                    }

                    dismiss()


                }
            })
            val itemDecoration = DividerItemDecoration(activity,LinearLayoutManager.HORIZONTAL)
            addItemDecoration(itemDecoration)
        }

    }


}