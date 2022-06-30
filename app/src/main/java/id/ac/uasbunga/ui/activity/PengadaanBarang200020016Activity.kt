package id.ac.uasbunga.ui.activity

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import id.ac.uasbunga.R
import id.ac.uasbunga.data.local.BarangHelper
import id.ac.uasbunga.data.local.DatabaseContract.BARANG_200020016.Companion.STOK
import id.ac.uasbunga.data.model.Barang
import id.ac.uasbunga.databinding.ActivityPengadaanBarang200020016Binding
import id.ac.uasbunga.helper.LocalDataHelper
import id.ac.uasbunga.helper.MappingHelper

class PengadaanBarang200020016Activity : AppCompatActivity() {
    private lateinit var binding: ActivityPengadaanBarang200020016Binding
    private var allBarangKode = ArrayList<String>()
    private lateinit var barangHelper: BarangHelper
    private lateinit var selectedBarang: Barang
    private lateinit var overlay: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPengadaanBarang200020016Binding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Pengadaan Barang"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        overlay = findViewById(R.id.progbar_layout)
        barangHelper = BarangHelper.getInstance(this)
        allBarangKode = LocalDataHelper.getAllBarangKode(barangHelper)

        val adapter = ArrayAdapter(this, R.layout.item_kode_barang, allBarangKode)
        binding.dropdownSpinner.setAdapter(adapter)
        binding.dropdownSpinner.setText("Pilih Kode Barang", false)
        binding.dropdownSpinner.setOnItemClickListener { adapterView, view, i, l ->
            getSelectedBarang(allBarangKode[i])
        }

        binding.btnSubmit.setOnClickListener {
            overlay.visibility = View.VISIBLE
            handleSubmit()
        }
    }

    private fun handleSubmit() {
        val submittedStok = binding.etJumlah.text.toString()
        val updatedStok = selectedBarang.stok + Integer.parseInt(submittedStok)
        updateStok(updatedStok)
        finish()
    }

    private fun updateStok(updatedStok: Int) {
        barangHelper.open()
        val values = ContentValues()
        values.put(STOK, updatedStok)
        barangHelper.update(selectedBarang.id.toString(), values)
    }

    private fun getSelectedBarang(kodeBarang: String) {
        selectedBarang = LocalDataHelper.getBarang(barangHelper, kodeBarang)
        binding.etNama.setText(selectedBarang.nama)
        binding.etHarga.setText(selectedBarang.harga.toString())
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}