package id.ac.uasbunga.data.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import id.ac.uasbunga.data.local.DatabaseContract.BARANG_200020016.Companion.KODE
import id.ac.uasbunga.data.local.DatabaseContract.BARANG_200020016.Companion._ID
import java.sql.SQLException

class BarangHelper(context: Context) {
    private var dbHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var db: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = DatabaseContract.BARANG_200020016.TABLE_NAME
        private var INSTANCE: BarangHelper? = null
        fun getInstance(context: Context): BarangHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: BarangHelper(context)
            }
    }

    @Throws(SQLException::class)
    fun open() {
        db = dbHelper.writableDatabase
    }

    fun close() {
        dbHelper.close()
        if (db.isOpen) {
            db.close()
        }
    }

    fun getAllBarang(): Cursor {
        return db.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$_ID ASC"
        )
    }

    fun getAllBarangKode(): Cursor {
        return db.query(
            DATABASE_TABLE,
            arrayOf(KODE),
            null,
            null,
            null,
            null,
            "$_ID ASC"
        )
    }

    fun getBarangByKode(kodeBarang: String): Cursor {
        return db.query(
            DATABASE_TABLE,
            null,
            "$KODE = ?",
            arrayOf(kodeBarang),
            null,
            null,
            null,
        )
    }

    fun insert(values: ContentValues): Long {
        return db.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues): Int {
        return db.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    fun delete(id: String): Int {
        return db.delete(DATABASE_TABLE, "$_ID = ?", arrayOf(id))
    }
}