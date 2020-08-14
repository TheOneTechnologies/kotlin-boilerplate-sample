package com.theonetech.kotlin.domain.model

/**
 * Created by Amit on 30,July,2020
 */
data class Homework(
    val id: Int,
    val userName: String,
    val subjectName: String,
    val date: String,
    val schoolId: Int,
    val standardId: Int,
    val subjectId: Int,
    val boardId: Int,
    val divisionId: Int,
    val dueDate: String,
    val description: String,
    val hasAttachment: Boolean
)