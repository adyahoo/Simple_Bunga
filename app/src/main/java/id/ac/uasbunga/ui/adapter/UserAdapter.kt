package id.ac.uasbunga.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.ac.uasbunga.R
import id.ac.uasbunga.data.model.User
import id.ac.uasbunga.databinding.ItemUserBinding

class UserAdapter(private val onItemClickCallback: OnItemClickCallback) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private val listData: ArrayList<User> = ArrayList<User>()

    fun setData(list: ArrayList<User>) {
        listData.clear()
        listData.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemUserBinding.bind(itemView)
        fun bind(user: User) {
            binding.tvUsername.text = user.username
            Glide.with(itemView.context)
                .load(if (user.thumbnail != "") Uri.parse(user.thumbnail) else R.drawable.user)
                .into(binding.civThumbnail)

            binding.cvUser.setOnClickListener {
                onItemClickCallback.onItemClicked(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listData[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = listData.count()

    interface OnItemClickCallback {
        fun onItemClicked(user: User)
    }
}