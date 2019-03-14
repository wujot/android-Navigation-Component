package com.hemmersbach.android.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Person(var firstName: String, var surname: String, var gender: String? = null, var about: MutableList<String>? = null) :
    Parcelable
