package com.carmemorize.app.model;

/**
 * Created by User on 11/7/2016.
 */

public class HistoryModel {


    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getHisTime() {
        return hisTime;
    }

    public void setHisTime(String hisTime) {
        this.hisTime = hisTime;
    }

    public String getHisDate() {
        return hisDate;
    }

    public void setHisDate(String hisDate) {
        this.hisDate = hisDate;
    }

    public String getHisDetail() {
        return hisDetail;
    }

    public void setHisDetail(String hisDetail) {
        this.hisDetail = hisDetail;
    }

    String carId;
    String hisTime;
    String hisDate;
    String hisDetail;


    public HistoryModel(String carId, String hisTime, String hisDate, String hisDetail)
    {
        this.carId           =  carId;
        this.hisTime         =  hisTime;
        this.hisDate           =  hisDate;
        this.hisDetail          =  hisDetail;


    }



}

