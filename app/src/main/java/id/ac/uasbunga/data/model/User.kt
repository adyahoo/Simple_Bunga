package id.ac.uasbunga.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id: Int = 0,
    var username: String = "",
    var password: String = "",
    var thumbnail: String = ""
) : Parcelable
