package com.example.moviedatabase.ui.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.moviedatabase.R
import com.example.moviedatabase.databinding.FragmentMovieBinding

class MovieFragment : Fragment() {
    private var _binding : FragmentMovieBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroy() {
        if (view != null){
            val parentViewGroup = requireView().parent as ViewGroup?
            parentViewGroup?.removeAllViews()
        }
        super.onDestroy()
    }
}