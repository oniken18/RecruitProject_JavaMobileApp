package com.example.testapp.ClassObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Capacity implements Comparable{

    private int JobCapacityId;
    private String JobCapacity;

    public int getJobCapacityId() {
        return JobCapacityId;
    }

    public void setJobCapacityId(int jobCapacityId) {
        JobCapacityId = jobCapacityId;
    }

    public String getJobCapacity() {
        return JobCapacity;
    }

    public void setJobCapacity(String jobCapacity) {
        JobCapacity = jobCapacity;
    }

    public Capacity(int jobCapacityId, String jobCapacity) {
        JobCapacityId = jobCapacityId;
        JobCapacity = jobCapacity;
    }

    //implements Comparable
    @Override
    public int compareTo(Object st) {
        return this.JobCapacityId-((Capacity)st).getJobCapacityId();
    }

    public static void SortA_Z(ArrayList<Capacity> AL, Boolean isReverse) {
        if (isReverse) {
            Collections.sort(AL, new Comparator<Capacity>() {
                @Override
                public int compare(Capacity o1, Capacity o2) {
                    return o1.getJobCapacity().toLowerCase().compareTo(o2.getJobCapacity().toLowerCase());
                }
            });
        }else {
            Collections.sort(AL, new Comparator<Capacity>() {
                @Override
                public int compare(Capacity o1, Capacity o2) {
                    return o2.getJobCapacity().toLowerCase().compareTo(o1.getJobCapacity().toLowerCase());
                }
            });
        }
    }
}
