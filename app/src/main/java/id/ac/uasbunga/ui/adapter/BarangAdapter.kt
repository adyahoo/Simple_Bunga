package id.ac.uasbunga.ui.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.ac.uasbunga.R
import id.ac.uasbunga.data.model.Barang
import id.ac.uasbunga.databinding.ItemBarangBinding
import id.ac.uasbunga.helper.LocalDataHelper

class BarangAdapter(private val onItemClickCallback: OnItemClickCallback) :
    RecyclerView.Adapter<BarangAdapter.ViewHolder>() {
    private val listItem: ArrayList<Barang> = ArrayList()

    fun setData(list: ArrayList<Barang>) {
        listItem.clear()
        listItem.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemBarangBinding.bind(itemView)
        fun bind(barang: Barang) {
            binding.tvKode.text = barang.kode
            binding.tvNama.text = barang.nama
            binding.tvHarga.text = "Rp. ${barang.harga}"
            binding.tvStok.text = "Stok : ${barang.stok}"

            val imgId = if (barang.gambar.contains("flower"))
                itemView.resources.getIdentifier(
                    barang.gambar,
                    "drawable",
                    itemView.context.packageName
                )
            else
                Uri.parse(barang.gambar)
            Glide.with(itemView.context)
                .load(imgId)
                .into(binding.ivGambar)

            binding.cvItem.setOnClickListener {
                onItemClickCallback.onItemClicked(barang)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_barang, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listItem[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = listItem.count()

    interface OnItemClickCallback {
        fun onItemClicked(barang: Barang)
    }
}