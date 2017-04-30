package com.carmemorize.app.fragment;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.carmemorize.app.R;


/**
 * Created by Cocozc on 12/11/2016 AD.
 */
public class OilFragment extends android.support.v4.app.Fragment {
        Cursor cursor;
        SQLiteDatabase db;
        EditText dateOil;


public static OilFragment newInstance() {
        OilFragment fragment = new OilFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

        }
@Override
public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_oil_service, container, false);


        dateOil   = (EditText) rootView.findViewById(R.id.et_date_oil);



        return rootView;
        }


        }

