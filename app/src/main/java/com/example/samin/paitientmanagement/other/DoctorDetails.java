package com.example.samin.paitientmanagement.other;

import android.util.Log;

/**
 * Created by samin on 11/6/2016.
 */
public class DoctorDetails {
    public DoctorDetails(int image_id, String name, String email,String phone, String spec)
    {
        Log.d("DoctorDetails","Constractor Called");
        this.setImage_id(image_id);
        this.setName(name);
        this.setEmail(email);
        this.setPhone(phone);
        this.setSpec(spec);
    }
    private int image_id;
    private String name,email,phone,spec;

    public int getImage_id() {
        Log.d("DoctorDetails","getImage_id Called");
        return image_id;
    }

    public void setImage_id(int image_id) {
        Log.d("DoctorDetails","SetImage_id Called");
        this.image_id = image_id;
    }

    public String getName() {
        Log.d("DoctorDetails","getNameCalled");
        return name;
    }

    public void setName(String name) {
        Log.d("DoctorDetails","SettImage_id Called");
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   public String getPhone() {
       return phone;
   }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }
}
