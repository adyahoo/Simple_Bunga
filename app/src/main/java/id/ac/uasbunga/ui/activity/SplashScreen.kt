package id.ac.uasbunga.ui.activity

import android.content.ContentValues
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.lifecycle.lifecycleScope
import id.ac.uasbunga.data.local.BarangHelper
import id.ac.uasbunga.data.local.DatabaseContract.BARANG_200020016.Companion.GAMBAR
import id.ac.uasbunga.data.local.DatabaseContract.BARANG_200020016.Companion.HARGA
import id.ac.uasbunga.data.local.DatabaseContract.BARANG_200020016.Companion.KODE
import id.ac.uasbunga.data.local.DatabaseContract.BARANG_200020016.Companion.NAMA
import id.ac.uasbunga.data.local.DatabaseContract.BARANG_200020016.Companion.STOK
import id.ac.uasbunga.data.local.DatabaseContract.USER_200020016.Companion.PASSWORD
import id.ac.uasbunga.data.local.DatabaseContract.USER_200020016.Companion.THUMBNAIL
import id.ac.uasbunga.data.local.DatabaseContract.USER_200020016.Companion.USERNAME
import id.ac.uasbunga.data.local.UserHelper
import id.ac.uasbunga.databinding.ActivitySplashScreenBinding
import id.ac.uasbunga.helper.DummyData
import id.ac.uasbunga.helper.Keys.Companion.ISFIRSTTIME
import id.ac.uasbunga.helper.Keys.Companion.SHAREDPREF
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var userHelper: UserHelper
    private lateinit var barangHelper: BarangHelper
    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        userHelper = UserHelper.getInstance(this)
        barangHelper = BarangHelper.getInstance(this)
        sharedPref = getSharedPreferences(SHAREDPREF, MODE_PRIVATE)

        val isFirstTime = getFirstTime()
        if (!isFirstTime) {
            Log.d("Gak first time", isFirstTime.toString())
            directToLogin()
        } else {
            Log.d("First time", isFirstTime.toString())
            sharedPref.edit().putBoolean(ISFIRSTTIME, false).apply()
            addFirstUser()
            addFirstItem()
            directToLogin()
        }
    }

    private fun directToLogin() {
        Handler().postDelayed({
            val intent = Intent(this, Login200020016Activity::class.java)
            startActivity(intent)
            finish()
        }, 1000)
    }

    private fun addFirstItem() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                barangHelper.open()
                DummyData.flowers.map { item ->
                    val values = ContentValues()
                    values.put(NAMA, item.nama)
                    values.put(KODE, item.kode)
                    values.put(STOK, item.stok)
                    values.put(HARGA, item.harga)
                    values.put(GAMBAR, item.gambar)
                    barangHelper.insert(values)
                }
            } catch (e: Exception) {
                Log.e("Error Add Barang to DB", e.toString())
            }
        }
    }

    private fun addFirstUser() {
        try {
            userHelper.open()
            val values = ContentValues()
            values.put(USERNAME, DummyData.admin.username)
            values.put(PASSWORD, DummyData.admin.password)
            values.put(THUMBNAIL, DummyData.admin.thumbnail)
            userHelper.insert(values)
        } catch (e: Exception) {
            Log.e("Error Add User to DB", e.toString())
        }
    }

    private fun getFirstTime(): Boolean {
        return sharedPref.getBoolean(ISFIRSTTIME, true)
    }
}