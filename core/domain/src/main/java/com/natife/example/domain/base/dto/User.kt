package com.natife.example.domain.base.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(val id: String, val name: String) : Parcelable