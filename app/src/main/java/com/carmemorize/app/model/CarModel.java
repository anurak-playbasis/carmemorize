package com.carmemorize.app.model;

import org.parceler.Parcel;

/**
 * Created by User on 11/7/2016.
 */
@Parcel
public class CarModel {
    String carId;
    String dateBuy;
    String name;
    String brand;
    String licenseCar;
    String colorCar;
    String photoCar;

    public CarModel(String carId,String dateBuy,String name, String brand,
                    String licenseCar, String colorCar, String photoCar)
    {
        this.carId           =  carId;
        this.dateBuy         =  dateBuy;
        this.name           =  name;
        this.brand          =  brand;
        this.licenseCar    =  licenseCar;
        this.colorCar      =  colorCar;
        this.photoCar      =  photoCar;

    }



    public String getPhotoCar() {
        return photoCar;
    }

    public void setPhotoCar(String photoCar) {
        this.photoCar = photoCar;
    }

    public String getColorCar() {
        return colorCar;
    }

    public void setColorCar(String colorCar) {
        this.colorCar = colorCar;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getDateBuy() {
        return dateBuy;
    }

    public void setDateBuy(String dateBuy) {
        this.dateBuy = dateBuy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLicenseCar() {
        return licenseCar;
    }

    public void setLicenseCar(String licenseCar) {
        this.licenseCar = licenseCar;
    }


}

