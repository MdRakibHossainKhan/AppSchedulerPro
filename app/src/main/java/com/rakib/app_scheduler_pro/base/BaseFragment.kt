package com.rakib.app_scheduler_pro.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

abstract class BaseFragment<T : ViewDataBinding, VM : ViewModel> : Fragment() {
    private lateinit var binidng: T
    private lateinit var viewModel: VM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binidng = DataBindingUtil.inflate(
            inflater,
            getLayout(),
            container,
            false
        )

        viewModel = ViewModelProvider(this)[getViewModel()]

        return binidng.root
    }

    abstract fun getLayout(): Int

    abstract fun getViewModel(): Class<VM>
}