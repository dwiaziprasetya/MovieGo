package com.example.moviedatabase.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.moviedatabase.R
import com.example.moviedatabase.adapter.ImageSliderAdapter
import com.example.moviedatabase.databinding.FragmentHomeBinding
import com.example.moviedatabase.model.ImageData

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter : ImageSliderAdapter
    private val list = ArrayList<ImageData>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.addAll(getListImageSlider())
        adapter = ImageSliderAdapter(list)
        binding.viewPager.adapter = adapter
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("Recycle")
    private fun getListImageSlider() : ArrayList<ImageData> {
        val dataImage = resources.obtainTypedArray(R.array.image_slider)
        val listImage = ArrayList<ImageData>()
        for (i in 0 until dataImage.length()) {
            val image = ImageData(dataImage.getResourceId(i,0))
            listImage.add(image)
        }
        return listImage
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}