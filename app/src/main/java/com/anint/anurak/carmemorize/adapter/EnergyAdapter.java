package com.anint.anurak.carmemorize.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anint.anurak.carmemorize.R;
import com.anint.anurak.carmemorize.model.CarModel;
import com.anint.anurak.carmemorize.model.EnergyModel;

import java.util.ArrayList;
import java.util.List;

public class EnergyAdapter extends ArrayAdapter<EnergyModel> {
    Context mContext;
    private static LayoutInflater inflater=null;
    int resource;
    ViewHolder holder;
    List<EnergyModel> energyModelList = new ArrayList<EnergyModel>();


    public EnergyAdapter(Context context, int resource , List<EnergyModel> energyModelList ) {
        super(context,resource,energyModelList);
        this . mContext              = context;
        this . resource              = resource;
        this . energyModelList    = energyModelList;

        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        holder = null;
        final EnergyModel energyModel = energyModelList.get(position);
        Log.e("666",""+position);

        if(convertView == null)
        {
            convertView       = inflater.inflate(R.layout.energy_item, null);
            holder            = new ViewHolder();

            holder.dateEnergy           = (TextView) convertView.findViewById(R.id.tv_1);
            holder.mile        = (TextView) convertView.findViewById(R.id.tv_2);
            holder.station        = (TextView) convertView.findViewById(R.id.tv_3);
            holder.volume        = (TextView) convertView.findViewById(R.id.tv_4);
            holder.typeVolume        = (TextView) convertView.findViewById(R.id.tv_4_1);
            holder.netPrice        = (TextView) convertView.findViewById(R.id.tv_5);
            holder.typeNetPrice        = (TextView) convertView.findViewById(R.id.tv_5_1);
            holder.energyLayout        = (LinearLayout) convertView.findViewById(R.id.energy_layout);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.dateEnergy.setText(energyModel.getDateEnergy());
        holder.mile.setText(energyModel.getMileage());
        holder.station.setText(energyModel.getStation());
        holder.volume.setText(energyModel.getVolume());
        holder.typeVolume.setText(energyModel.getTypeVolume());
        holder.netPrice.setText(energyModel.getNetPrice());
        holder.typeNetPrice.setText(energyModel.getTypeNetPrice());


        return convertView;
    }

    static class ViewHolder {

        TextView dateEnergy,mile,volume,typeVolume,netPrice,typeNetPrice,station;
        LinearLayout energyLayout;

    }


}
