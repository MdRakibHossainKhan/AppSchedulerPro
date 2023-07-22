package com.rakib.appschedulerpro.adapter

import android.app.AlertDialog
import android.content.Context
import com.rakib.appschedulerpro.R
import com.rakib.appschedulerpro.base.RecyclerViewAdapter
import com.rakib.appschedulerpro.databinding.AppListItemBinding
import com.rakib.appschedulerpro.listener.SchedulerCallBack
import com.rakib.appschedulerpro.model.AppData

class AppListAdapter(
    private val context: Context,
    private val alertDialog: AlertDialog,
    private val callBack: SchedulerCallBack
) : RecyclerViewAdapter<AppData, AppListItemBinding>() {
    override fun getLayout() = R.layout.app_list_item

    override fun onBindViewHolder(
        viewHolder: Companion.BaseViewHolder<AppListItemBinding>,
        position: Int
    ) {
        viewHolder.binding.apply {
            val item = items[position]
            model = item
        }

        viewHolder.binding.listItemLinearLayout.setOnClickListener {
            alertDialog.cancel()
            callBack.passData(items[position])
        }

        val drawable = context.packageManager.getApplicationIcon(items[position].packageName)

        viewHolder.binding.appLogoImageView.setImageDrawable(drawable)
    }
}