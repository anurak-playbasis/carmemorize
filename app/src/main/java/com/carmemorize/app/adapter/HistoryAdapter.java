package com.carmemorize.app.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.carmemorize.app.R;
import com.carmemorize.app.model.HistoryModel;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends ArrayAdapter<HistoryModel> {
    Context mContext;
    private static LayoutInflater inflater=null;
    int resource;
    ViewHolder holder;
    List<HistoryModel> hisModelList = new ArrayList<HistoryModel>();


    public HistoryAdapter(Context context, int resource , List<HistoryModel> hisModelList ) {
        super(context,resource,hisModelList);
        this . mContext              = context;
        this . resource              = resource;
        this . hisModelList    = hisModelList;

        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        holder = null;
        final HistoryModel HistoryModel = hisModelList.get(position);
        Log.e("666",""+position);

        if(convertView == null)
        {
            convertView       = inflater.inflate(R.layout.history_item, null);
            holder            = new ViewHolder();

            holder.hisTime           = (TextView) convertView.findViewById(R.id.his_time);
            holder.hisDate        = (TextView) convertView.findViewById(R.id.his_date);
            holder.hisDetail        = (TextView) convertView.findViewById(R.id.his_Detail);
            holder.historyLayout        = (LinearLayout) convertView.findViewById(R.id.history_layout);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.hisTime.setText(HistoryModel.getHisTime());
        holder.hisDate.setText(HistoryModel.getHisDate());
        holder.hisDetail.setText(HistoryModel.getHisDetail());


        return convertView;
    }

    static class ViewHolder {

        TextView hisTime,hisDate,hisDetail;
        LinearLayout historyLayout;

    }


}
