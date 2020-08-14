package com.theonetech.kotlin.domain.utils

import android.widget.ImageView
import coil.api.load
import com.theonetech.kotlin.R

class CommonImageLoader {
    fun loadImage(imageView: ImageView, imageUrl: String) {
        imageView.load(imageUrl) {
            placeholder(R.drawable.ic_birthday)
            error(R.drawable.ic_birthday)
        }
    }
}