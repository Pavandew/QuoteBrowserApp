package com.example.quotesbrowserapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import coil.load
import com.example.quotesbrowserapp.model.PicsumItem
import com.example.quotesbrowserapp.viewModel.QuoteViewModel
import com.example.quotesbrowserapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val vm: QuoteViewModel by activityViewModels()


    companion object {
        private const val TAG = "DetailFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Quote Detail"

        val args = DetailFragmentArgs.fromBundle(requireArguments())

        Log.d(TAG, "Quote Author: ${args.quoteAuthor}")
        Log.d(TAG, "Quote Image URL: ${args.quoteImage}")

        binding.quoteAuthor.text = args.quoteAuthor
        binding.detailImage.load(args.quoteImage)

        binding.favoriteButton.setOnClickListener {
            vm.addFavorite(
                PicsumItem(
                    id = "",
                    author = args.quoteAuthor,
                    width = 200,
                    height = 200,
                    url = "",
                    downloadUrl = args.quoteImage
                ),
                requireContext()
            )
            Toast.makeText(requireContext(), "Item added to favorites", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "Added to favorites: ${args.quoteAuthor}")
        }

        // Back button click
        binding.toolbar.setNavigationOnClickListener {
            requireActivity().onBackPressed() // Go back to HomeFragment
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
