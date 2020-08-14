package com.theonetech.kotlin.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.theonetech.kotlin.R
import com.theonetech.kotlin.databinding.ActivityPhotoGalleryBinding
import com.theonetech.kotlin.presentation.viewmodel.PhotoGalleryViewModel

/**
 * Created by Amit on 4,Aug,2020
 */
class PhotoGalleryActivity : AppCompatActivity() {

    private var photoGalleryBinding: ActivityPhotoGalleryBinding? = null
    private var photoGalleryViewModel: PhotoGalleryViewModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //bind layout for databinding
        photoGalleryBinding = DataBindingUtil.setContentView<ViewDataBinding>(
            this,
            R.layout.activity_photo_gallery
        ) as ActivityPhotoGalleryBinding?
        photoGalleryBinding?.lifecycleOwner = this
        //init viewmodel
        photoGalleryViewModel = this.photoGalleryBinding?.let { PhotoGalleryViewModel(this, it) }
        photoGalleryBinding?.albumList = photoGalleryViewModel
        photoGalleryBinding!!.setVariable(0, photoGalleryViewModel)
    }
}