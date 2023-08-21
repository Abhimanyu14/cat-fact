package com.makeappssimple.abhimanyu.catfact.android.ui.home

import android.os.Bundle
import android.view.*
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
    private val mainActivityRecyclerViewAdapter by lazy { HomeFragmentRecyclerViewAdapter() }
    private var initialConnectivityCheckCompleted: Boolean = false
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { context ->
            ConnectivityLiveData(context).observe(viewLifecycleOwner) { networkState ->
                if (networkState == NetworkState.CONNECTED) {
                    binding.fragmentHomeImageviewNoInternet.visibility = View.GONE
                    binding.fragmentHomeTextviewNoInternet.visibility = View.GONE
                    binding.fragmentHomeRecyclerView.visibility
                    fetchCatFacts()
                } else if (!initialConnectivityCheckCompleted) {
                    logError("No internet")
                    binding.fragmentHomeImageviewNoInternet.visibility = View.VISIBLE
                    binding.fragmentHomeTextviewNoInternet.visibility = View.VISIBLE
                }
                initialConnectivityCheckCompleted = true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_appbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_settings -> {
            val action = HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
            findNavController().navigate(action)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    private fun fetchCatFacts() {
        logError("fetching cat facts")
        lifecycleScope.launch {
            viewModel.pagedCatFacts.collectLatest {
                logError("submitting data")
                mainActivityRecyclerViewAdapter.submitData(it)
            }
        }
    }
}
