package com.anint.anurak.carmemorize.model;

/**
 * Created by User on 11/7/2016.
 */

public class EnergyModel {


    String carId;
    String dateEnergy;
    String station;
    String typeGass;
    String mileage;
    String volume;
    String typeVolume;
    String netPrice;
    String typeNetPrice;

    public EnergyModel(String carId, String dateEnergy, String station, String typeGass,
                       String mileage, String volume,String typeVolume, String netPrice,String typeNetPrice)
    {
        this.carId           =  carId;
        this.dateEnergy         =  dateEnergy;
        this.station           =  station;
        this.typeGass          =  typeGass;
        this.mileage    =  mileage;
        this.volume      =  volume;
        this.typeVolume      =  typeVolume;
        this.netPrice      =  netPrice;
        this.typeNetPrice      =  typeNetPrice;

    }


    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getDateEnergy() {
        return dateEnergy;
    }

    public void setDateEnergy(String dateEnergy) {
        this.dateEnergy = dateEnergy;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getTypeGass() {
        return typeGass;
    }

    public void setTypeGass(String typeGass) {
        this.typeGass = typeGass;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getTypeVolume() {
        return typeVolume;
    }

    public void setTypeVolume(String typeVolume) {
        this.typeVolume = typeVolume;
    }

    public String getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(String netPrice) {
        this.netPrice = netPrice;
    }

    public String getTypeNetPrice() {
        return typeNetPrice;
    }

    public void setTypeNetPrice(String typeNetPrice) {
        this.typeNetPrice = typeNetPrice;
    }



}

