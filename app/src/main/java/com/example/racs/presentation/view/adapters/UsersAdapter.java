package com.example.racs.presentation.view.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racs.R;
import com.example.racs.model.data.UsersEntityData;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UserHolder> {

    private final List<UsersEntityData.User> users_list = new ArrayList<>();
    private UsersAdapterListener usersAdapterListener;

    public UsersAdapter(UsersAdapterListener usersAdapterListener) {
        this.usersAdapterListener = usersAdapterListener;
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.user_item, parent, false);
        return new UserHolder(view, usersAdapterListener);
    }


    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        final int itemId = users_list.get(position).getUId();
        UsersEntityData.User user = users_list.get(position);
        holder.bindViews(user);
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return users_list.size();
    }

    public void replaceUsers(List<UsersEntityData.User> u) {
        this.users_list.clear();
        this.users_list.addAll(u);
        notifyDataSetChanged();
    }

    class UserHolder extends RecyclerView.ViewHolder {

        TextView nsp;
        TextView c_id;
        ImageView del_btn;

        private UsersAdapterListener usersAdapterListener;

        UserHolder(@NonNull View itemView, UsersAdapterListener usersAdapterListener) {
            super(itemView);

            this.usersAdapterListener = usersAdapterListener;
            nsp = itemView.findViewById(R.id.NSP);
            c_id = itemView.findViewById(R.id.card_id);
            del_btn = itemView.findViewById(R.id.del_user1);
        }

        public void bindViews(UsersEntityData.User user) {
            String NSP = user.getLastName() + " " + user.getFirstName() + " " + user.getPatronymic();
            final int uId = user.getUId();
            nsp.setText(NSP);
            c_id.setText(user.getCardId());
            del_btn.setImageResource(R.drawable.ic_delete_black_24dp);
            del_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    usersAdapterListener.onBinClick(uId);
                }
            });
        }

    }

    public interface UsersAdapterListener {
        void onBinClick(int id);
    }

}
