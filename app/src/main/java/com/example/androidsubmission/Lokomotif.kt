package com.example.androidsubmission

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Lokomotif(
    val name: String,
    val description: String,
    val image: String
) : Parcelable
