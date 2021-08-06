package com.makeappssimple.abhimanyu.catfact.android.ui.settings

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.makeappssimple.abhimanyu.catfact.android.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)
        activity?.actionBar?.setDisplayHomeAsUpEnabled(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentSettingsTextviewApi.movementMethod = LinkMovementMethod.getInstance()
        binding.fragmentSettingsTextviewAppLogo.movementMethod = LinkMovementMethod.getInstance()
        binding.fragmentSettingsTextviewNoInternetConnection.movementMethod =
            LinkMovementMethod.getInstance()
    }
}
