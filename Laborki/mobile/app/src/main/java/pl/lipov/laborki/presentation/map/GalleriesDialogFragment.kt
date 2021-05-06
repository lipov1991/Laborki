package pl.lipov.laborki.presentation.map

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import pl.lipov.laborki.R
import pl.lipov.laborki.data.model.Gallery

class GalleriesDialogFragment(
        private val galleries: List<Gallery>
): BottomSheetDialogFragment() {

    private var recyclerView: RecyclerView? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_galleries,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.galleries)

        recyclerView?.run {
            adapter = GalleryListAdapter(galleries,object : OnItemClickListener{
                override fun onItemClick(position: Int) {
                    val name = galleries[position].name
                    dismiss()
                    Toast.makeText(activity, "Wybrano $name na pozycji $position",Toast.LENGTH_LONG).show()                }
            })
            val itemDecoration = DividerItemDecoration(activity,LinearLayoutManager.HORIZONTAL)
            addItemDecoration(itemDecoration)
        }

    }


}