package id.ac.uasbungaa.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import id.ac.uasbungaa.R;
import id.ac.uasbungaa.data.local.UserHelper;
import id.ac.uasbungaa.data.model.User;
import id.ac.uasbungaa.databinding.ActivityUser200020016Binding;
import id.ac.uasbungaa.helper.LocalDataHelper;
import id.ac.uasbungaa.ui.adapter.UserAdapter;
import id.ac.uasbungaa.ui.adapter.UserAdapter.OnItemClickCallback;

import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class User200020016Activity extends AppCompatActivity {
    private ActivityUser200020016Binding binding;
    private ArrayList allUser;
    private UserAdapter userAdapter;
    private UserHelper userHelper;
    private View emptyView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = ActivityUser200020016Binding.inflate(this.getLayoutInflater());
        this.setContentView(binding.getRoot());
        ActionBar supportActionBar = this.getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle("User Data");
        }

        this.emptyView = findViewById(R.id.tv_empty);
        this.userHelper = new UserHelper(this);

        binding.fab.setOnClickListener((OnClickListener) (new OnClickListener() {
            public final void onClick(View it) {
                Intent intent = new Intent(getApplicationContext(), AddUserActivity.class);
                startActivity(intent);
            }
        }));
    }

    protected void onResume() {
        super.onResume();
        this.allUser = LocalDataHelper.INSTANCE.getAllUser(userHelper);

        int userCount = allUser.size();
        if (userCount == 0) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }

        this.userAdapter = new UserAdapter((OnItemClickCallback) (new OnItemClickCallback() {
            public void onItemClicked(@NotNull User user) {
                Intent intent = new Intent(User200020016Activity.this, Detail200020016UserActivity.class);
                intent.putExtra(Detail200020016UserActivity.EXTRA_USER, user);
                User200020016Activity.this.startActivity(intent);
            }
        }));
        this.userAdapter.setData(allUser);
        binding.rv.setLayoutManager(new LinearLayoutManager(this));
        binding.rv.setAdapter(userAdapter);
    }

    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}