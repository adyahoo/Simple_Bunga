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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import id.ac.uasbungaa.R;
import id.ac.uasbungaa.data.local.DatabaseContract;
import id.ac.uasbungaa.data.local.UserHelper;
import id.ac.uasbungaa.data.model.User;
import id.ac.uasbungaa.databinding.ActivityDetail200020016UserBinding;
import id.ac.uasbungaa.helper.Keys;
import id.ac.uasbungaa.helper.ToastHelper;

import java.io.ByteArrayOutputStream;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Detail200020016UserActivity extends AppCompatActivity {
    private ActivityDetail200020016UserBinding binding;
    private User user;
    private String path = "";
    private UserHelper userHelper;
    private View overlay;
    @NotNull
    public static final String EXTRA_USER = "EXTRA_USER";

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetail200020016UserBinding.inflate(this.getLayoutInflater());
        this.setContentView(binding.getRoot());
        this.overlay = findViewById(R.id.progbar_layout);

        this.user = this.getIntent().getParcelableExtra(EXTRA_USER);
        this.userHelper = new UserHelper(this);

        ActionBar supportActionBar = this.getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle(user.getUsername());
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        binding.etPassword.setText(user.getPassword());
        if (user.getThumbnail() != "") {
            binding.civThumbnail.setImageURI(Uri.parse(user.getThumbnail()));
        } else {
            binding.civThumbnail.setImageResource(R.drawable.user);
        }

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

    private final void handleSubmit() {
        String password = binding.etPassword.getText().toString();
        updateRecord(password);
        finish();
    }

    private final void updateRecord(String password) {
        userHelper.open();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.USER_200020016.PASSWORD, password);
        if (!path.equals("")) {
            values.put(DatabaseContract.USER_200020016.THUMBNAIL, path);
        }
        userHelper.update(String.valueOf(user.getId()), values);
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
                    user.getUsername() + "_Image",
                    null
            );
            binding.civThumbnail.setImageURI(Uri.parse(path));
        }
    }

    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_delete:
                this.deleteUser();
                break;
            case android.R.id.home:
                this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private final void deleteUser() {
        userHelper.open();
        userHelper.delete(String.valueOf(user.getId()));
        userHelper.close();
        finish();
    }

    public boolean onCreateOptionsMenu(@Nullable Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_user, menu);
        return super.onCreateOptionsMenu(menu);
    }
}