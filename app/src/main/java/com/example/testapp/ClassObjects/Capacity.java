package com.example.testapp.ClassObjects;

public class Capacity {

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
}
