package com.myproject.todoappwithnodejs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB:ViewBinding>(private val bindinginflater:(inflater:LayoutInflater) -> VB):Fragment() {


   private var _binding:VB?=null
    val binding:VB
    get() = _binding as VB



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding=bindinginflater.invoke(inflater)

        if (_binding==null)
        {
            throw IllegalArgumentException("Binding cant be null")
        }
        return binding.root

    }


}