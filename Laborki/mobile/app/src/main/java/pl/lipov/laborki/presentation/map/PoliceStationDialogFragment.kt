package pl.lipov.laborki.presentation.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import pl.lipov.laborki.R

class PoliceStationDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_police_stations,container,false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.setUp(R.id.option1,1)
        view.setUp(R.id.option2,2)
        view.setUp(R.id.option3,3)
    }

    private fun View.setUp(
        viewResId: Int,
        option: Int
    ){
        findViewById<CheckBox>(viewResId).setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){

            }
        }
    }

}