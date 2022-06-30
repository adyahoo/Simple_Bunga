package id.ac.uasbunga.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.uasbunga.R
import id.ac.uasbunga.data.local.UserHelper
import id.ac.uasbunga.data.model.User
import id.ac.uasbunga.databinding.ActivityUser200020016Binding
import id.ac.uasbunga.helper.LocalDataHelper
import id.ac.uasbunga.ui.adapter.UserAdapter

class User200020016Activity : AppCompatActivity() {
    private lateinit var binding: ActivityUser200020016Binding
    private lateinit var allUser: ArrayList<User>
    private lateinit var userAdapter: UserAdapter
    private lateinit var userHelper: UserHelper
    private lateinit var emptyView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUser200020016Binding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "User Data"

        emptyView = findViewById(R.id.tv_empty)
        userHelper = UserHelper.getInstance(this)

        binding.fab.setOnClickListener {
            val intent = Intent(this, AddUserActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        allUser = LocalDataHelper.getAllUser(userHelper)

        emptyView.visibility = if (allUser.count() == 0) View.VISIBLE else View.GONE

        userAdapter = UserAdapter(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: User) {
                val intent =
                    Intent(this@User200020016Activity, Detail200020016UserActivity::class.java)
                intent.putExtra(Detail200020016UserActivity.EXTRA_USER, user)
                startActivity(intent)
            }
        })
        userAdapter.setData(allUser)
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = userAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}