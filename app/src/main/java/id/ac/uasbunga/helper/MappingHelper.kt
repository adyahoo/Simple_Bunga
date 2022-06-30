package id.ac.uasbunga.helper

import android.database.Cursor
import id.ac.uasbunga.data.local.DatabaseContract.BARANG_200020016.Companion.GAMBAR
import id.ac.uasbunga.data.local.DatabaseContract.BARANG_200020016.Companion.HARGA
import id.ac.uasbunga.data.local.DatabaseContract.BARANG_200020016.Companion.KODE
import id.ac.uasbunga.data.local.DatabaseContract.BARANG_200020016.Companion.NAMA
import id.ac.uasbunga.data.local.DatabaseContract.BARANG_200020016.Companion.STOK
import id.ac.uasbunga.data.local.DatabaseContract.USER_200020016.Companion.PASSWORD
import id.ac.uasbunga.data.local.DatabaseContract.USER_200020016.Companion.THUMBNAIL
import id.ac.uasbunga.data.local.DatabaseContract.USER_200020016.Companion.USERNAME
import id.ac.uasbunga.data.local.DatabaseContract.USER_200020016.Companion._ID
import id.ac.uasbunga.data.model.Barang
import id.ac.uasbunga.data.model.User

object MappingHelper {
    fun mapCursor(userCursor: Cursor?): User {
        var user = User()

        if (userCursor != null && userCursor.moveToFirst()) {
            do {
                val id = userCursor.getInt(userCursor.getColumnIndexOrThrow(_ID))
                val username = userCursor.getString(userCursor.getColumnIndexOrThrow(USERNAME))
                val password = userCursor.getString(userCursor.getColumnIndexOrThrow(PASSWORD))
                val thumbnail = userCursor.getString(userCursor.getColumnIndexOrThrow(THUMBNAIL))
                user = User(id, username, password, thumbnail)
            } while (userCursor.moveToNext())
        }

        return user
    }

    fun mapCursorAllUser(userCursor: Cursor?): ArrayList<User> {
        val allUser = ArrayList<User>()

        if (userCursor != null && userCursor.moveToFirst()) {
            do {
                val id = userCursor.getInt(userCursor.getColumnIndexOrThrow(_ID))
                val username = userCursor.getString(userCursor.getColumnIndexOrThrow(USERNAME))
                val password = userCursor.getString(userCursor.getColumnIndexOrThrow(PASSWORD))
                val thumbnail = userCursor.getString(userCursor.getColumnIndexOrThrow(THUMBNAIL))
                val user = User(id, username, password, thumbnail)
                allUser.add(user)
            } while (userCursor.moveToNext())
        }

        return allUser
    }

    fun mapCursorAllBarang(barangCursor: Cursor?): ArrayList<Barang> {
        val allBarang = ArrayList<Barang>()

        if (barangCursor != null && barangCursor.moveToFirst()) {
            do {
                val id = barangCursor.getInt(barangCursor.getColumnIndexOrThrow(_ID))
                val nama = barangCursor.getString(barangCursor.getColumnIndexOrThrow(NAMA))
                val kode = barangCursor.getString(barangCursor.getColumnIndexOrThrow(KODE))
                val stok = barangCursor.getInt(barangCursor.getColumnIndexOrThrow(STOK))
                val harga = barangCursor.getInt(barangCursor.getColumnIndexOrThrow(HARGA))
                val gambar = barangCursor.getString(barangCursor.getColumnIndexOrThrow(GAMBAR))
                val barang = Barang(id, nama, kode, stok, harga, gambar)
                allBarang.add(barang)
            } while (barangCursor.moveToNext())
        }

        return allBarang
    }

    fun mapCursorAllBarangKode(barangCursor: Cursor?): ArrayList<String> {
        val allBarangKode = ArrayList<String>()

        if (barangCursor != null && barangCursor.moveToFirst()) {
            do {
                val kode = barangCursor.getString(barangCursor.getColumnIndexOrThrow(KODE))
                allBarangKode.add(kode)
            } while (barangCursor.moveToNext())
        }

        return allBarangKode
    }

    fun mapCursorBarang(barangCursor: Cursor?): Barang {
        var barang = Barang()

        if (barangCursor != null && barangCursor.moveToFirst()) {
            do {
                val id = barangCursor.getInt(barangCursor.getColumnIndexOrThrow(_ID))
                val nama = barangCursor.getString(barangCursor.getColumnIndexOrThrow(NAMA))
                val kode = barangCursor.getString(barangCursor.getColumnIndexOrThrow(KODE))
                val stok = barangCursor.getInt(barangCursor.getColumnIndexOrThrow(STOK))
                val harga = barangCursor.getInt(barangCursor.getColumnIndexOrThrow(HARGA))
                val gambar = barangCursor.getString(barangCursor.getColumnIndexOrThrow(GAMBAR))
                barang = Barang(id, nama, kode, stok, harga, gambar)
            } while (barangCursor.moveToNext())
        }

        return barang
    }
}