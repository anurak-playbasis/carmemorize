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
 * Created by User on 11/4/2016.
 */

public class RadiaterFragment extends android.support.v4.app.Fragment {

    Cursor cursor;
    SQLiteDatabase db;
    EditText dateRadiater;


    public static RadiaterFragment newInstance() {
        RadiaterFragment fragment = new RadiaterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_radiater_service, container, false);


        dateRadiater    = (EditText) rootView.findViewById(R.id.et_date_radiater);



        return rootView;
    }


}

