package com.example.racs.presentation.view.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.racs.App;
import com.example.racs.R;
import com.example.racs.model.data.AccessPostEntityData;
import com.example.racs.presentation.view.activities.OnCloseFragmentManager;
import com.example.racs.presentation.viewmodel.AccessViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddAccessFragment extends Fragment {

    private EditText lId;
    private EditText uId;
    private EditText datestart;
    private EditText timestart;
    private EditText datefinish;
    private EditText timefinish;
    private Button addButton;
    private ImageView dateFinish;
    private ImageView dateStart;
    private ImageView back;
    private String start = "2000-01-01T01:01:00Z";
    private String finish = "2000-01-01T01:01:00Z";
    private Calendar date = Calendar.getInstance();
    private AccessViewModel accessViewModel;
    private OnCloseFragmentManager onCloseFragmentManager;

    private DatePickerDialog.OnDateSetListener d1 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            date.set(Calendar.YEAR, year);
            date.set(Calendar.MONTH, monthOfYear);
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDate(datestart);
        }
    };

    private DatePickerDialog.OnDateSetListener d2 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            date.set(Calendar.YEAR, year);
            date.set(Calendar.MONTH, monthOfYear);
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDate(datefinish);
        }
    };


    public static AddAccessFragment newInstance() {

        Bundle args = new Bundle();

        AddAccessFragment fragment = new AddAccessFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_add_access, container, false);
        initViews(root);
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        onCloseFragmentManager = (OnCloseFragmentManager) getActivity();

        back.setOnClickListener(v -> onCloseFragmentManager.onClose());

        addButton.setOnClickListener(view -> {
            AccessPostEntityData accessPostEntityData = new AccessPostEntityData();
            int l = Integer.valueOf(lId.getText().toString());
            int u = Integer.valueOf(uId.getText().toString());

            accessPostEntityData.setLock(l);
            accessPostEntityData.setUser(u);

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


            accessPostEntityData.setAccessStart(start);
            accessPostEntityData.setAccessStop(finish);

            accessViewModel = ViewModelProviders.of(getActivity(), App.getAccessModelFactory())
                    .get(AccessViewModel.class);
            accessViewModel.addAccess(accessPostEntityData);

            onCloseFragmentManager.onClose();
        });

        dateStart.setOnClickListener(v -> setDate1());


        dateFinish.setOnClickListener(v -> setDate2());
    }


    public void setDate1() {
        new DatePickerDialog(getActivity(), d1,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH))
                .show();

    }

    public void setDate2() {
        new DatePickerDialog(getActivity(), d2,
                date.get(Calendar.YEAR),
                date.get(Calendar.MONTH),
                date.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    private void initViews(View root) {
        lId = root.findViewById(R.id.l_id);
        uId = root.findViewById(R.id.u_id);
        datestart = root.findViewById(R.id.datestart);
        timestart = root.findViewById(R.id.timestart);
        datefinish = root.findViewById(R.id.datefinish);
        timefinish = root.findViewById(R.id.timefinish);
        addButton = root.findViewById(R.id.add_access);
        dateFinish = root.findViewById(R.id.imageView);
        dateStart = root.findViewById(R.id.imageView2);
        back = root.findViewById(R.id.back);
    }

    // установка начальных даты и времени
    private void setInitialDate(TextView t) {
        int m = date.get(Calendar.MONTH) + 1;
        String dat = date.get(Calendar.DAY_OF_MONTH) + "." + m + "." + date.get(Calendar.YEAR);
        t.setText(dat);
    }


}
