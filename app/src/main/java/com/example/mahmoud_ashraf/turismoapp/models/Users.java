package com.example.mahmoud_ashraf.turismoapp.models;

/**
 * Created by mahmoud_ashraf on 6/12/2017.
 */

public class Users {
    private String tourGuide_id="";
    private String description="";
    private String username="";
    private String password="";
    private String  age="";
    private String country="";
    private String   email="";
    private String gender="";

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String language="";
    private String profile_pic="";
    private String tourist_id="";
    private String hour_salary="";

    public Users(){
    }
    public Users (String username,String language,String profile_pic){
        this.username = username;
        this.language = language;
        this.profile_pic = profile_pic;
    }
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTourGuide_id() {
        return tourGuide_id;
    }

    public void setTourGuide_id(String tourGuide_id) {
        this.tourGuide_id = tourGuide_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getTourist_id() {
        return tourist_id;
    }

    public void setTourist_id(String tourist_id) {
        this.tourist_id = tourist_id;
    }

    public String getHour_salary() {
        return hour_salary;
    }

    public void setHour_salary(String hour_salary) {
        this.hour_salary = hour_salary;
    }






}
