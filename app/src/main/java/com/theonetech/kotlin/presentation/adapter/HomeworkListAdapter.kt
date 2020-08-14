package com.theonetech.kotlin.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.theonetech.kotlin.R
import com.theonetech.kotlin.databinding.ItemHomeworkListBinding
import com.theonetech.kotlin.domain.model.Homework
import com.theonetech.kotlin.domain.utils.Utils
import java.util.*

/*
  Created by Amit on 31,July,2020
 */
class HomeworkListAdapter(
    mContext: Context,
    arrayListHomework: ArrayList<Homework>
) : RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    private val arrayListHomework: ArrayList<Homework>
    private var inflater: LayoutInflater?

    init {
        inflater = LayoutInflater.from(mContext)
        this.arrayListHomework = arrayListHomework
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        //bind layout for databinding
        val binding: ItemHomeworkListBinding? =
            inflater?.let {
                DataBindingUtil.inflate(
                    it,
                    R.layout.item_homework_list,
                    parent,
                    false
                )
            }
        return binding?.let { MyViewHolder(it) }!!

    }

    override fun getItemCount(): Int {
        return arrayListHomework.size

    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as MyViewHolder
        holder.bind(arrayListHomework[position])

    }

    class MyViewHolder(itemBinding: ItemHomeworkListBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        private val binding: ItemHomeworkListBinding = itemBinding
        //method to set data
        fun bind(
            homework: Homework?
        ) {
            binding.textSubjectName.text = homework?.subjectName
            binding.textDescription.text = homework?.description
            binding.textDay.text =
                Utils.getDayFromString(homework!!.date)
            binding.textMonthYear.text =
                Utils.getMonthYearFromString(homework.date)

        }
    }
}
