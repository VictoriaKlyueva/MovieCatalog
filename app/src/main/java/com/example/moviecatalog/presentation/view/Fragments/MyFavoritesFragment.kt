package com.example.moviecatalog.presentation.view.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviecatalog.R
import com.example.moviecatalog.databinding.FragmentMoviesBinding
import com.example.moviecatalog.databinding.FragmentMyFavoritesBinding

class MyFavoritesFragment : Fragment() {
    private var _binding: FragmentMyFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyFavoritesBinding.inflate(
            inflater,
            container,
            false
        )



        return binding.root
    }
}