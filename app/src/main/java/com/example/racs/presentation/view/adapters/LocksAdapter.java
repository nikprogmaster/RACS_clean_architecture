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
import com.example.racs.model.data.LocksEntityData;

import java.util.ArrayList;
import java.util.List;

public class LocksAdapter extends RecyclerView.Adapter<LocksAdapter.ViewHolder> {

    private final List<LocksEntityData.Lock> locks_list = new ArrayList<>();
    private boolean withoutDeleting;
    private OnLockClickListener onLockClickListener;
    private OnDeleteClickListener onDeleteClickListener;

    public LocksAdapter(OnLockClickListener onLockClickListener, boolean withoutDeleting) {
        this.withoutDeleting = withoutDeleting;
        this.onLockClickListener = onLockClickListener;
    }

    public LocksAdapter(OnLockClickListener onLockClickListener, OnDeleteClickListener onDeleteClickListener, boolean withoutDeleting) {
        this.withoutDeleting = withoutDeleting;
        this.onLockClickListener = onLockClickListener;
        this.onDeleteClickListener = onDeleteClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view;
        if (withoutDeleting) {
            view = layoutInflater.inflate(R.layout.list_item, parent, false);
        } else {
            view = layoutInflater.inflate(R.layout.lock_item, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LocksEntityData.Lock lock = locks_list.get(position);
        if (withoutDeleting) {
            holder.bindViews(lock);
        } else {
            holder.bindAllViews(lock);
        }
    }


    @Override
    public int getItemCount() {
        return locks_list.size();
    }

    public void replaceLocks(List<LocksEntityData.Lock> u) {
        this.locks_list.clear();
        this.locks_list.addAll(u);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView id;
        private TextView description;
        private TextView version;
        private ImageView del_image;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocksEntityData.Lock lock = locks_list.get(getLayoutPosition());
                    onLockClickListener.onLockClick(lock);
                }
            });

        }

        public void bindAllViews(final LocksEntityData.Lock lock) {
            id = itemView.findViewById(R.id.tw_id);
            description = itemView.findViewById(R.id.tw_descr);
            version = itemView.findViewById(R.id.tw_ver);
            del_image = itemView.findViewById(R.id.delete_iv);
            id.setText(String.valueOf(lock.getLId()));
            description.setText(lock.getDescription().substring(0, 3));
            version.setText(lock.getVersion());
            del_image.setImageResource(R.drawable.ic_delete_black_24dp);
            del_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onDeleteClickListener.onDelete(lock.getLId());
                }
            });
        }

        public void bindViews(LocksEntityData.Lock lock) {
            id = itemView.findViewById(R.id.t2);
            description = itemView.findViewById(R.id.t3);
            version = itemView.findViewById(R.id.t4);
            id.setText(String.valueOf(lock.getLId()));
            description.setText(lock.getDescription().substring(0, 3));
            version.setText(lock.getVersion());
        }
    }

    public interface OnLockClickListener {
        void onLockClick(LocksEntityData.Lock lock);
    }

    public interface OnDeleteClickListener {
        void onDelete(int id);
    }

}
