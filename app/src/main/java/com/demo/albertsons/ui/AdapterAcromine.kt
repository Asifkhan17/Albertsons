package com.demo.albertsons.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.demo.albertsons.data.model.Lf
import com.demo.albertsons.databinding.ListItemAcromineBinding
import com.demo.albertsons.util.AcronimeDiffCallback


class AdapterAcromine : RecyclerView.Adapter<AdapterAcromine.AcromineViewHolder>() {
    private var listAcromine = mutableListOf<Lf>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcromineViewHolder {
        val binding =
            ListItemAcromineBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AcromineViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AcromineViewHolder, position: Int) {
        val currentItem = listAcromine[position]
        holder.bind(currentItem)
    }

    inner class AcromineViewHolder(private val binding: ListItemAcromineBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Lf) {
            binding.apply {
                tvTitle.text = item.lf
            }
        }
    }

    override fun getItemCount(): Int {
        return listAcromine.size
    }

    fun updateListItems(list: List<Lf>) {
        val diffCallback = AcronimeDiffCallback(this.listAcromine, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listAcromine.clear()
        this.listAcromine.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }
}

