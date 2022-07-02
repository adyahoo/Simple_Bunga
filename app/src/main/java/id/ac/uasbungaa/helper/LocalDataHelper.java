package id.ac.uasbungaa.helper;

import android.database.Cursor;

import id.ac.uasbungaa.data.local.BarangHelper;
import id.ac.uasbungaa.data.local.UserHelper;
import id.ac.uasbungaa.data.model.Barang;

import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;

public final class LocalDataHelper {
    @NotNull
    public static final LocalDataHelper INSTANCE;

    @NotNull
    public final ArrayList getAllUser(@NotNull UserHelper userHelper) {
        userHelper.open();
        Cursor users = userHelper.getAllUser();
        return MappingHelper.INSTANCE.mapCursorAllUser(users);
    }

    @NotNull
    public final ArrayList getAllBarang(@NotNull BarangHelper barangHelper) {
        barangHelper.open();
        Cursor barangs = barangHelper.getAllBarang();
        return MappingHelper.INSTANCE.mapCursorAllBarang(barangs);
    }

    @NotNull
    public final ArrayList getAllBarangKode(@NotNull BarangHelper barangHelper) {
        barangHelper.open();
        Cursor kodes = barangHelper.getAllBarangKode();
        return MappingHelper.INSTANCE.mapCursorAllBarangKode(kodes);
    }

    @NotNull
    public final Barang getBarang(@NotNull BarangHelper barangHelper, @NotNull String kodeBarang) {
        barangHelper.open();
        Cursor barang = barangHelper.getBarangByKode(kodeBarang);
        return MappingHelper.INSTANCE.mapCursorBarang(barang);
    }

    private LocalDataHelper() {
    }

    static {
        INSTANCE = new LocalDataHelper();
    }
}