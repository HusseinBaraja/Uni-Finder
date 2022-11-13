package com.example.mae_project.adaptors

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mae_project.adaptors.viewholders.UniViewHolder
import com.example.mae_project.databinding.UniitemBinding
import com.example.mae_project.dataclasses.UniversityDataClass
import com.example.mae_project.utils.setImage

class UniAdaptor(val context: Context) : RecyclerView.Adapter<UniViewHolder>() {
    var uniList: MutableList<UniversityDataClass> = mutableListOf()
    var onItemCLick: ((UniversityDataClass) -> Unit?)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UniViewHolder {
        return UniViewHolder(UniitemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: UniViewHolder, position: Int) {
        val singleItem = mList.currentList[position]
        with(holder.binding) {
            tvTitle.text = singleItem.uniName
            tvSubTitle.text = singleItem.uniLocation
            mImage.setImage(singleItem.uniImage)
            mItem.setOnClickListener {
                onItemCLick?.invoke(singleItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return mList.currentList.size
    }

    private val differCallBack = object : DiffUtil.ItemCallback<UniversityDataClass>() {
        override fun areItemsTheSame(
            oldItem: UniversityDataClass,
            newItem: UniversityDataClass
        ): Boolean {
            return oldItem.pushId == newItem.pushId
        }
        override fun areContentsTheSame(
            oldItem: UniversityDataClass,
            newItem: UniversityDataClass
        ): Boolean {
            return oldItem == newItem
        }
    }
    var mList = AsyncListDiffer(this, differCallBack)
}