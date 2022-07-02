package id.ac.uasbungaa.data.local;

import android.provider.BaseColumns;

import kotlin.jvm.internal.DefaultConstructorMarker;

import org.jetbrains.annotations.NotNull;

public final class DatabaseContract {
    public static final class USER_200020016 implements BaseColumns {
        @NotNull
        public static final String TABLE_NAME = "USER_200020016";
        @NotNull
        public static final String _ID = "_id";
        @NotNull
        public static final String USERNAME = "username";
        @NotNull
        public static final String PASSWORD = "password";
        @NotNull
        public static final String THUMBNAIL = "thumbnail";
        @NotNull
        public static final DatabaseContract.USER_200020016.Companion Companion = new DatabaseContract.USER_200020016.Companion((DefaultConstructorMarker) null);

        public static final class Companion {
            private Companion(DefaultConstructorMarker defaultConstructorMarker) {
            }
        }
    }

    public static final class BARANG_200020016 implements BaseColumns {
        @NotNull
        public static final String TABLE_NAME = "BARANG_200020016";
        @NotNull
        public static final String _ID = "_id";
        @NotNull
        public static final String NAMA = "nama";
        @NotNull
        public static final String KODE = "kode";
        @NotNull
        public static final String STOK = "stok";
        @NotNull
        public static final String HARGA = "harga";
        @NotNull
        public static final String GAMBAR = "gambar";
        @NotNull
        public static final DatabaseContract.BARANG_200020016.Companion Companion = new DatabaseContract.BARANG_200020016.Companion((DefaultConstructorMarker) null);

        public static final class Companion {
            private Companion(DefaultConstructorMarker defaultConstructorMarker) {
            }
        }
    }
}