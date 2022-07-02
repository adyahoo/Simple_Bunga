package id.ac.uasbungaa.ui.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import id.ac.uasbungaa.data.local.BarangHelper;
import id.ac.uasbungaa.data.local.DatabaseContract;
import id.ac.uasbungaa.data.local.UserHelper;
import id.ac.uasbungaa.data.model.Barang;
import id.ac.uasbungaa.databinding.ActivitySplashScreenBinding;
import id.ac.uasbungaa.helper.DummyData;
import id.ac.uasbungaa.helper.Keys;

import org.jetbrains.annotations.Nullable;

import static id.ac.uasbungaa.helper.DummyData.INSTANCE;

public final class SplashScreenActivity extends AppCompatActivity {
    private UserHelper userHelper;
    private BarangHelper barangHelper;
    private SharedPreferences sharedPref;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySplashScreenBinding binding = ActivitySplashScreenBinding.inflate(this.getLayoutInflater());

        this.setContentView(binding.getRoot());
        ActionBar supportActionBar = this.getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.hide();
        }

        this.userHelper = new UserHelper(this);
        this.barangHelper = new BarangHelper(this);
        this.sharedPref = this.getSharedPreferences(Keys.SHAREDPREF, 0);
        boolean isFirstTime = this.getFirstTime();
        if (isFirstTime) {
            sharedPref.edit().putBoolean(Keys.ISFIRSTTIME, false).apply();
            this.addFirstUser();
            this.addFirstItem();
        }
        this.directToLogin();

    }

    private void directToLogin() {
        (new Handler()).postDelayed((Runnable) (new Runnable() {
            public final void run() {
                Intent intent = new Intent(getApplicationContext(), Login200020016Activity.class);
                startActivity(intent);
                finish();
            }
        }), 1000L);
    }

    private final void addFirstItem() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    barangHelper.open();
                    Barang[] flowers = DummyData.INSTANCE.getFlowers();
                    for (int i = 0; i < flowers.length; i++) {
                        Barang barang = flowers[i];
                        ContentValues values = new ContentValues();
                        values.put(DatabaseContract.BARANG_200020016.NAMA, barang.getNama());
                        values.put(DatabaseContract.BARANG_200020016.KODE, barang.getKode());
                        values.put(DatabaseContract.BARANG_200020016.STOK, barang.getStok());
                        values.put(DatabaseContract.BARANG_200020016.HARGA, barang.getHarga());
                        values.put(DatabaseContract.BARANG_200020016.GAMBAR, barang.getGambar());

                        barangHelper.insert(values);
                    }
                } catch (Exception e) {
                    Log.e("Error Add Item to DB", e.toString());
                }
            }
        };
        thread.start();
    }

    private final void addFirstUser() {
        try {
            userHelper.open();
            ContentValues values = new ContentValues();
            values.put("username", INSTANCE.getAdmin().getUsername());
            values.put("password", INSTANCE.getAdmin().getPassword());
            values.put("thumbnail", INSTANCE.getAdmin().getThumbnail());

            userHelper.insert(values);
        } catch (Exception e) {
            Log.e("Error Add User to DB", e.toString());
        }

    }

    private final boolean getFirstTime() {
        return sharedPref.getBoolean("is_first_time", true);
    }
}