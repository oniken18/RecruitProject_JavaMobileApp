package com.example.testapp.ClassObjects;

import android.icu.util.IslamicCalendar;

public class Client {

    private int ClientId;
    private String FirstName;
    private String LastName;
    private String Email;
    private int CityId;
    private String Address;
    private int AreaId;
    private int JobRequestId;
    private int JobTypeId;
    private int CategoryId;
    private int SubCategoryId;
    private int EducationId;
    private String Phone;
    private boolean IsActive;
    private boolean IsFlag;

    public Client(){}

    public Client(int clientID, String firstName, String lastName, String email, int cityId, String address, int areaId, int jobRequestId, int jobTypeId, int categoryId, int subCategoryId, int educationId, String phone, Boolean isActive, Boolean isFlag){
        ClientId = clientID;
        FirstName=firstName;
        LastName=lastName;
        Email=email;
        CityId=cityId;
        Address=address;
        AreaId=areaId;
        JobRequestId=jobRequestId;
        JobTypeId=jobTypeId;
        CategoryId=categoryId;
        SubCategoryId=subCategoryId;
        EducationId=educationId;
        Phone=phone;
        IsActive=isActive;
        IsFlag = isFlag;
    }

    public Client(int clientID, String firstName, String lastName, Boolean isFlag){
        ClientId = clientID;
        FirstName=firstName;
        LastName=lastName;
        IsFlag = isFlag;
    }

    public int getClientId() {
        return ClientId;
    }

    public void setClientId(int clientId) {
        ClientId = clientId;
    }

    public Boolean getIsActive() {
        return IsActive;
    }

    public Boolean getIsFlag() {
        return IsFlag;
    }

    public void setIsActive(Boolean isActive) {
        IsActive = isActive;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public int getCityId() {
        return  CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public int getAreaId() {
        return AreaId;
    }

    public void setAreaId(int areaId) {
        AreaId = areaId;
    }

    public int getJobRequestId() {
        return JobRequestId;
    }

    public void setJobRequestId(int jobRequestId) {
        JobRequestId = jobRequestId;
    }

    public int getJobTypeId() {
        return JobTypeId;
    }

    public void setJobTypeId(int jobTypeId) {
        this.JobTypeId = jobTypeId;
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

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }


}