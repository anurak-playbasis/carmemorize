package com.carmemorize.app.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carmemorize.app.R;
import com.carmemorize.app.activity.CarShow;
import com.carmemorize.app.activity.ListEnergy;
import com.carmemorize.app.activity.Service;
import com.carmemorize.app.activity.TaxShow;
import com.carmemorize.app.component.Constants;
import com.carmemorize.app.model.CarModel;

import java.util.ArrayList;
import java.util.List;

public class CarAdapter extends ArrayAdapter<CarModel> {
    Context mContext;
    private static LayoutInflater inflater=null;
    int resource;
    ViewHolder holder;
    List<CarModel> carModelList = new ArrayList<CarModel>();


    public CarAdapter(Context context, int resource , List<CarModel> carModelList ) {
        super(context,resource,carModelList);
        this . mContext              = context;
        this . resource              = resource;
        this . carModelList    = carModelList;

        inflater = ( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        holder = null;
        final CarModel carModel = carModelList.get(position);
        Log.e("666",""+position);

        if(convertView == null)
        {
            convertView       = inflater.inflate(R.layout.car_item, null);
            holder            = new ViewHolder();

            holder.nameCar           = (TextView) convertView.findViewById(R.id.name_of_car);
            holder.brandCar        = (TextView) convertView.findViewById(R.id.brand_of_car);
            holder.carLayout        = (LinearLayout) convertView.findViewById(R.id.car_layout);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.nameCar.setText(carModel.getName());
        holder.brandCar.setText(carModel.getBrand());



        holder.carLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO show add my car;
                Log.e("777","carId"+carModel.getCarId());

                Bundle extras = ((Activity) mContext).getIntent().getExtras();
                String numberPage = null;
                if (extras != null) {
                    numberPage = extras.getString("number");
                }
                Log.e("888","number"+numberPage);

                if (numberPage.equals("1")){
                    Intent intent = new Intent(getContext(), CarShow.class);
                    intent.putExtra(Constants.CAR_ID, carModel.getCarId());
                    getContext().startActivity(intent);

                }
                else if (numberPage.equals("2")){
                    Intent intent = new Intent(getContext(), ListEnergy.class);
                    intent.putExtra(Constants.CAR_ID, carModel.getCarId());
                    intent.putExtra(Constants.CAR_NAME, carModel.getName());
                    getContext().startActivity(intent);
                }
                else if (numberPage.equals("3")){
                    Intent intent = new Intent(getContext(), TaxShow.class);
                    intent.putExtra(Constants.CAR_ID, carModel.getCarId());
                    getContext().startActivity(intent);
                }
                else if (numberPage.equals("4")){
                    Intent intent = new Intent(getContext(), Service.class);
                    intent.putExtra(Constants.CAR_ID, carModel.getCarId());
                    getContext().startActivity(intent);
                }
                else if (numberPage.equals("5")){
                    Intent intent = new Intent(getContext(), CarShow.class);
                    intent.putExtra(Constants.CAR_ID, carModel.getCarId());
                    getContext().startActivity(intent);
                }
                else if (numberPage.equals("6")){
                    Intent intent = new Intent(getContext(), CarShow.class);
                    intent.putExtra(Constants.CAR_ID, carModel.getCarId());
                    getContext().startActivity(intent);
                }



            }
        });


        return convertView;
    }

    static class ViewHolder {

        TextView nameCar,brandCar;
        LinearLayout carLayout;

    }


}
