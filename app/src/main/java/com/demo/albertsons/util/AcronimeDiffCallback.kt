package com.demo.albertsons.util

import androidx.recyclerview.widget.DiffUtil
import com.demo.albertsons.data.model.Lf

class AcronimeDiffCallback(private val oldList: List<Lf>, private val newList: List<Lf>)
    : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
       return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].lf == newList[newItemPosition].lf
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return oldItem.lf.equals(newItem.lf)
    }
}