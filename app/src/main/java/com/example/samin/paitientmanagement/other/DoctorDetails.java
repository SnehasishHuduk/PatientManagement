package com.example.samin.paitientmanagement.other;

import android.util.Log;

/**
 * Created by samin on 11/6/2016.
 */
public class DoctorDetails {
    private String Name, Email, Phone, Qualification, Image_Url;

    public DoctorDetails(String name, String email, String phone, String qualification, String image_Url) {
        Name = name;
        Email = email;
        Phone = phone;
        Qualification = qualification;
        Image_Url = image_Url;
    }

    public DoctorDetails()
    {

    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getQualification() {
        return Qualification;
    }

    public void setQualification(String qualification) {
        Qualification = qualification;
    }

    public String getImage_Url() {
        return Image_Url;
    }

    public void setImage_Url(String image_Url) {
        Image_Url = image_Url;
    }
}