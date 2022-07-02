package id.ac.uasbungaa.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import kotlin.jvm.internal.DefaultConstructorMarker;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "UAS200020016";
    private static final int DATABASE_VERSION = 2;
    private static final String CREATE_TABLE_USER = "CREATE TABLE USER_200020016 (_id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT NOT NULL, password TEXT NOT NULL, thumbnail TEXT NOT NULL)";
    private static final String CREATE_TABLE_BARANG = "CREATE TABLE BARANG_200020016 (_id INTEGER PRIMARY KEY AUTOINCREMENT, nama TEXT NOT NULL, kode TEXT NOT NULL, stok INTEGER NOT NULL, harga INTEGER NOT NULL, gambar TEXT NOT NULL)";
    @NotNull
    public static final DatabaseHelper.Companion Companion = new DatabaseHelper.Companion((DefaultConstructorMarker) null);

    public void onCreate(@Nullable SQLiteDatabase db) {
        if (db != null) {
            db.execSQL(CREATE_TABLE_USER);
        }

        if (db != null) {
            db.execSQL(CREATE_TABLE_BARANG);
        }

    }

    public void onUpgrade(@Nullable SQLiteDatabase db, int oldVersion, int newVersion) {
        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS USER_200020016");
        }

        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS BARANG_200020016");
        }

        this.onCreate(db);
    }

    public DatabaseHelper(@NotNull Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }
}
