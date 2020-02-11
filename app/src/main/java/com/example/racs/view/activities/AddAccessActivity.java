package com.example.racs.view.activities;

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

import androidx.appcompat.app.AppCompatActivity;

import com.example.racs.R;
import com.example.racs.data.entities.AccessPostEntity;
import com.example.racs.data.repository.AccessesRepository;
import com.example.racs.domain.usecases.OnCompleteListener;
import com.example.racs.domain.usecases.addusecases.AddAccess;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddAccessActivity extends AppCompatActivity {
    private EditText l_id, u_id, datestart, timestart, datefinish, timefinish;
    private Button add_btn;
    private ImageView date_finish, date_start;
    private static SharedPreferences settings;
    private static final String ACCESS_TOKEN = "ACCESS";
    private static final String APP_PREFERENCES = "mysettings";
    private static final AccessesRepository accessesRepository = new AccessesRepository();
    private static AddAccess usecaseAddAccess;

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
                AccessPostEntity accessPostEntity = new AccessPostEntity();
                int l = Integer.valueOf(l_id.getText().toString());
                int u = Integer.valueOf(u_id.getText().toString());

                accessPostEntity.setLock(l);
                accessPostEntity.setUser(u);

                String s = (datestart.getText().toString() + "T" + timestart.getText().toString());
                String f = (datefinish.getText().toString() + "T" + timefinish.getText().toString());
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


                accessPostEntity.setAccessStart(start);
                accessPostEntity.setAccessStop(finish);

                usecaseAddAccess = new AddAccess(accessesRepository, accessPostEntity, settings.getString(ACCESS_TOKEN, ""), new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(Boolean smt) {
                        boolean added = smt;
                        Intent intent = new Intent();
                        intent.putExtra(ADDED, added);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
                usecaseAddAccess.addUser();

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
        new DatePickerDialog(AddAccessActivity.this, d1,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void setDate2(View v) {
        new DatePickerDialog(AddAccessActivity.this, d2,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка начальных даты и времени
    private void setInitialDate(TextView t) {
        int m = date.get(Calendar.MONTH) + 1;
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


    public void onCalendarClickStart(View view) {
        setDate1(view);
    }

    public void onCalendarClickFinish(View view) {
        setDate2(view);
    }

    public void onBackClick3(View view) {
        finish();
    }
}
