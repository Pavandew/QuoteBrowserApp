package com.example.quotesbrowserapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quotesbrowserapp.adapter.QuoteAdapter
import com.example.quotesbrowserapp.viewModel.QuoteViewModel
import com.example.quotesbrowserapp.databinding.FragmentFavoritesBinding

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private val vm: QuoteViewModel by activityViewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup Toolbar
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Favorites"

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed()
        }

        // Setup RecyclerView
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())

        // Load favorites
        vm.loadFavorites(requireContext())
        vm.favorites.observe(viewLifecycleOwner) { list ->
            binding.recycler.adapter = QuoteAdapter(list) { item ->
                val action = FavoritesFragmentDirections.actionFavoritesFragmentToDetailFragment(
                    quoteAuthor = item.author,
                    quoteImage = item.downloadUrl
                )
                findNavController().navigate(action)
            }
            binding.emptyView.visibility = if (list.isEmpty()) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
