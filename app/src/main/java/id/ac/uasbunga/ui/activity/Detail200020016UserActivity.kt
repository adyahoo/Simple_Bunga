package id.ac.uasbunga.ui.activity

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
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
import androidx.lifecycle.lifecycleScope
import id.ac.uasbunga.R
import id.ac.uasbunga.data.local.DatabaseContract.USER_200020016.Companion.PASSWORD
import id.ac.uasbunga.data.local.DatabaseContract.USER_200020016.Companion.THUMBNAIL
import id.ac.uasbunga.data.local.UserHelper
import id.ac.uasbunga.data.model.User
import id.ac.uasbunga.databinding.ActivityDetail200020016UserBinding
import id.ac.uasbunga.helper.Keys.Companion.CAMERA_PERMISSION_CODE
import id.ac.uasbunga.helper.Keys.Companion.CAMERA_REQUEST
import id.ac.uasbunga.helper.Keys.Companion.WRITE_EXTERNAL_CODE
import id.ac.uasbunga.helper.ToastHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File

class Detail200020016UserActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "EXTRA_USER"
    }

    private lateinit var binding: ActivityDetail200020016UserBinding
    private lateinit var user: User
    private var path: String = ""
    private lateinit var userHelper: UserHelper
    private lateinit var overlay: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetail200020016UserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overlay = findViewById(R.id.progbar_layout)

        user = intent.getParcelableExtra(EXTRA_USER)!!
        userHelper = UserHelper.getInstance(this)

        supportActionBar?.title = user.username
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.etPassword.setText(user.password)
        if (user.thumbnail != "") {
            binding.civThumbnail.setImageURI(Uri.parse(user.thumbnail))
        } else {
            binding.civThumbnail.setImageResource(R.drawable.user)
        }

        binding.civThumbnail.setOnClickListener {
            checkStoragePermission()
        }

        binding.btnSubmit.setOnClickListener {
            overlay.visibility = View.VISIBLE
            handleSubmit()
        }
    }

    private fun handleSubmit() {
        val password = binding.etPassword.text.toString()
        updateRecord(password)
        finish()
    }

    private fun updateRecord(password: String) {
        userHelper.open()
        val values = ContentValues()
        values.put(PASSWORD, password)
        if (path != "") {
            values.put(THUMBNAIL, path)
        }
        userHelper.update(user.id.toString(), values)
        userHelper.close()
    }

    private fun checkStoragePermission() {
        if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                WRITE_EXTERNAL_CODE
            )
        } else {
            checkCameraPermission()
        }
    }

    private fun checkCameraPermission() {
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_CODE)
        } else {
            val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, CAMERA_REQUEST)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ToastHelper.showToast("Camera Permission Granted", this)
                val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(intent, CAMERA_REQUEST)
            } else {
                ToastHelper.showToast("Camera Permission Denied", this)
            }
        }
        if (requestCode == WRITE_EXTERNAL_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                ToastHelper.showToast("Storage Permission Granted", this)
                checkCameraPermission()
            } else {
                ToastHelper.showToast("Storage Permission Denied", this)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            val photo = data?.extras?.get("data") as Bitmap
            photo.compress(Bitmap.CompressFormat.JPEG, 100, ByteArrayOutputStream())
            path =
                MediaStore.Images.Media.insertImage(
                    this.contentResolver,
                    photo,
                    "${user.username}_Image",
                    null
                )
            binding.civThumbnail.setImageURI(Uri.parse(path))
        }
    }

    private fun storeToGallery(photoPath: String, context: Context) {
        lifecycleScope.launch(Dispatchers.IO) {
            val mediaScanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
            val imageFile = File(photoPath)
            val imageUri = Uri.fromFile(imageFile)
            mediaScanIntent.data = imageUri
            context.sendBroadcast(mediaScanIntent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.btn_delete -> deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
        userHelper.open()
        userHelper.delete(user.id.toString())
        userHelper.close()
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_user, menu)
        return super.onCreateOptionsMenu(menu)
    }
}