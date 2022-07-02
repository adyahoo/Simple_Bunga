package id.ac.uasbungaa.ui.activity.barang;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridLayout;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import id.ac.uasbungaa.R;
import id.ac.uasbungaa.data.local.BarangHelper;
import id.ac.uasbungaa.data.model.Barang;
import id.ac.uasbungaa.databinding.ActivityBarang200020016Binding;
import id.ac.uasbungaa.helper.LocalDataHelper;
import id.ac.uasbungaa.ui.adapter.BarangAdapter;
import id.ac.uasbungaa.ui.adapter.BarangAdapter.OnItemClickCallback;

import java.util.ArrayList;
import java.util.Collection;

import kotlin.jvm.internal.Intrinsics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Barang200020016Activity extends AppCompatActivity {
    private ActivityBarang200020016Binding binding;
    private BarangHelper barangHelper;
    private BarangAdapter barangAdapter;
    private ArrayList allBarangs = new ArrayList();
    private View emptyView;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBarang200020016Binding.inflate(this.getLayoutInflater());
        this.setContentView(binding.getRoot());
        ActionBar supportActionBar = this.getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle("Flower Data");
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        emptyView = findViewById(R.id.tv_empty);
        barangHelper = new BarangHelper(this);

        binding.fab.setOnClickListener((OnClickListener) (new OnClickListener() {
            public final void onClick(View it) {
                Intent intent = new Intent(getApplicationContext(), PengadaanBarang200020016Activity.class);
                startActivity(intent);
            }
        }));
    }

    protected void onResume() {
        super.onResume();
        allBarangs = LocalDataHelper.INSTANCE.getAllBarang(barangHelper);

        if (allBarangs.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            emptyView.setVisibility(View.GONE);
        }

        this.barangAdapter = new BarangAdapter((OnItemClickCallback) (new OnItemClickCallback() {
            public void onItemClicked(@NotNull Barang barang) {
                Intent intent = new Intent(getApplicationContext(), Detail200020016BarangActivity.class);
                intent.putExtra(Detail200020016BarangActivity.EXTRA_BARANG, barang);
                startActivity(intent);
            }
        }));
        barangAdapter.setData(allBarangs);
        binding.rv.setAdapter(barangAdapter);
        binding.rv.setLayoutManager(new GridLayoutManager(this, 2));
    }

    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}