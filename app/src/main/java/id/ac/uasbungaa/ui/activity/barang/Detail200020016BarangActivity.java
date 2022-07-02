package id.ac.uasbungaa.ui.activity.barang;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import id.ac.uasbungaa.R;
import id.ac.uasbungaa.data.local.BarangHelper;
import id.ac.uasbungaa.data.local.DatabaseContract;
import id.ac.uasbungaa.data.model.Barang;
import id.ac.uasbungaa.databinding.ActivityDetail200020016BarangBinding;
import id.ac.uasbungaa.helper.Keys;
import id.ac.uasbungaa.helper.ToastHelper;

import java.io.ByteArrayOutputStream;

import kotlin.jvm.internal.Intrinsics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Detail200020016BarangActivity extends AppCompatActivity {
    private ActivityDetail200020016BarangBinding binding;
    private Barang barang;
    private BarangHelper barangHelper;
    private String path = "";
    private View overlay;
    @NotNull
    public static final String EXTRA_BARANG = "EXTRA_BARANG";

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetail200020016BarangBinding.inflate(this.getLayoutInflater());
        this.setContentView(binding.getRoot());

        barang = this.getIntent().getParcelableExtra(EXTRA_BARANG);
        ActionBar supportActionBar = this.getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(barang.getNama());
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        overlay = this.findViewById(R.id.progbar_layout);
        barangHelper = new BarangHelper(this);

        Comparable imgId;
        if (barang.getGambar().contains("flower")) {
            imgId = this.getResources().getIdentifier(
                    barang.getGambar(),
                    "drawable",
                    this.getPackageName()
            );
        } else {
            imgId = Uri.parse(barang.getGambar());
        }
        Glide.with(this)
                .load(imgId)
                .into(binding.civThumbnail);
        binding.etKode.setText(barang.getKode());
        binding.etNama.setText(barang.getNama());
        binding.etHarga.setText(String.valueOf(barang.getHarga()));

        binding.civThumbnail.setOnClickListener((OnClickListener) (new OnClickListener() {
            public final void onClick(View it) {
                checkStoragePermission();
            }
        }));

        binding.btnSubmit.setOnClickListener((OnClickListener) (new OnClickListener() {
            public final void onClick(View it) {
                overlay.setVisibility(View.VISIBLE);
                handleSubmit();
            }
        }));
    }

    private final void checkStoragePermission() {
        if (this.checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, Keys.WRITE_EXTERNAL_CODE);
        } else {
            this.checkCameraPermission();
        }
    }

    private final void checkCameraPermission() {
        if (this.checkSelfPermission("android.permission.CAMERA") != PackageManager.PERMISSION_GRANTED) {
            this.requestPermissions(new String[]{"android.permission.CAMERA"}, Keys.CAMERA_PERMISSION_CODE);
        } else {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            this.startActivityForResult(intent, Keys.CAMERA_REQUEST);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Keys.CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            photo.compress(
                    CompressFormat.JPEG,
                    100,
                    new ByteArrayOutputStream()
            );
            path = MediaStore.Images.Media.insertImage(
                    this.getContentResolver(),
                    photo,
                    barang.getNama() + "_Image",
                    null
            );
            binding.civThumbnail.setImageURI(Uri.parse(path));
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions, @NotNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Keys.CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ToastHelper.INSTANCE.showToast("Camera Permission Granted", this);
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                this.startActivityForResult(intent, Keys.CAMERA_REQUEST);
            } else {
                ToastHelper.INSTANCE.showToast("Camera Permission Denied", this);
            }
        }

        if (requestCode == Keys.WRITE_EXTERNAL_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ToastHelper.INSTANCE.showToast("Storage Permission Granted", this);
                this.checkCameraPermission();
            } else {
                ToastHelper.INSTANCE.showToast("Storage Permission Denied", this);
            }
        }
    }

    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        switch (item.getItemId()) {
            case R.id.btn_delete:
                this.handleDeleteBarang();
                break;
            case android.R.id.home:
                this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean onCreateOptionsMenu(@Nullable Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_user, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private final void handleSubmit() {
        String kode = binding.etKode.getText().toString();
        String nama = binding.etNama.getText().toString();
        String harga = binding.etHarga.getText().toString();
        updateRecord(kode, nama, Integer.parseInt(harga));
        finish();
    }

    private final void updateRecord(String kode, String nama, int harga) {
        barangHelper.open();
        ContentValues values = new ContentValues();
        if (path != "") {
            values.put(DatabaseContract.BARANG_200020016.GAMBAR, path);
        }
        values.put(DatabaseContract.BARANG_200020016.KODE, kode);
        values.put(DatabaseContract.BARANG_200020016.NAMA, nama);
        values.put(DatabaseContract.BARANG_200020016.HARGA, harga);
        barangHelper.update(String.valueOf(barang.getId()), values);
        barangHelper.close();
    }

    private final void handleDeleteBarang() {
        barangHelper.open();
        barangHelper.delete(String.valueOf(barang.getId()));
        barangHelper.close();
        finish();
    }
}