package id.ac.uasbunga.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Barang(
    var id: Int = 0,
    var nama: String = "",
    var kode: String = "",
    var stok: Int = 0,
    var harga: Int = 0,
    var gambar: String = "",
) : Parcelable