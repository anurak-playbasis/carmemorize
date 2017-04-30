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
public class BreakFragment extends android.support.v4.app.Fragment {
    Cursor cursor;
    SQLiteDatabase db;
    EditText dateBreak;


    public static BreakFragment newInstance() {
        BreakFragment fragment = new BreakFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_break_service, container, false);


        dateBreak    = (EditText) rootView.findViewById(R.id.et_date_break);



        return rootView;
    }


}

