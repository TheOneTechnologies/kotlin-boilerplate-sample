package com.theonetech.kotlin.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theonetech.kotlin.R
import com.theonetech.kotlin.databinding.ItemDashboardBinding
import com.theonetech.kotlin.domain.interfaces.DashboardItemClickListener
import com.theonetech.kotlin.domain.model.Category
import java.util.*

/**
 * Created by Mahesh Keshvala on 28,July,2020
 */
class CategoryAdapter(
    private val mContext: Context?,
    private val categoryArrayList: ArrayList<Category>?,
    private val dashboardItemClickListener: DashboardItemClickListener

) :
    RecyclerView.Adapter<CategoryAdapter.MyViewHolder?>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        //bind layout for databinding
        return MyViewHolder(
            ItemDashboardBinding.bind(
                LayoutInflater.from(mContext).inflate(
                    R.layout.item_dashboard,
                    parent,
                    false
                )
            )
        )

    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {

        holder.bind(categoryArrayList?.get(position), mContext, dashboardItemClickListener)
    }

    override fun getItemCount(): Int {
        return categoryArrayList?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    class MyViewHolder(private var itemBinding: ItemDashboardBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        @SuppressLint("UseCompatLoadingForDrawables")
        //method to set data
        fun bind(
            category: Category?,
            context: Context?,
            dashboardItemClickListener: DashboardItemClickListener
        ) {
            itemBinding.textCategoryName.text = category?.categoryName
            itemBinding.imageCategory.setImageDrawable(context?.getDrawable(category?.categoryImage!!))
            itemBinding.rlMain.setOnClickListener {
                dashboardItemClickListener.onDashboardItemClick(category?.categoryName!!)
            }
        }
    }
}