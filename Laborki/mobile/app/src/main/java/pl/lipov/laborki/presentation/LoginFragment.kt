package pl.lipov.laborki.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import pl.lipov.laborki.R
import pl.lipov.laborki.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModel()
    private lateinit var binding: FragmentLoginBinding


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        return binding.root
    }

    override fun onViewCreated(
            view: View,
            savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val starIdList =
                listOf(
                        binding.icFirstLogin,
                        binding.icSecondLogin,
                        binding.icThirdLogin,
                        binding.icFourthLogin
                )

        viewModel.run {
            onEvent.observe(viewLifecycleOwner, Observer {
                addCurrentSample(it)
                passwordInput(starIdList)
                passwordCheck(starIdList)?.let {
                    Toast.makeText(context, it.second, Toast.LENGTH_LONG).show()
                    it.first?.let {
                        onEvent.removeObservers(viewLifecycleOwner)
                    }
                }
            })
        }
    }
}