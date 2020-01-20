package com.example.racs.Access;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.racs.App;
import com.example.racs.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAccess extends AppCompatActivity {
    private EditText l_id, u_id, datestart, timestart, datefinish, timefinish;
    private Button add_btn;
    private ImageView date_finish, date_start;
    private static SharedPreferences settings;
    private static final String ACCESS_TOKEN = "ACCESS";
    private static final String APP_PREFERENCES = "mysettings";

    public static final String ADDED = "ADDED_NEW_VALUE";
    private String start = "2000-01-01T01:01:00Z";
    private String finish = "2000-01-01T01:01:00Z";
    private Calendar date = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_access);
        initViews();
        final String TAG = "Привет, Андрей";

        settings = getSharedPreferences(APP_PREFERENCES, MODE_PRIVATE);

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AccessPostPOJO accessPostPOJO = new AccessPostPOJO();
                int l = Integer.valueOf(l_id.getText().toString());
                int u = Integer.valueOf(u_id.getText().toString());

                accessPostPOJO.setLock(l);
                accessPostPOJO.setUser(u);

                String s = (datestart.getText().toString()+"T"+timestart.getText().toString());
                String f = (datefinish.getText().toString()+"T"+timefinish.getText().toString());
                SimpleDateFormat applFormat = new SimpleDateFormat("dd.MM.yyyy'T'HH:mm");
                try {
                    Date applStartDate = applFormat.parse(s);
                    Date applFinishDate = applFormat.parse(f);
                    SimpleDateFormat apiFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    start = apiFormat.format(applStartDate);
                    finish = apiFormat.format(applFinishDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                accessPostPOJO.setAccessStart(start);
                accessPostPOJO.setAccessStop(finish);



                App.getAccessApi().postAccess(settings.getString(ACCESS_TOKEN, ""), accessPostPOJO).enqueue(new Callback<AccessPOJO>() {
                    @Override
                    public void onResponse(Call<AccessPOJO> call, Response<AccessPOJO> response) {
                        Toast.makeText(AddAccess.this, "Code:"+response.code(), Toast.LENGTH_SHORT).show();
                        if (response.code()==201){
                            Intent intent = new Intent();
                            intent.putExtra(ADDED, true);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<AccessPOJO> call, Throwable t) {

                    }
                });

            }
        });
    }

    private void initViews() {
        l_id = findViewById(R.id.l_id);
        u_id = findViewById(R.id.u_id);
        datestart = findViewById(R.id.datestart);
        timestart = findViewById(R.id.timestart);
        datefinish = findViewById(R.id.datefinish);
        timefinish = findViewById(R.id.timefinish);
        add_btn = findViewById(R.id.add_access);
        date_finish = findViewById(R.id.imageView);
        date_start = findViewById(R.id.imageView2);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra(ADDED, false);
        setResult(RESULT_OK, intent);
    }

    public void setDate1(View v) {
        new DatePickerDialog(AddAccess.this, d1,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH))
                .show();
    }
    public void setDate2(View v) {
        new DatePickerDialog(AddAccess.this, d2,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка начальных даты и времени
    private void setInitialDate(TextView t) {
        int m = date.get(Calendar.MONTH)+1;
        String dat = date.get(Calendar.DAY_OF_MONTH) + "." + m + "." + date.get(Calendar.YEAR);
        t.setText(dat);
    }
    DatePickerDialog.OnDateSetListener d1 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            date.set(Calendar.YEAR, year);
            date.set(Calendar.MONTH, monthOfYear);
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDate(datestart);
        }
    };

    DatePickerDialog.OnDateSetListener d2 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            date.set(Calendar.YEAR, year);
            date.set(Calendar.MONTH, monthOfYear);
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDate(datefinish);
        }
    };


    public void onCalendarClickStart (View view){
        setDate1(view);
    }

    public void onCalendarClickFinish (View view){
        setDate2(view);
    }

    public void onBackClick3 (View view) {
        finish();
    }
}
