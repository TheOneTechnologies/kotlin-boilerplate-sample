package com.theonetech.kotlin.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.theonetech.kotlin.R
import com.theonetech.kotlin.databinding.ItemPhotoGalleryBinding
import com.theonetech.kotlin.domain.model.Albums
import com.theonetech.kotlin.domain.utils.CommonImageLoader
import java.util.*

/**
 * Created by Amit on 4,Aug,2020
 */
class AlbumListAdapter(
    private val mContext: Context?,
    private val arrayListAlbums: ArrayList<Albums>?
) :
    RecyclerView.Adapter<AlbumListAdapter.MyViewHolder?>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {

        //bind layout for databinding
        return MyViewHolder(
            ItemPhotoGalleryBinding.bind(
                LayoutInflater.from(mContext).inflate(
                    R.layout.item_photo_gallery,
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
        holder.bind(arrayListAlbums?.get(position))

    }

    override fun getItemCount(): Int {
        return arrayListAlbums?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    class MyViewHolder(private var itemBinding: ItemPhotoGalleryBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        //method to set data
        fun bind(
            album: Albums?
        ) {
            itemBinding.textAlbumName.text = album?.name
            val commonImageLoader = CommonImageLoader()
            commonImageLoader.loadImage(itemBinding.imageAlbum, album?.photoUrl.toString())

        }
    }
}