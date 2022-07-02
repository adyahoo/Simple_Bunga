package id.ac.uasbungaa.helper;

import id.ac.uasbungaa.data.model.Barang;
import id.ac.uasbungaa.data.model.User;
import kotlin.jvm.internal.DefaultConstructorMarker;

import org.jetbrains.annotations.NotNull;

public final class DummyData {
    @NotNull
    private static final User admin;
    @NotNull
    private static final Barang[] flowers;
    @NotNull
    public static final DummyData INSTANCE;

    @NotNull
    public final User getAdmin() {
        return admin;
    }

    @NotNull
    public final Barang[] getFlowers() {
        return flowers;
    }

    private DummyData() {
    }

    static {
        INSTANCE = new DummyData();
        admin = new User(0, "admin", "admin", "");
        Barang[] barangs = new Barang[4];
        barangs[0] = new Barang(0, "Blue Roses Silver Leaves", "BRG_01", 10, 650000, "flower1");
        barangs[1] = new Barang(0, "10 Tulip With Baby Breath", "BRG_02", 20, 600000, "flower2");
        barangs[2] = new Barang(0, "50 Roses With 5 Lily", "BRG_03", 15, 1600000, "flower3");
        barangs[3] = new Barang(0, "99 Roses Bouquet", "BRG_04", 18, 2200000, "flower4");
        flowers = barangs;
    }
}