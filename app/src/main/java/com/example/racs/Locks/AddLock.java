package com.example.racs.Locks;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.racs.App;
import com.example.racs.Authorization.AuthorizationActivity;
import com.example.racs.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLock extends AppCompatActivity {

    private static SharedPreferences settings;
    private static final String ACCESS_TOKEN = "ACCESS";
    private static final String APP_PREFERENCES = "mysettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lock);

        settings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

        Button btn = findViewById(R.id.button);
        final EditText des = findViewById(R.id.descr);
        final EditText ver = findViewById(R.id.ver);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String d;
                int v;
                LockPostPOJO body;
                if (lockDataCheck(String.valueOf(des.getText())) && lockDataCheck(String.valueOf(ver.getText())))
                {
                    d = String.valueOf(des.getText());
                    v = Integer.parseInt(String.valueOf(ver.getText()));
                    body = new LockPostPOJO();
                    body.setDescription(d);
                    body.setVersion(v);
                    App.getApi().addLock(settings.getString(ACCESS_TOKEN, ""), body).enqueue(new Callback<LocksPOJO>() {
                        @Override
                        public void onResponse(Call<LocksPOJO> call, Response<LocksPOJO> response) {
                            LocksPOJO newLock;
                            newLock = response.body();
                            Toast.makeText(AddLock.this, "Замок успешно добавлен!", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<LocksPOJO> call, Throwable t) {
                            Toast.makeText(AddLock.this, "An error occurred during networking", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else
                    if (!lockDataCheck(String.valueOf(des.getText())))
                        des.setError("Введенные данные некорректны");
                    if(!lockDataCheck(String.valueOf(ver.getText())))
                        ver.setError("Введенные данные некорректны");
            }
        });
    }


    public boolean lockDataCheck (String des) {
        if (des==null)
            return false;
        for (int i=0; i<des.length(); i++){
            if (des.charAt(i)<'0' || des.charAt(i)>'9')
                return false;
        }
        return true;
    }
}
