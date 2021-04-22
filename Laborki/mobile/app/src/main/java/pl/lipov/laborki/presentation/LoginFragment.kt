package pl.lipov.laborki.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.FragmentLoginBinding
import pl.lipov.laborki.presentation.map.MapActivity

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        loginViewModel.run {

            onEvent.observe(viewLifecycleOwner, Observer {
                gestureCheck(binding.icLoginAttempt, it)
                onEvent.removeObservers(viewLifecycleOwner)
            })
        }

        binding.mapButton.setOnClickListener {

            activity?.let { parentActivity ->
                val intent = Intent(parentActivity, MapActivity::class.java)
                startActivity(intent)
            }
            binding.mapButton.isEnabled = false
        }
    }
}
