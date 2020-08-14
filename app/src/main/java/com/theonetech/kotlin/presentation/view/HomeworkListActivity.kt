package com.theonetech.kotlin.presentation.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.theonetech.kotlin.R
import com.theonetech.kotlin.databinding.ActivityHomeworkListBinding
import com.theonetech.kotlin.presentation.viewmodel.HomeworkListViewModel
/**
 * Created by Amit on 31,July,2020
 */
class HomeworkListActivity : AppCompatActivity() {
    private var homeworkListBinding: ActivityHomeworkListBinding? = null
    private var homeworkListViewModel: HomeworkListViewModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //bind layout for databinding
        homeworkListBinding = DataBindingUtil.setContentView<ViewDataBinding>(
            this,
            R.layout.activity_homework_list
        ) as ActivityHomeworkListBinding?
        homeworkListBinding?.lifecycleOwner = this
        //inti viewmodel
        homeworkListViewModel = this.homeworkListBinding?.let { HomeworkListViewModel(this, it) }
        homeworkListBinding?.homeworklist = homeworkListViewModel
        homeworkListBinding!!.setVariable(0, homeworkListViewModel)
    }
}