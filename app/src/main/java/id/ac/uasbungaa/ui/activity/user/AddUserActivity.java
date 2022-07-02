package id.ac.uasbungaa.ui.activity.user;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import id.ac.uasbungaa.R;
import id.ac.uasbungaa.data.local.DatabaseContract;
import id.ac.uasbungaa.data.local.UserHelper;
import id.ac.uasbungaa.data.model.User;
import id.ac.uasbungaa.databinding.ActivityAddUserBinding;
import id.ac.uasbungaa.helper.Keys;
import id.ac.uasbungaa.helper.ToastHelper;

import java.io.ByteArrayOutputStream;

import kotlin.jvm.internal.Intrinsics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class AddUserActivity extends AppCompatActivity {
    private ActivityAddUserBinding binding;
    private UserHelper userHelper;
    private String path = "";
    private View overlay;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddUserBinding.inflate(this.getLayoutInflater());
        this.setContentView(binding.getRoot());

        ActionBar supportActionBar = this.getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle("Add User");
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        overlay = findViewById(R.id.progbar_layout);
        userHelper = new UserHelper(this);

        binding.civThumbnail.setImageResource(R.drawable.user);
        binding.btnSubmit.setOnClickListener((OnClickListener) (new OnClickListener() {
            public final void onClick(View it) {
                String username = binding.etUsername.getText().toString();
                String password = binding.etPassword.getText().toString();
                handleSubmit(username, password);
            }
        }));

        binding.civThumbnail.setOnClickListener((OnClickListener) (new OnClickListener() {
            public final void onClick(View it) {
                checkStoragePermission();
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
                    "new_image",
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

    private final void handleSubmit(String username, String password) {
        overlay.setVisibility(View.VISIBLE);
        addRecord(username, password);
        finish();
    }

    private final void addRecord(String username, String password) {
        userHelper.open();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.USER_200020016.USERNAME, username);
        values.put(DatabaseContract.USER_200020016.PASSWORD, password);
        values.put(DatabaseContract.USER_200020016.THUMBNAIL, path);
        userHelper.insert(values);
        userHelper.close();
    }

    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}