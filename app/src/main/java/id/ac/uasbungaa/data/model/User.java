package id.ac.uasbungaa.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;

public final class User implements Parcelable {
    private int id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String thumbnail;
    public static final android.os.Parcelable.Creator CREATOR = new User.Creator();

    public final int getId() {
        return this.id;
    }

    public final void setId(int var1) {
        this.id = var1;
    }

    @NotNull
    public final String getUsername() {
        return this.username;
    }

    public final void setUsername(@NotNull String var1) {
        this.username = var1;
    }

    @NotNull
    public final String getPassword() {
        return this.password;
    }

    public final void setPassword(@NotNull String var1) {
        this.password = var1;
    }

    @NotNull
    public final String getThumbnail() {
        return this.thumbnail;
    }

    public final void setThumbnail(@NotNull String var1) {
        this.thumbnail = var1;
    }

    public User(int id, @NotNull String username, @NotNull String password, @NonNull String thumbnail) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.thumbnail = thumbnail;
    }

    public User() {
    }

    public User(@NotNull String username, @NotNull String password, @NonNull String thumbnail) {

    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(@NotNull Parcel parcel, int flags) {
        parcel.writeInt(this.id);
        parcel.writeString(this.username);
        parcel.writeString(this.password);
        parcel.writeString(this.thumbnail);
    }

    public static class Creator implements android.os.Parcelable.Creator {
        @NotNull
        public final User[] newArray(int size) {
            return new User[size];
        }

        @NotNull
        public final User createFromParcel(@NotNull Parcel in) {
            return new User(in.readInt(), in.readString(), in.readString(), in.readString());
        }
    }
}