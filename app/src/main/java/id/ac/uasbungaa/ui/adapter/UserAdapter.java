package id.ac.uasbungaa.ui.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.bumptech.glide.Glide;

import id.ac.uasbungaa.R;
import id.ac.uasbungaa.data.model.User;
import id.ac.uasbungaa.databinding.ItemUserBinding;

import java.util.ArrayList;

import kotlin.jvm.internal.Intrinsics;

import org.jetbrains.annotations.NotNull;

public final class UserAdapter extends Adapter<UserAdapter.ViewHolder> {
    private final ArrayList<User> listData;
    private final UserAdapter.OnItemClickCallback onItemClickCallback;

    public final void setData(@NotNull ArrayList list) {
        this.listData.clear();
        this.listData.addAll(list);
        this.notifyDataSetChanged();
    }

    @NotNull
    public UserAdapter.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User item = listData.get(position);
        holder.bind(item);
    }

    public int getItemCount() {
        return listData.size();
    }

    public UserAdapter(@NotNull UserAdapter.OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
        this.listData = new ArrayList();
    }

    public final class ViewHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        @NotNull
        private final ItemUserBinding binding;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = ItemUserBinding.bind(itemView);
        }

        @NotNull
        public final ItemUserBinding getBinding() {
            return this.binding;
        }

        public final void bind(@NotNull final User user) {
            binding.tvUsername.setText(user.getUsername());
            Glide.with(itemView.getContext())
                    .load(!Intrinsics.areEqual(user.getThumbnail(), "") ? Uri.parse(user.getThumbnail()) : R.drawable.user)
                    .into(binding.civThumbnail);

            binding.cvUser.setOnClickListener((OnClickListener) (new OnClickListener() {
                public final void onClick(View it) {
                    UserAdapter.this.onItemClickCallback.onItemClicked(user);
                }
            }));
        }
    }

    public interface OnItemClickCallback {
        void onItemClicked(@NotNull User user);
    }
}