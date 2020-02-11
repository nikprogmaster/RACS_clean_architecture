package com.example.racs.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racs.R;
import com.example.racs.data.entities.UsersEntity;
import com.example.racs.view.fragments.UsersFragment;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UserHolder> {

    private final List<UsersEntity.User> users_list = new ArrayList<>();
    private UserHolder.OnBinClickListener onBinClickListener;

    public UsersAdapter(UserHolder.OnBinClickListener onBinClickListener) {
        this.onBinClickListener = onBinClickListener;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.user_item, parent, false);
        return new UserHolder(view, onBinClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        if (users_list.size() >= 50 && users_list.size() % 50 == 0 && position == users_list.size() - 20) {
            UsersFragment.getUsers(users_list.size() + 50);
        }
        final long itemId = users_list.get(position).getUId();
        UsersEntity.User user = users_list.get(position);
        String NSP = user.getLastName() + " " + user.getFirstName() + " " + user.getPatronymic();
        holder.nsp.setText(NSP);
        holder.c_id.setText(user.getCardId());
        holder.del_btn.setImageResource(R.drawable.ic_delete_black_24dp);
        holder.del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBinClickListener.onBinClick(view, itemId);
            }
        });
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return users_list.size();
    }

    public void replaceUsers(List<UsersEntity.User> u) {
        this.users_list.clear();
        this.users_list.addAll(u);
        notifyDataSetChanged();
    }


}
