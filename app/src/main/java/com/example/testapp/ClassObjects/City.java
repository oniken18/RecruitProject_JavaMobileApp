package com.example.testapp.ClassObjects;

public class City {

    private int CityId;
    private String City;
    private int AreaId;

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public int getAreaId() {
        return AreaId;
    }

    public void setAreaId(int areaId) {
        AreaId = areaId;
    }

    public City(int cityId, String city, int areaId) {
        CityId = cityId;
        City = city;
        AreaId = areaId;
    }
}
