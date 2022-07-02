package id.ac.uasbungaa.helper;

import android.database.Cursor;

import id.ac.uasbungaa.data.model.Barang;
import id.ac.uasbungaa.data.model.User;

import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class MappingHelper {
    @NotNull
    public static final MappingHelper INSTANCE;

    @NotNull
    public final User mapCursor(@Nullable Cursor userCursor) {
        User user = new User();
        if (userCursor != null && userCursor.moveToFirst()) {
            do {
                int id = userCursor.getInt(userCursor.getColumnIndexOrThrow("_id"));
                String username = userCursor.getString(userCursor.getColumnIndexOrThrow("username"));
                String password = userCursor.getString(userCursor.getColumnIndexOrThrow("password"));
                String thumbnail = userCursor.getString(userCursor.getColumnIndexOrThrow("thumbnail"));
                user = new User(id, username, password, thumbnail);
            } while (userCursor.moveToNext());
        }

        return user;
    }

    @NotNull
    public final ArrayList mapCursorAllUser(@Nullable Cursor userCursor) {
        ArrayList allUser = new ArrayList();
        if (userCursor != null && userCursor.moveToFirst()) {
            do {
                int id = userCursor.getInt(userCursor.getColumnIndexOrThrow("_id"));
                String username = userCursor.getString(userCursor.getColumnIndexOrThrow("username"));
                String password = userCursor.getString(userCursor.getColumnIndexOrThrow("password"));
                String thumbnail = userCursor.getString(userCursor.getColumnIndexOrThrow("thumbnail"));
                User user = new User(id, username, password, thumbnail);
                allUser.add(user);
            } while (userCursor.moveToNext());
        }

        return allUser;
    }

    @NotNull
    public final ArrayList mapCursorAllBarang(@Nullable Cursor barangCursor) {
        ArrayList allBarang = new ArrayList();
        if (barangCursor != null && barangCursor.moveToFirst()) {
            do {
                int id = barangCursor.getInt(barangCursor.getColumnIndexOrThrow("_id"));
                String nama = barangCursor.getString(barangCursor.getColumnIndexOrThrow("nama"));
                String kode = barangCursor.getString(barangCursor.getColumnIndexOrThrow("kode"));
                int stok = barangCursor.getInt(barangCursor.getColumnIndexOrThrow("stok"));
                int harga = barangCursor.getInt(barangCursor.getColumnIndexOrThrow("harga"));
                String gambar = barangCursor.getString(barangCursor.getColumnIndexOrThrow("gambar"));
                Barang barang = new Barang(id, nama, kode, stok, harga, gambar);
                allBarang.add(barang);
            } while (barangCursor.moveToNext());
        }

        return allBarang;
    }

    @NotNull
    public final ArrayList mapCursorAllBarangKode(@Nullable Cursor barangCursor) {
        ArrayList allBarangKode = new ArrayList();
        if (barangCursor != null && barangCursor.moveToFirst()) {
            do {
                String kode = barangCursor.getString(barangCursor.getColumnIndexOrThrow("kode"));
                allBarangKode.add(kode);
            } while (barangCursor.moveToNext());
        }

        return allBarangKode;
    }

    @NotNull
    public final Barang mapCursorBarang(@Nullable Cursor barangCursor) {
        Barang barang = new Barang();
        if (barangCursor != null && barangCursor.moveToFirst()) {
            do {
                int id = barangCursor.getInt(barangCursor.getColumnIndexOrThrow("_id"));
                String nama = barangCursor.getString(barangCursor.getColumnIndexOrThrow("nama"));
                String kode = barangCursor.getString(barangCursor.getColumnIndexOrThrow("kode"));
                int stok = barangCursor.getInt(barangCursor.getColumnIndexOrThrow("stok"));
                int harga = barangCursor.getInt(barangCursor.getColumnIndexOrThrow("harga"));
                String gambar = barangCursor.getString(barangCursor.getColumnIndexOrThrow("gambar"));
                barang = new Barang(id, nama, kode, stok, harga, gambar);
            } while (barangCursor.moveToNext());
        }

        return barang;
    }

    private MappingHelper() {
    }

    static {
        INSTANCE = new MappingHelper();
    }
}