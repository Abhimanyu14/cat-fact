package com.makeappssimple.abhimanyu.catfact.android.ui

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
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    
    private val viewModel: HomeViewModel by viewModels()
    private val mainActivityRecyclerViewAdapter by lazy { MainActivityRecyclerViewAdapter() }
    private var initialConnectivityCheckCompleted: Boolean = false
    private lateinit var binding: FragmentHomeBinding
    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater)
        
        // Allows Data Binding to Observe LiveData with the lifecycle of this Fragment
        binding.lifecycleOwner = this
        
        // Giving the binding access to the OverviewViewModel
        binding.viewModel = viewModel
        
        // Sets the adapter of the photosGrid RecyclerView
        binding.fragmentHomeRecyclerView.adapter =
            mainActivityRecyclerViewAdapter.withLoadStateFooter(
                footer = MainActivityRecyclerViewLoadingStateAdapter {
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
            ConnectivityLiveData(context).observe(viewLifecycleOwner, { networkState ->
                if (networkState == NetworkState.CONNECTED) {
                    binding.fragmentHomeImageviewNoInternet.visibility = View.GONE
                    binding.fragmentHomeTextviewNoInternet.visibility = View.GONE
                    binding.fragmentHomeRecyclerView.visibility
                    fetchCatFacts()
                } else {
                    if (!initialConnectivityCheckCompleted) {
                        binding.fragmentHomeImageviewNoInternet.visibility = View.VISIBLE
                        binding.fragmentHomeTextviewNoInternet.visibility = View.VISIBLE
                    }
                }
                initialConnectivityCheckCompleted = true
            })
        }
    }
    
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_appbar, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    
    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_credits -> {
            val action = HomeFragmentDirections.actionHomeFragmentToCreditsFragment()
            findNavController().navigate(action)
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }
    
    private fun fetchCatFacts() {
        lifecycleScope.launch {
            viewModel.pagedCatFacts.collectLatest {
                mainActivityRecyclerViewAdapter.submitData(it)
            }
        }
    }
}
