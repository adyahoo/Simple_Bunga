package id.ac.uasbungaa.ui.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.appcompat.app.AppCompatActivity;
import id.ac.uasbungaa.data.local.UserHelper;
import id.ac.uasbungaa.data.model.User;
import id.ac.uasbungaa.databinding.ActivityLogin200020016Binding;
import id.ac.uasbungaa.helper.MappingHelper;
import id.ac.uasbungaa.helper.ToastHelper;

import org.jetbrains.annotations.Nullable;

public final class Login200020016Activity extends AppCompatActivity {
    private ActivityLogin200020016Binding binding;
    private UserHelper userHelper;
    private User userData = new User();

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityLogin200020016Binding.inflate(this.getLayoutInflater());

        this.setContentView(binding.getRoot());
        this.userHelper = new UserHelper(this);

        binding.btnSubmit.setOnClickListener((OnClickListener) (new OnClickListener() {
            public final void onClick(View it) {
                String username = binding.etUsername.getText().toString();
                String password = binding.etPassword.getText().toString();
                handleLogin(username, password);
            }
        }));
    }

    private final void handleLogin(String username, String password) {
        this.getUser(username, password);
        if (this.userData.getId() != 0 && this.userData.getUsername().equals(username)) {
            Intent intent = new Intent(getApplicationContext(), Dashboard200020016Activity.class);
            intent.putExtra(Dashboard200020016Activity.EXTRA_USER, this.userData);
            this.startActivity(intent);
            this.finish();
        } else {
            ToastHelper.INSTANCE.showToast("User Tidak Ditemukan", this);
        }

    }

    private final void getUser(String username, String password) {
        Cursor userCursor = userHelper.login(username, password);
        this.userData = MappingHelper.INSTANCE.mapCursor(userCursor);
    }
}