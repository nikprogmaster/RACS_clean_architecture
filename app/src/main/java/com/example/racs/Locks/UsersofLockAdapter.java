package com.example.racs.Locks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racs.R;
import com.example.racs.Users.UsersPOJO;

import java.util.ArrayList;

public class UsersofLockAdapter extends RecyclerView.Adapter<UsersofLockAdapter.UserHolder> {

    private final ArrayList<UsersPOJO.User> users_list = new ArrayList<>();


    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.users_of_lock_kist, parent, false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        UsersPOJO.User user = users_list.get(position);
        String NSP = user.getLastName()+" "+user.getFirstName()+" "+user.getPatronymic();
        holder.nsp.setText(NSP);
        holder.uol_id.setText(String.valueOf(user.getUId()));

    }



    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return users_list.size();
    }

    public void replaceUsers (ArrayList<UsersPOJO.User> u) {
        this.users_list.clear();
        this.users_list.addAll(u);
        notifyDataSetChanged();
    }
    class UserHolder extends RecyclerView.ViewHolder {

        TextView uol_id;
        TextView nsp;

        UserHolder(@NonNull View itemView) {
            super(itemView);
            uol_id = itemView.findViewById(R.id.uol_id);
            nsp = itemView.findViewById(R.id.fio);

        }
    }

}


