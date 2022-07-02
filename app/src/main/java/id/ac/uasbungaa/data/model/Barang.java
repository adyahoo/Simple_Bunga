package id.ac.uasbungaa.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.NotNull;

public final class Barang implements Parcelable {
    private int id;
    @NotNull
    private String nama;
    @NotNull
    private String kode;
    private int stok;
    private int harga;
    @NotNull
    private String gambar;
    public static final android.os.Parcelable.Creator CREATOR = new Barang.Creator();

    public final int getId() {
        return this.id;
    }

    public final void setId(int var1) {
        this.id = var1;
    }

    @NotNull
    public final String getNama() {
        return this.nama;
    }

    public final void setNama(@NotNull String var1) {
        this.nama = var1;
    }

    @NotNull
    public final String getKode() {
        return this.kode;
    }

    public final void setKode(@NotNull String var1) {
        this.kode = var1;
    }

    public final int getStok() {
        return this.stok;
    }

    public final void setStok(int var1) {
        this.stok = var1;
    }

    public final int getHarga() {
        return this.harga;
    }

    public final void setHarga(int var1) {
        this.harga = var1;
    }

    @NotNull
    public final String getGambar() {
        return this.gambar;
    }

    public final void setGambar(@NotNull String var1) {
        this.gambar = var1;
    }

    public Barang(int id, @NotNull String nama, @NotNull String kode, int stok, int harga, @NotNull String gambar) {
        this.id = id;
        this.nama = nama;
        this.kode = kode;
        this.stok = stok;
        this.harga = harga;
        this.gambar = gambar;
    }

    public Barang() {
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(@NotNull Parcel parcel, int flags) {
        parcel.writeInt(this.id);
        parcel.writeString(this.nama);
        parcel.writeString(this.kode);
        parcel.writeInt(this.stok);
        parcel.writeInt(this.harga);
        parcel.writeString(this.gambar);
    }

    public static class Creator implements android.os.Parcelable.Creator {
        @NotNull
        public final Barang[] newArray(int size) {
            return new Barang[size];
        }

        @NotNull
        public final Barang createFromParcel(@NotNull Parcel in) {
            return new Barang(in.readInt(), in.readString(), in.readString(), in.readInt(), in.readInt(), in.readString());
        }
    }
}