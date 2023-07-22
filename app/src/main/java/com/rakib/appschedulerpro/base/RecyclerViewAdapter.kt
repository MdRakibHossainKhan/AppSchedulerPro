package com.rakib.appschedulerpro.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class RecyclerViewAdapter<T : Any, viewDataBinding : ViewDataBinding> :
    RecyclerView.Adapter<RecyclerViewAdapter.Companion.BaseViewHolder<viewDataBinding>>() {
    var items = mutableListOf<T>()
    val bundle = Bundle()

    fun addItem(items: List<T>) {
        this.items = items as MutableList<T>
        notifyItemChanged(itemCount)
    }

    abstract fun getLayout(): Int

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BaseViewHolder<viewDataBinding>(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                getLayout(),
                parent,
                false
            )
        )

    companion object {
        class BaseViewHolder<viewDataBinding : ViewDataBinding>(val binding: viewDataBinding) :
            RecyclerView.ViewHolder(binding.root)
    }
}