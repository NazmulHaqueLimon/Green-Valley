package com.example.greenvalley.ui.myGarden

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.greenvalley.R
import com.example.greenvalley.databinding.FragmentMyGardenBinding


class MyGardenFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       // return inflater.inflate(R.layout.fragment_my_garden, container, false)
        val binding=FragmentMyGardenBinding.inflate(inflater,container,false)
        return binding.root
    }


}