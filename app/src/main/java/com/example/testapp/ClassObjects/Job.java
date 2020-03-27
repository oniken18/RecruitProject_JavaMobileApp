package com.example.testapp.ClassObjects;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Job implements Comparable {

    private int JobId;
    private int JobNumber;
    private String Job;
    private int CityId;
    private int AreaId;
    private int JobCapacityId;
    private int CategoryId;
    private int SubCategoryId;
    private Boolean IsActive;


    public Job(int jobId, int jobNumber, String job, int cityId, int areaId, int jobCapacityId, int categoryId, int subCategoryId, Boolean isActive) {
        JobId = jobId;
        JobNumber = jobNumber;
        Job = job;
        CityId = cityId;
        AreaId = areaId;
        JobCapacityId = jobCapacityId;
        CategoryId = categoryId;
        SubCategoryId = subCategoryId;
        IsActive = isActive;
    }

    public Job(int jobId, int jobNumber, String job) {
        JobId = jobId;
        JobNumber = jobNumber;
        Job = job;
    }

    public Job() {
    }

    public int getJobId() {
        return JobId;
    }

    public void setJobId(int jobId) {
        JobId = jobId;
    }

    public int getJobNumber() {
        return JobNumber;
    }

    public void setJobNumber(int jobNumber) {
        JobNumber = jobNumber;
    }

    public String getJob() {
        return Job;
    }

    public void setJob(String job) {
        Job = job;
    }

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }

    public int getAreaId() {
        return AreaId;
    }

    public void setAreaId(int areaId) {
        AreaId = areaId;
    }

    public int getJobCapacityId() {
        return JobCapacityId;
    }

    public void setJobCapacityId(int jobCapacityId) {
        JobCapacityId = jobCapacityId;
    }

    public int getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(int categoryId) {
        CategoryId = categoryId;
    }

    public int getSubCategoryId() {
        return SubCategoryId;
    }

    public void setSubCategoryId(int subCategoryId) {
        SubCategoryId = subCategoryId;
    }

    public Boolean getIsActive() {
        return IsActive;
    }

    public void setIsActive(Boolean isActive) {
        IsActive = isActive;
    }

    //implements Comparable
    @Override
    public int compareTo(Object st) {
        return this.JobId-((Job)st).getJobId();
    }

    public static void SortA_Z(ArrayList<Job> AL, Boolean isReverse) {
        if (isReverse) {
            Collections.sort(AL, new Comparator<Job>() {
                @Override
                public int compare(Job o1, Job o2) {
                    return o1.getJob().toLowerCase().compareTo(o2.getJob().toLowerCase());
                }
            });
        }else {
            Collections.sort(AL, new Comparator<Job>() {
                @Override
                public int compare(Job o1, Job o2) {
                    return o2.getJob().toLowerCase().compareTo(o1.getJob().toLowerCase());
                }
            });
        }
    }
}
