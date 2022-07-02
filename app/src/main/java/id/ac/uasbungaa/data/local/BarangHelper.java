package id.ac.uasbungaa.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.jetbrains.annotations.NotNull;

public final class BarangHelper {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private static final String DATABASE_TABLE = "BARANG_200020016";

    public final void open() {
        this.db = this.dbHelper.getWritableDatabase();
    }

    public final void close() {
        this.dbHelper.close();
        SQLiteDatabase db = this.db;
        if (db.isOpen()) {
            db = this.db;
            db.close();
        }
    }

    @NotNull
    public final Cursor getAllBarang() {
        SQLiteDatabase db = this.db;

        return db.query(DATABASE_TABLE, (String[]) null, (String) null, (String[]) null, (String) null, (String) null, "_id ASC");
    }

    @NotNull
    public final Cursor getAllBarangKode() {
        SQLiteDatabase db = this.db;

        return db.query(DATABASE_TABLE, new String[]{"kode"}, (String) null, (String[]) null, (String) null, (String) null, "_id ASC");
    }

    @NotNull
    public final Cursor getBarangByKode(@NotNull String kodeBarang) {
        SQLiteDatabase db = this.db;

        return db.query(DATABASE_TABLE, (String[]) null, "kode = ?", new String[]{kodeBarang}, (String) null, (String) null, (String) null);
    }

    public final long insert(@NotNull ContentValues values) {
        SQLiteDatabase db = this.db;

        return db.insert(DATABASE_TABLE, (String) null, values);
    }

    public final int update(@NotNull String id, @NotNull ContentValues values) {
        SQLiteDatabase db = this.db;

        return db.update(DATABASE_TABLE, values, "_id = ?", new String[]{id});
    }

    public final int delete(@NotNull String id) {
        SQLiteDatabase db = this.db;

        return db.delete(DATABASE_TABLE, "_id = ?", new String[]{id});
    }

    public BarangHelper(@NotNull Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }
}