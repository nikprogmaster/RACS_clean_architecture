package com.example.racs.Locks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racs.R;

import java.util.ArrayList;

public class LocksAdapter extends RecyclerView.Adapter<LocksAdapter.ViewHolder> {

    private final ArrayList<LocksPOJO.Lock> locks_list = new ArrayList<>();

    private boolean withoutImage;

    private OnLockClickListener onLockClickListener;

    public boolean isWithoutImage() {
        return withoutImage;
    }

    public void setWithoutImage(boolean withoutImage) {
        this.withoutImage = withoutImage;
    }

    public LocksAdapter(OnLockClickListener onLockClickListener, boolean withoutImage) {
        this.withoutImage = withoutImage;
        this.onLockClickListener = onLockClickListener;
    }

    public interface OnLockClickListener {
        void onLockClick(LocksPOJO.Lock lock);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view;
        if (withoutImage){
            view = layoutInflater.inflate(R.layout.list_item, parent, false);
        } else {
            view = layoutInflater.inflate(R.layout.lock_list, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LocksPOJO.Lock lock = locks_list.get(position);
        if (locks_list.size()>=50 && locks_list.size()%50 == 0 && position == locks_list.size()-20){
            LocksActivity.getLocks(locks_list.size()+50);
        }
        if (withoutImage){
            holder.bindViews();
        } else {
            holder.bindAllViews();
        }
        holder.id.setText(String.valueOf(lock.getLId()));
        holder.description.setText(lock.getDescription().substring(0,3));
        holder.version.setText(lock.getVersion());
        if (!withoutImage){
            holder.del_image.setImageResource(R.drawable.ic_delete_black_24dp);
        }

    }


    @Override
    public int getItemCount() {
        return locks_list.size();
    }

    public void replaceLocks(ArrayList<LocksPOJO.Lock> u) {
        this.locks_list.clear();
        this.locks_list.addAll(u);
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView id;
        TextView description;
        TextView version;
        ImageView del_image;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocksPOJO.Lock lock = locks_list.get(getLayoutPosition());
                    onLockClickListener.onLockClick(lock);
                }
            });
        }

        public void bindAllViews (){
            id = itemView.findViewById(R.id.tw_id);
            description = itemView.findViewById(R.id.tw_descr);
            version = itemView.findViewById(R.id.tw_ver);
            del_image = itemView.findViewById(R.id.delete_iv);
        }

        public void bindViews(){
            id = itemView.findViewById(R.id.t2);
            description = itemView.findViewById(R.id.t3);
            version = itemView.findViewById(R.id.t4);
        }
    }


}
