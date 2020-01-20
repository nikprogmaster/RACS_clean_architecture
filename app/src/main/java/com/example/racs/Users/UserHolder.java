package com.example.racs.Users;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racs.R;

public class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView nsp;
    TextView c_id;
    ImageView del_btn;

    private OnBinClickListener onBinClickListener;

    UserHolder (@NonNull View itemView, OnBinClickListener onBinClickListener) {
        super(itemView);
        this.onBinClickListener = onBinClickListener;
        nsp = itemView.findViewById(R.id.NSP);
        c_id = itemView.findViewById(R.id.card_id);
        del_btn = itemView.findViewById(R.id.del_user1);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        onBinClickListener.onBinClick(view, getItemId());
    }

    public static interface OnBinClickListener {
        public void onBinClick(View v, long id);
    }
}

