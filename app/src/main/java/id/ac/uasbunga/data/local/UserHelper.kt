package id.ac.uasbunga.data.local

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import id.ac.uasbunga.data.local.DatabaseContract.USER_200020016.Companion.PASSWORD
import id.ac.uasbunga.data.local.DatabaseContract.USER_200020016.Companion.TABLE_NAME
import id.ac.uasbunga.data.local.DatabaseContract.USER_200020016.Companion.USERNAME
import id.ac.uasbunga.data.local.DatabaseContract.USER_200020016.Companion._ID
import java.sql.SQLException

class UserHelper(context: Context) {
    private var dbHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var db: SQLiteDatabase

    companion object {
        private const val DATABASE_TABLE = TABLE_NAME
        private var INSTANCE: UserHelper? = null
        fun getInstance(context: Context): UserHelper =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserHelper(context)
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

    fun getAllUser(): Cursor {
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

    fun getUser(id: String): Cursor {
        return db.query(
            DATABASE_TABLE,
            null,
            "$_ID = ?",
            arrayOf(id),
            null,
            null,
            null,
        )
    }

    fun insert(values: ContentValues): Long {
        return db.insert(DATABASE_TABLE, null, values)
    }

    fun update(id: String, values: ContentValues?): Int {
        return db.update(DATABASE_TABLE, values, "$_ID = ?", arrayOf(id))
    }

    fun delete(id: String): Int {
        return db.delete(DATABASE_TABLE, "$_ID = ?", arrayOf(id))
    }

    fun login(username: String, password: String): Cursor? {
        db = dbHelper.readableDatabase
        return db.query(
            DATABASE_TABLE,
            null,
            "$USERNAME = ? AND $PASSWORD = ?",
            arrayOf(username, password),
            null,
            null,
            null
        )
    }
}