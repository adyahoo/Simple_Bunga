package id.ac.uasbungaa.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import id.ac.uasbungaa.data.local.BarangHelper;
import id.ac.uasbungaa.data.local.UserHelper;
import id.ac.uasbungaa.data.model.User;
import id.ac.uasbungaa.databinding.ActivityDashboard200020016Binding;
import id.ac.uasbungaa.helper.LocalDataHelper;
import id.ac.uasbungaa.ui.activity.barang.Barang200020016Activity;
import id.ac.uasbungaa.ui.activity.user.User200020016Activity;

import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Dashboard200020016Activity extends AppCompatActivity {
    private ActivityDashboard200020016Binding binding;
    private UserHelper userHelper;
    private BarangHelper barangHelper;
    private ArrayList allUser;
    private ArrayList allFlower;
    @NotNull
    public static final String EXTRA_USER = "EXTRA_USER";

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityDashboard200020016Binding.inflate(this.getLayoutInflater());

        this.setContentView(binding.getRoot());
        ActionBar supportActionBar = this.getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle("Dashboard");
        }

        this.userHelper = new UserHelper(this);
        this.barangHelper = new BarangHelper(this);
        User username = this.getIntent().getParcelableExtra(EXTRA_USER);

        binding.tvWelcome.setText((CharSequence) ("Welcome " + (username != null ? username.getUsername() : null)));

        binding.btnUser.setOnClickListener((OnClickListener) (new OnClickListener() {
            public final void onClick(View it) {
                handleUserClicked();
            }
        }));

        binding.btnItem.setOnClickListener((OnClickListener) (new OnClickListener() {
            public final void onClick(View it) {
                handleItemClicked();
            }
        }));
    }

    protected void onResume() {
        super.onResume();

        this.allUser = LocalDataHelper.INSTANCE.getAllUser(userHelper);
        this.allFlower = LocalDataHelper.INSTANCE.getAllBarang(barangHelper);
        binding.tvUser.setText(String.valueOf(allUser.size()));
        binding.tvItem.setText(String.valueOf(allFlower.size()));
    }

    private final void handleItemClicked() {
        Intent intent = new Intent(this, Barang200020016Activity.class);
        this.startActivity(intent);
    }

    private final void handleUserClicked() {
        Intent intent = new Intent(this, User200020016Activity.class);
        this.startActivity(intent);
    }
}