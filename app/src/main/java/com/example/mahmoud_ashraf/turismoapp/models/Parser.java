package com.example.mahmoud_ashraf.turismoapp.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mahmoud_ashraf on 6/11/2017.
 */

public class Parser {
    ArrayList<String>result;
    ArrayList<Users>res;
    boolean f=true;
    String ans="";

    public ArrayList<String> ParseLoginResponse(String jsonFile) {
        try {
            result = new ArrayList<>();
            f=true;
            JSONObject jsonObject = new JSONObject(jsonFile);
            if(!jsonObject.isNull("result")) {
               JSONObject res = jsonObject.getJSONObject("result");
               // result.add(data);
                if(!res.isNull("tokken")) {
                    String data = String.valueOf(res.getInt("tokken"));
                    result.add(data);

                }
                if(!res.isNull("user_id")) {
                    String data = res.getString("user_id");
                    result.add(data);
                }

                if(!res.isNull("type")) {
                    String data = res.getString("type");
                    result.add(data);
                }


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
    public ArrayList<Users> ParseGetTourGuidesResponse(String jsonFile) {
        try {
            res = new ArrayList<>();
            f=true;
            JSONObject jsonObject = new JSONObject(jsonFile);
            if(!jsonObject.isNull("result")) {

                JSONArray ja = jsonObject.getJSONArray("result");

                for (int i = 0; i < ja.length(); i++) {
                    String userName ="",userLanguage="",userProfilePic="";
                    JSONObject jo = ja.getJSONObject(i);
                    if(!jo.isNull("username")) {
                         userName = String.valueOf(jo.getString("username"));
                    }
                    if(!jo.isNull("language")) {
                         userLanguage = String.valueOf(jo.getString("language"));
                    }

                    if(!jo.isNull("profile_pic")) {
                        userProfilePic = String.valueOf(jo.getString("profile_pic"));
                    }
                    Users temp = new Users();
                    temp.setUsername(userName);
                    temp.setLanguage(userLanguage);
                    temp.setProfile_pic(userProfilePic);
                    res.add(temp);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }
    public ArrayList<Users> ParseGetPlacesResponse(String jsonFile) {
        try {
            res = new ArrayList<>();
            f=true;
            JSONObject jsonObject = new JSONObject(jsonFile);
            if(!jsonObject.isNull("result")) {

                JSONArray ja = jsonObject.getJSONArray("result");

                for (int i = 0; i < ja.length(); i++) {
                    String userName ="",userProfilePic="",description="";
                    JSONObject jo = ja.getJSONObject(i);
                    if(!jo.isNull("name")) {
                        userName = String.valueOf(jo.getString("name"));
                    }


                    if(!jo.isNull("pic")) {
                        userProfilePic = String.valueOf(jo.getString("pic"));
                    }

                    if(!jo.isNull("description")) {
                        description = String.valueOf(jo.getString("description"));
                    }

                    Users temp = new Users();
                    temp.setUsername(userName);
                    temp.setProfile_pic(userProfilePic);
                    temp.setDescription(description);
                    res.add(temp);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }
    public ArrayList<Users> ParseCategoryResponse(String jsonFile) {
        try {
            res = new ArrayList<>();
            f=true;
            JSONObject jsonObject = new JSONObject(jsonFile);
            if(!jsonObject.isNull("result")) {

                JSONArray ja = jsonObject.getJSONArray("result");

                for (int i = 0; i < ja.length(); i++) {
                    String userName ="",userProfilePic="",id="";
                    JSONObject jo = ja.getJSONObject(i);
                    if(!jo.isNull("name")) {
                        userName = String.valueOf(jo.getString("name"));
                    }


                    if(!jo.isNull("picture")) {
                        userProfilePic = String.valueOf(jo.getString("picture"));
                    }


                    if(!jo.isNull("cat_id")){
                        id=String.valueOf(jo.getString("cat_id"));
                    }

                    Users temp = new Users();
                    temp.setUsername(userName);
                    temp.setProfile_pic(userProfilePic);
                    temp.setTourGuide_id(id);
                    res.add(temp);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return res;
    }
    public  String ParseRegisterResponse(String jsonFile){

        try {
            f=true;
            JSONObject jsonObject = new JSONObject(jsonFile);
            if(!jsonObject.isNull("result")) {
                JSONObject res = jsonObject.getJSONObject("result");
                // result.add(data);
                if(!res.isNull("success")) {
                    String data = res.getString("success");
                   ans = data;

                }


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ans;

    }
    public Users ParseFillProfileResponse(String jsonFile) {
        Users temp = null;
        try {
            res = new ArrayList<>();

            f = true;
            JSONObject jsonObject = new JSONObject(jsonFile);
            if (!jsonObject.isNull("result")) {

                JSONArray ja = jsonObject.getJSONArray("result");

                for (int i = 0; i < ja.length(); i++) {
                    String userName = "", userProfilePic = "", country = "", age = "";
                    JSONObject jo = ja.getJSONObject(i);
                    if (!jo.isNull("username")) {
                        userName = String.valueOf(jo.getString("username"));
                    }


                    if (!jo.isNull("profile_pic")) {
                        userProfilePic = String.valueOf(jo.getString("profile_pic"));
                    }

                    if (!jo.isNull("country")) {
                        country = String.valueOf(jo.getString("country"));
                    }

                    if (!jo.isNull("age")) {
                        age = String.valueOf(jo.getString("age"));
                    }


                    temp = new Users();
                    temp.setUsername(userName);
                    temp.setProfile_pic(userProfilePic);
                    temp.setCountry(country);
                    temp.setAge(age);


                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return temp;
    }
    public  String ParseAddMemoryResponse(String jsonFile){

        try {
            f=true;
            JSONObject jsonObject = new JSONObject(jsonFile);
            if(!jsonObject.isNull("result")) {
                JSONObject res = jsonObject.getJSONObject("result");
                // result.add(data);
                if(!res.isNull("success")) {
                    String data = res.getString("success");
                    ans = data;

                }


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ans;

    }
}
