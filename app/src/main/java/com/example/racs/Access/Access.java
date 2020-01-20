package com.example.racs.Access;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.example.racs.App;
import com.example.racs.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Access extends AppCompatActivity {

    private static final int DEFAULT_NUMBER = 50;
    private static RecyclerView accessRecycler;
    private static AccessAdapter accessAdapter;
    public static AccessPOJO accesses;
    private static boolean isAdded = false;
    private Button add_btn;
    private ImageView back;
    private static SharedPreferences settings;
    private static final String ACCESS_TOKEN = "ACCESS";
    private static final String APP_PREFERENCES = "mysettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_access);
        add_btn = findViewById(R.id.button2);
        back = findViewById(R.id.back);

        settings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

        accessAdapter = new AccessAdapter(Access.this, R.layout.activity_access, new AccessAdapter.OnDeleteListener() {
            @Override
            public void onDelete(int position) {
                Log.d("del", String.valueOf(position));
                App.getAccessApi().deleteAccess(settings.getString(ACCESS_TOKEN, ""), position).enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        Toast.makeText(Access.this, "Доступ удален", Toast.LENGTH_SHORT).show();
                        getAccesses(accesses.getResults().size());
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {

                    }
                });
            }
        });
        accessRecycler = findViewById(R.id.access_list);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, RecyclerView.VERTICAL);
        accessRecycler.addItemDecoration(itemDecoration);
        getAccesses(DEFAULT_NUMBER);
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Access.this, AddAccess.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {return;}
        isAdded = data.getBooleanExtra(AddAccess.ADDED, isAdded);
        getAccesses(isAdded ? accesses.getResults().size()+1 : accesses.getResults().size());

    }

    public static void getAccesses(int count) {
        App.getAccessApi().getAccesses(settings.getString(ACCESS_TOKEN, ""), count).enqueue(new Callback<AccessPOJO>() {
            @Override
            public void onResponse(Call<AccessPOJO> call, Response<AccessPOJO> response) {
                accesses = response.body();
                accessRecycler.setAdapter(accessAdapter);
                accessAdapter.replaceAccesses(accesses.getResults());
            }

            @Override
            public void onFailure(Call<AccessPOJO> call, Throwable t) {

            }
        });
    }

    public void onAccBinClick (View view) {
        getAccesses(accesses.getResults().size());

    }

    public void onBackClick2 (View view) {
        finish();
    }


}
