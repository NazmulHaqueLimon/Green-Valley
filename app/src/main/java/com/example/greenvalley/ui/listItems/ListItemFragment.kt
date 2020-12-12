package com.example.greenvalley.ui.listItems

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.greenvalley.databinding.FragmentListItemBinding


class ListItemFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding =FragmentListItemBinding.inflate(inflater,container,false)
        return binding.root

    }


}