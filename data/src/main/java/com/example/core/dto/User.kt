package com.example.core.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(val id: String, val name: String) : Parcelable