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
import android.view.MenuItem
import android.view.View
import id.ac.uasbunga.R
import id.ac.uasbunga.data.local.DatabaseContract.USER_200020016.Companion.PASSWORD
import id.ac.uasbunga.data.local.DatabaseContract.USER_200020016.Companion.THUMBNAIL
import id.ac.uasbunga.data.local.DatabaseContract.USER_200020016.Companion.USERNAME
import id.ac.uasbunga.data.local.UserHelper
import id.ac.uasbunga.data.model.User
import id.ac.uasbunga.databinding.ActivityAddUserBinding
import id.ac.uasbunga.helper.Keys
import id.ac.uasbunga.helper.ToastHelper
import java.io.ByteArrayOutputStream

class AddUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddUserBinding
    private lateinit var userHelper: UserHelper
    private var path: String = ""
    private lateinit var overlay: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Add User"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        overlay = findViewById(R.id.progbar_layout)
        userHelper = UserHelper.getInstance(this)

        binding.civThumbnail.setImageResource(R.drawable.user)
        binding.btnSubmit.setOnClickListener {
            val user = User(
                username = binding.etUsername.text.toString(),
                password = binding.etPassword.text.toString(),
                thumbnail = if (path != "") path else ""
            )
            handleSubmit(user)
        }

        binding.civThumbnail.setOnClickListener {
            checkStoragePermission()
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
                    "new_Image",
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

    private fun handleSubmit(user: User) {
        overlay.visibility = View.VISIBLE
        addRecord(user)
        finish()
    }

    private fun addRecord(user: User) {
        userHelper.open()
        val values = ContentValues()
        values.put(USERNAME, user.username)
        values.put(PASSWORD, user.password)
        values.put(THUMBNAIL, user.thumbnail)
        userHelper.insert(values)
        userHelper.close()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}