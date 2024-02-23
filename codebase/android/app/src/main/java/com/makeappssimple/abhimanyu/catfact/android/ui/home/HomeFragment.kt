package com.makeappssimple.abhimanyu.catfact.android.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.makeappssimple.abhimanyu.catfact.android.R
import com.makeappssimple.abhimanyu.catfact.android.databinding.FragmentHomeBinding
import com.makeappssimple.abhimanyu.catfact.android.utils.ConnectivityLiveData
import com.makeappssimple.abhimanyu.catfact.android.utils.NetworkState
import com.makeappssimple.abhimanyu.catfact.android.utils.logError
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()
    private val mainActivityRecyclerViewAdapter by lazy {
        HomeFragmentRecyclerViewAdapter()
    }
    private var initialConnectivityCheckCompleted: Boolean = false
    private lateinit var binding: FragmentHomeBinding

    @Suppress("DEPRECATION")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)

        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this

        // Sets the adapter of the photosGrid RecyclerView
        binding.fragmentHomeRecyclerView.adapter =
            mainActivityRecyclerViewAdapter.withLoadStateFooter(
                footer = HomeFragmentRecyclerViewLoadingStateAdapter {
                    mainActivityRecyclerViewAdapter.retry()
                }
            )

        // For appbar actions
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { context ->
            ConnectivityLiveData(context).observe(viewLifecycleOwner) { networkState ->
                if (networkState == NetworkState.CONNECTED) {
                    binding.fragmentHomeImageviewNoInternet.visibility = View.GONE
                    binding.fragmentHomeTextviewNoInternet.visibility = View.GONE
                    binding.fragmentHomeRecyclerView.visibility
                    fetchCatFacts()
                } else if (!initialConnectivityCheckCompleted) {
                    logError(
                        message = "No internet",
                    )
                    binding.fragmentHomeImageviewNoInternet.visibility = View.VISIBLE
                    binding.fragmentHomeTextviewNoInternet.visibility = View.VISIBLE
                }
                initialConnectivityCheckCompleted = true
            }
        }
    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater,
    ) {
        inflater.inflate(R.menu.menu_appbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    @Suppress("DEPRECATION")
    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(
        item: MenuItem,
    ): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                val directions = HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
                findNavController().navigate(
                    directions = directions,
                )
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun fetchCatFacts() {
        logError(
            message = "fetching cat facts",
        )
        lifecycleScope.launch {
            viewModel.pagedCatFacts.collectLatest {
                logError(
                    message = "submitting data",
                )
                mainActivityRecyclerViewAdapter.submitData(it)
            }
        }
    }
}
