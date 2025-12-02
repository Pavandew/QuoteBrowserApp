package com.example.quotesbrowserapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quotesbrowserapp.adapter.QuoteAdapter
import com.example.quotesbrowserapp.viewModel.QuoteViewModel
import com.example.quotesbrowserapp.R
import com.example.quotesbrowserapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val vm: QuoteViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true) // Enable menu for TopAppBar
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.title = "Home Screen"

        // Setup RecyclerView
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())

        // Observe API data
        vm.picsList.observe(viewLifecycleOwner) { list ->
            list.forEach { item ->
                Log.d("HomeFragment", "Author: ${item.author}, URL: ${item.downloadUrl}")
            }

            binding.recycler.adapter = QuoteAdapter(list) { item ->
                val imageUrl = item.downloadUrl ?: ""
                if (imageUrl.isNotEmpty()) {
                    Log.d("HomeFragment", "Navigating to Detail with $imageUrl")
                    val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(
                        quoteAuthor = item.author,
                        quoteImage = imageUrl
                    )
                    findNavController().navigate(action)
                }
            }
        }

    }

    // TopAppBar menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_app_bar_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_favorites -> {
                // Navigate to FavoritesFragment
                findNavController().navigate(R.id.favoritesFragment)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}