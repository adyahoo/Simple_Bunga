package id.ac.uasbunga.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.ac.uasbunga.data.local.BarangHelper
import id.ac.uasbunga.data.local.UserHelper
import id.ac.uasbunga.data.model.Barang
import id.ac.uasbunga.data.model.User
import id.ac.uasbunga.databinding.ActivityDashboard200020016Binding
import id.ac.uasbunga.helper.LocalDataHelper

class Dashboard200020016Activity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "EXTRA_USER"
    }

    private lateinit var binding: ActivityDashboard200020016Binding
    private lateinit var userHelper: UserHelper
    private lateinit var barangHelper: BarangHelper
    private lateinit var allUser: ArrayList<User>
    private lateinit var allFlower: ArrayList<Barang>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboard200020016Binding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Dashboard"

        userHelper = UserHelper.getInstance(this)
        barangHelper = BarangHelper.getInstance(this)

        val username = intent.getParcelableExtra<User>(EXTRA_USER)
        binding.tvWelcome.text = "Welcome ${username?.username}"

        binding.btnUser.setOnClickListener {
            handleUserClicked()
        }

        binding.btnItem.setOnClickListener {
            handleItemClicked()
        }
    }

    override fun onResume() {
        super.onResume()
        allUser = LocalDataHelper.getAllUser(userHelper)
        allFlower = LocalDataHelper.getAllBarang(barangHelper)
        binding.tvUser.text = allUser.count().toString()
        binding.tvItem.text = allFlower.count().toString()
    }

    private fun handleItemClicked() {
        val intent = Intent(this, Barang200020016Activity::class.java)
        startActivity(intent)
    }

    private fun handleUserClicked() {
        val intent = Intent(this, User200020016Activity::class.java)
        startActivity(intent)
    }
}