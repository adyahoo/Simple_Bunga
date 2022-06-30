package id.ac.uasbunga.ui.activity

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import id.ac.uasbunga.R
import id.ac.uasbunga.data.local.BarangHelper
import id.ac.uasbunga.data.local.DatabaseContract.BARANG_200020016.Companion.GAMBAR
import id.ac.uasbunga.data.local.DatabaseContract.BARANG_200020016.Companion.HARGA
import id.ac.uasbunga.data.local.DatabaseContract.BARANG_200020016.Companion.KODE
import id.ac.uasbunga.data.local.DatabaseContract.BARANG_200020016.Companion.NAMA
import id.ac.uasbunga.data.model.Barang
import id.ac.uasbunga.databinding.ActivityDetail200020016BarangBinding
import id.ac.uasbunga.helper.Keys
import id.ac.uasbunga.helper.ToastHelper
import java.io.ByteArrayOutputStream

class Detail200020016BarangActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_BARANG = "EXTRA_BARANG"
    }

    private lateinit var binding: ActivityDetail200020016BarangBinding
    private lateinit var barang: Barang
    private lateinit var barangHelper: BarangHelper
    private var path = ""
    private lateinit var overlay: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetail200020016BarangBinding.inflate(layoutInflater)
        setContentView(binding.root)

        barang = intent.getParcelableExtra(EXTRA_BARANG)!!
        supportActionBar?.title = barang.nama
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        overlay = findViewById(R.id.progbar_layout)
        barangHelper = BarangHelper.getInstance(this)

        val gambar = if (barang.gambar.contains("flower"))
            resources.getIdentifier(
                barang.gambar,
                "drawable",
                packageName
            )
        else
            Uri.parse(barang.gambar)
        Glide.with(this).load(gambar).into(binding.civThumbnail)
        binding.etKode.setText(barang.kode)
        binding.etNama.setText(barang.nama)
        binding.etHarga.setText(barang.harga.toString())

        binding.civThumbnail.setOnClickListener {
            checkStoragePermission()
        }

        binding.btnSubmit.setOnClickListener {
            overlay.visibility = View.VISIBLE
            handleSubmit()
        }
    }

    private fun checkStoragePermission() {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                Keys.WRITE_EXTERNAL_CODE
            )
        } else {
            checkCameraPermission()
        }
    }

    private fun checkCameraPermission() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), Keys.CAMERA_PERMISSION_CODE)
        } else {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, Keys.CAMERA_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Keys.CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            val photo = data?.extras?.get("data") as Bitmap
            photo.compress(Bitmap.CompressFormat.JPEG, 100, ByteArrayOutputStream())
            path =
                MediaStore.Images.Media.insertImage(
                    this.contentResolver,
                    photo,
                    "${barang.nama}_Image",
                    null
                )
            binding.civThumbnail.setImageURI(Uri.parse(path))
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Keys.CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ToastHelper.showToast("Camera Permission Granted", this)
                val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, Keys.CAMERA_REQUEST)
            } else {
                ToastHelper.showToast("Camera Permission Denied", this)
            }
        }
        if (requestCode == Keys.WRITE_EXTERNAL_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ToastHelper.showToast("Storage Permission Granted", this)
                checkCameraPermission()
            } else {
                ToastHelper.showToast("Storage Permission Denied", this)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.btn_delete -> handleDeleteBarang()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_user, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun handleSubmit() {
        val kode = binding.etKode.text.toString()
        val nama = binding.etNama.text.toString()
        val harga = binding.etHarga.text.toString()
        updateRecord(kode, nama, Integer.parseInt(harga))
        finish()
    }

    private fun updateRecord(kode: String, nama: String, harga: Int) {
        barangHelper.open()
        val values = ContentValues()
        if (path != "") values.put(GAMBAR, path)
        values.put(KODE, kode)
        values.put(NAMA, nama)
        values.put(HARGA, harga)
        barangHelper.update(barang.id.toString(), values)
        barangHelper.close()
    }

    private fun handleDeleteBarang() {
        barangHelper.open()
        barangHelper.delete(barang.id.toString())
        barangHelper.close()
        finish()
    }
}