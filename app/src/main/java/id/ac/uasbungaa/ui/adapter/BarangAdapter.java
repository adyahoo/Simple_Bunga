package id.ac.uasbungaa.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.bumptech.glide.Glide;

import id.ac.uasbungaa.R;
import id.ac.uasbungaa.data.model.Barang;
import id.ac.uasbungaa.databinding.ItemBarangBinding;

import java.util.ArrayList;
import java.util.Collection;

import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

import org.jetbrains.annotations.NotNull;

public final class BarangAdapter extends Adapter<BarangAdapter.ViewHolder> {
    private final ArrayList<Barang> listItem;
    private final BarangAdapter.OnItemClickCallback onItemClickCallback;

    public final void setData(@NotNull ArrayList list) {
        this.listItem.clear();
        this.listItem.addAll((Collection) list);
        this.notifyDataSetChanged();
    }

    @NotNull
    public BarangAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_barang, parent, false);
        return new BarangAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Barang item = listItem.get(position);
        holder.bind(item);
    }

    public int getItemCount() {
        return listItem.size();
    }

    public BarangAdapter(@NotNull BarangAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
        this.listItem = new ArrayList();
    }

    public final class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @NotNull
        private final ItemBarangBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemBarangBinding.bind(itemView);
        }

        @NotNull
        public final ItemBarangBinding getBinding() {
            return this.binding;
        }

        public final void bind(@NotNull final Barang barang) {
            binding.tvKode.setText(barang.getKode());
            binding.tvNama.setText(barang.getNama());
            binding.tvHarga.setText(String.valueOf(barang.getHarga()));
            binding.tvStok.setText(String.valueOf(barang.getStok()));

            Comparable imgId;
            if (barang.getGambar().contains("flower")) {
                imgId = itemView.getResources().getIdentifier(
                        barang.getGambar(),
                        "drawable",
                        itemView.getContext().getPackageName()
                );
            } else {
                imgId = Uri.parse(barang.getGambar());
            }

            Glide.with(itemView.getContext())
                    .load(imgId)
                    .into(binding.ivGambar);

            this.binding.cvItem.setOnClickListener((OnClickListener) (new OnClickListener() {
                public final void onClick(View it) {
                    BarangAdapter.this.onItemClickCallback.onItemClicked(barang);
                }
            }));
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(@NotNull Barang barang);
    }
}
