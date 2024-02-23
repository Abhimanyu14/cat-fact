package com.makeappssimple.abhimanyu.catfact.android.ui.credits

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makeappssimple.abhimanyu.catfact.android.databinding.FragmentCreditsBinding

class CreditsFragment : Fragment() {
    private lateinit var binding: FragmentCreditsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentCreditsBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentCreditsTextviewApi.movementMethod = LinkMovementMethod.getInstance()
        binding.fragmentCreditsTextviewAppLogo.movementMethod = LinkMovementMethod.getInstance()
        binding.fragmentCreditsTextviewNoInternetConnection.movementMethod =
            LinkMovementMethod.getInstance()
    }
}
