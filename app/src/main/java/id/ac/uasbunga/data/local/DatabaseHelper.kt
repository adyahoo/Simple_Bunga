package id.ac.uasbunga.data.local

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import id.ac.uasbunga.data.local.DatabaseContract.USER_200020016.Companion.PASSWORD
import id.ac.uasbunga.data.local.DatabaseContract.USER_200020016.Companion.TABLE_NAME
import id.ac.uasbunga.data.local.DatabaseContract.USER_200020016.Companion.THUMBNAIL
import id.ac.uasbunga.data.local.DatabaseContract.USER_200020016.Companion.USERNAME
import id.ac.uasbunga.data.local.DatabaseContract.USER_200020016.Companion._ID

internal class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "UAS200020016"
        private const val DATABASE_VERSION = 2
        private const val CREATE_TABLE_USER = "CREATE TABLE $TABLE_NAME" +
                " (${_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${USERNAME} TEXT NOT NULL," +
                " ${PASSWORD} TEXT NOT NULL," +
                " ${THUMBNAIL} TEXT NOT NULL)"
        private const val CREATE_TABLE_BARANG =
            "CREATE TABLE ${DatabaseContract.BARANG_200020016.TABLE_NAME}" +
                    " (${DatabaseContract.BARANG_200020016._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " ${DatabaseContract.BARANG_200020016.NAMA} TEXT NOT NULL," +
                    " ${DatabaseContract.BARANG_200020016.KODE} TEXT NOT NULL," +
                    " ${DatabaseContract.BARANG_200020016.STOK} INTEGER NOT NULL," +
                    " ${DatabaseContract.BARANG_200020016.HARGA} INTEGER NOT NULL," +
                    " ${DatabaseContract.BARANG_200020016.GAMBAR} TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(CREATE_TABLE_USER)
        db?.execSQL(CREATE_TABLE_BARANG)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db?.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.BARANG_200020016.TABLE_NAME}")
        onCreate(db)
    }
}