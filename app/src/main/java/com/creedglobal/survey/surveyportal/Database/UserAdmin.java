package com.creedglobal.survey.surveyportal.Database;

/**
 * Created by SUJEET on 5/16/2016.
 */
public class UserAdmin {
    private String name;
    private String mobile;
    private String DOB;
    private String email;
    private String occupation;
    private String DOC;
    private String userType;
    private String street;
    private String city;
    private String state;
    private String pincode;
    private String address;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getDOC() {
        return DOC;
    }

    public void setDOC(String DOC) {
        this.DOC = DOC;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getAddress() {
        address=(getStreet()!=null)?getStreet():"";
        address=(getCity()!=null)?address+"\n"+getCity():address;
        address=(getState()!=null)?address+"\n"+getState():address;
        address=(getPincode()!=null)?address+"\n"+getPincode():address;
        return address;
    }

    public void setAddress(String address) {

        this.address = address;
    }
}
