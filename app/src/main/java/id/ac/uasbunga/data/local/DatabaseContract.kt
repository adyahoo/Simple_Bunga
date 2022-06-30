package id.ac.uasbunga.data.local

import android.provider.BaseColumns

internal class DatabaseContract {
    internal class USER_200020016 : BaseColumns {
        companion object {
            const val TABLE_NAME = "USER_200020016"
            const val _ID = "_id"
            const val USERNAME = "username"
            const val PASSWORD = "password"
            const val THUMBNAIL = "thumbnail"
        }
    }

    internal class BARANG_200020016 : BaseColumns {
        companion object {
            const val TABLE_NAME = "BARANG_200020016"
            const val _ID = "_id"
            const val NAMA = "nama"
            const val KODE = "kode"
            const val STOK = "stok"
            const val HARGA = "harga"
            const val GAMBAR = "gambar"
        }
    }
}