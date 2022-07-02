package id.ac.uasbungaa.data.local;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.SQLException;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class UserHelper {
    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private static final String DATABASE_TABLE = "USER_200020016";

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
    public final Cursor getAllUser() {
        SQLiteDatabase db = this.db;

        return db.query(DATABASE_TABLE, (String[]) null, (String) null, (String[]) null, (String) null, (String) null, "_id ASC");
    }

    public final long insert(@NotNull ContentValues values) {
        SQLiteDatabase db = this.db;

        return db.insert(DATABASE_TABLE, (String) null, values);
    }

    public final int update(@NotNull String id, @Nullable ContentValues values) {
        SQLiteDatabase db = this.db;

        return db.update(DATABASE_TABLE, values, "_id = ?", new String[]{id});
    }

    public final int delete(@NotNull String id) {
        SQLiteDatabase db = this.db;

        return db.delete(DATABASE_TABLE, "_id = ?", new String[]{id});
    }

    @Nullable
    public final Cursor login(@NotNull String username, @NotNull String password) {
        this.db = this.dbHelper.getReadableDatabase();
        SQLiteDatabase db = this.db;

        return db.query(DATABASE_TABLE, (String[]) null, "username = ? AND password = ?", new String[]{username, password}, (String) null, (String) null, (String) null);
    }

    public UserHelper(@NotNull Context context) {
        this.dbHelper = new DatabaseHelper(context);
    }
}