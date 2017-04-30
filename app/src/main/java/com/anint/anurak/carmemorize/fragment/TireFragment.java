package com.anint.anurak.carmemorize.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.anint.anurak.carmemorize.R;


/**
 * Created by Cocozc on 12/11/2016 AD.
 */
public class TireFragment extends android.support.v4.app.Fragment {
    Cursor cursor;
    SQLiteDatabase db;
    EditText dateTire;


    public static TireFragment newInstance() {
        TireFragment fragment = new TireFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tire_service, container, false);


        dateTire  = (EditText) rootView.findViewById(R.id.et_date_radiater);



        return rootView;
    }


}


