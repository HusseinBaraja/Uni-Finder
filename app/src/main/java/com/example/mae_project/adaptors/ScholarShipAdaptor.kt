package com.example.mae_project.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mae_project.R
import com.example.mae_project.adaptors.viewholders.UniViewHolder
import com.example.mae_project.databinding.UniitemBinding
import com.example.mae_project.dataclasses.ScholarDataClass
import com.example.mae_project.utils.setImage

class ScholarShipAdaptor(val context: Context) : RecyclerView.Adapter<UniViewHolder>() {
    var scholarList: MutableList<ScholarDataClass> = mutableListOf()
    var onItemCLick: ((ScholarDataClass) -> Unit?)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniViewHolder {
        return UniViewHolder(UniitemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: UniViewHolder, position: Int) {
        val singleItem = mList.currentList[position]
        with(holder.binding) {
            tvTitle.text = singleItem.title
            tvSubTitle.text = singleItem.content
            mImage.setImage(R.drawable.graduation)
            mItem.setOnClickListener {
                onItemCLick?.invoke(singleItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.currentList.size
    }


    private val differCallBack = object : DiffUtil.ItemCallback<ScholarDataClass>() {
        override fun areItemsTheSame(
            oldItem: ScholarDataClass,
            newItem: ScholarDataClass
        ): Boolean {
            return oldItem == newItem

        }

        override fun areContentsTheSame(
            oldItem: ScholarDataClass,
            newItem: ScholarDataClass
        ): Boolean {
            return oldItem == newItem
        }
    }
    var mList = AsyncListDiffer(this, differCallBack)
}