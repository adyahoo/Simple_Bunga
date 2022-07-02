package id.ac.uasbungaa.ui.activity.barang;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;


import id.ac.uasbungaa.R;
import id.ac.uasbungaa.data.local.BarangHelper;
import id.ac.uasbungaa.data.local.DatabaseContract;
import id.ac.uasbungaa.data.model.Barang;
import id.ac.uasbungaa.databinding.ActivityPengadaanBarang200020016Binding;
import id.ac.uasbungaa.helper.LocalDataHelper;

import java.util.ArrayList;

import kotlin.jvm.internal.Intrinsics;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PengadaanBarang200020016Activity extends AppCompatActivity {
    private ActivityPengadaanBarang200020016Binding binding;
    private ArrayList allBarangKode = new ArrayList();
    private BarangHelper barangHelper;
    private Barang selectedBarang;
    private View overlay;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPengadaanBarang200020016Binding.inflate(this.getLayoutInflater());
        this.setContentView(binding.getRoot());

        ActionBar supportActionBar = this.getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setTitle("Pengadaan Barang");
            supportActionBar.setDisplayHomeAsUpEnabled(true);
        }

        overlay = this.findViewById(R.id.progbar_layout);
        barangHelper = new BarangHelper(this);
        allBarangKode = LocalDataHelper.INSTANCE.getAllBarangKode(barangHelper);

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.item_kode_barang, this.allBarangKode);
        binding.dropdownSpinner.setAdapter(adapter);
        binding.dropdownSpinner.setText("Pilih Kode Barang", false);
        binding.dropdownSpinner.setOnItemClickListener((OnItemClickListener) (new OnItemClickListener() {
            public final void onItemClick(AdapterView adapterView, View view, int i, long l) {
                getSelectedBarang(String.valueOf(allBarangKode.get(i)));
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
        String submittedStok = binding.etJumlah.getText().toString();
        int updatedStok = selectedBarang.getStok() + Integer.parseInt(submittedStok);
        updateStok(updatedStok);
        finish();
    }

    private final void updateStok(int updatedStok) {
        barangHelper.open();
        ContentValues values = new ContentValues();
        values.put(DatabaseContract.BARANG_200020016.STOK, updatedStok);
        barangHelper.update(String.valueOf(selectedBarang.getId()), values);
    }

    private final void getSelectedBarang(String kodeBarang) {
        selectedBarang = LocalDataHelper.INSTANCE.getBarang(barangHelper, kodeBarang);
        binding.etNama.setText(selectedBarang.getNama());
        binding.etHarga.setText(String.valueOf(selectedBarang.getHarga()));
    }

    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}