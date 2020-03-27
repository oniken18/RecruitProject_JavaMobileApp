package com.example.testapp.ClassObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class City implements Comparable{

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

    @Override
    public int compareTo(Object st) {
        return this.CityId-((City)st).getCityId();
    }

    public static void SortA_Z(ArrayList<City> AL, Boolean isReverse) {
        if (isReverse) {
            Collections.sort(AL, new Comparator<City>() {
                @Override
                public int compare(City o1, City o2) {
                    return o1.getCity().toLowerCase().compareTo(o2.getCity().toLowerCase());
                }
            });
        }else {
            Collections.sort(AL, new Comparator<City>() {
                @Override
                public int compare(City o1, City o2) {
                    return o2.getCity().toLowerCase().compareTo(o1.getCity().toLowerCase());
                }
            });
        }
    }
}
