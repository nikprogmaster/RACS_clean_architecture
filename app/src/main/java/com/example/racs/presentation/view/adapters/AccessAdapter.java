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
import com.example.racs.model.data.AccessEntityData;
import com.example.racs.model.data.LocksEntityData;
import com.example.racs.model.data.UsersEntityData;

import java.util.ArrayList;
import java.util.List;

public class AccessAdapter extends RecyclerView.Adapter<AccessAdapter.ViewHolder> {

    private final List<AccessEntityData.Access> accesses = new ArrayList<>();

    private OnDeleteListener onDeleteListener;
    private List<LocksEntityData.Lock> locks;
    private List<UsersEntityData.User> users;

    public AccessAdapter(List<LocksEntityData.Lock> locks, List<UsersEntityData.User> users, OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
        this.locks = locks;
        this.users = users;

    }

    public void replaceAccesses(List<AccessEntityData.Access> a) {
        this.accesses.clear();
        this.accesses.addAll(a);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.access_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AccessEntityData.Access access = accesses.get(position);
        holder.bindViews(access);

    }

    @Override
    public int getItemCount() {
        return accesses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView fio;
        TextView aud;
        ImageView del_acc;

        private ViewHolder(View itemview) {
            super(itemview);
            fio = itemview.findViewById(R.id.famNamPat);
            aud = itemview.findViewById(R.id.aud);
            del_acc = itemview.findViewById(R.id.del_acc);
        }

        private void bindViews(AccessEntityData.Access access) {
            fio.setText(AccessEntityData.searchUserById(users, access.getUser()));
            String auditoria = AccessEntityData.searchLockById(locks, access.getLock()).substring(0, 3);
            aud.setText(auditoria);
            del_acc.setImageResource(R.drawable.ic_delete_black_24dp);
            del_acc.setTag(access.getA_id());
            itemView.setTag(access.getA_id());

            del_acc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) view.getTag();
                    onDeleteListener.onDelete(position);

                }
            });
        }
    }

    public interface OnDeleteListener {
        void onDelete(int position);
    }
}
