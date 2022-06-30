package id.ac.uasbunga.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.ac.uasbunga.data.local.UserHelper
import id.ac.uasbunga.data.model.User
import id.ac.uasbunga.databinding.ActivityLogin200020016Binding
import id.ac.uasbunga.helper.MappingHelper
import id.ac.uasbunga.helper.ToastHelper

class Login200020016Activity : AppCompatActivity() {
    private lateinit var binding: ActivityLogin200020016Binding
    private lateinit var userHelper: UserHelper
    private var userData = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin200020016Binding.inflate(layoutInflater)
        setContentView(binding.root)

        userHelper = UserHelper.getInstance(this)

        binding.btnSubmit.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val password = binding.etPassword.text.toString()
            handleLogin(username, password)
        }
    }

    private fun handleLogin(username: String, password: String) {
        getUser(username, password)
        if (userData.id != 0 && userData.username == username) {
            val intent = Intent(this, Dashboard200020016Activity::class.java)
            intent.putExtra(Dashboard200020016Activity.EXTRA_USER, userData)
            startActivity(intent)
            finish()
        } else {
            ToastHelper.showToast("User Tidak Ditemukan", this)
        }
    }

    private fun getUser(username: String, password: String) {
        val userCursor = userHelper.login(username, password)
        userData = MappingHelper.mapCursor(userCursor)
    }
}