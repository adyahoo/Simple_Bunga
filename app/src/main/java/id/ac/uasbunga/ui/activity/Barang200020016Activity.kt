package id.ac.uasbunga.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import id.ac.uasbunga.R
import id.ac.uasbunga.data.local.BarangHelper
import id.ac.uasbunga.data.model.Barang
import id.ac.uasbunga.databinding.ActivityBarang200020016Binding
import id.ac.uasbunga.helper.LocalDataHelper
import id.ac.uasbunga.ui.adapter.BarangAdapter

class Barang200020016Activity : AppCompatActivity() {
    private lateinit var binding: ActivityBarang200020016Binding
    private lateinit var barangHelper: BarangHelper
    private lateinit var barangAdapter: BarangAdapter
    private var allBarangs: ArrayList<Barang> = ArrayList()
    private lateinit var emptyView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBarang200020016Binding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Flower Data"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        emptyView = findViewById(R.id.tv_empty)
        barangHelper = BarangHelper.getInstance(this)

        binding.fab.setOnClickListener {
            val intent = Intent(this, PengadaanBarang200020016Activity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        allBarangs = LocalDataHelper.getAllBarang(barangHelper)

        emptyView.visibility = if (allBarangs.count() == 0) View.VISIBLE else View.GONE

        barangAdapter = BarangAdapter(object : BarangAdapter.OnItemClickCallback {
            override fun onItemClicked(barang: Barang) {
                val intent =
                    Intent(this@Barang200020016Activity, Detail200020016BarangActivity::class.java)
                intent.putExtra(Detail200020016BarangActivity.EXTRA_BARANG, barang)
                startActivity(intent)
            }
        })
        barangAdapter.setData(allBarangs)
        binding.rv.adapter = barangAdapter
        binding.rv.layoutManager = GridLayoutManager(this, 2)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}