package com.sinoptek_media.lagdev.parser;

import com.sinoptek_media.lagdev.GithubUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SINOPI on 3/5/2017.
 */

public class UserJsonParser {
    public static String[] userNames;
    public static String[] profileURLS;
    public static String[] profilePhotoURLS;

    private JSONArray users = null;
    List<GithubUser> Users;
    private String json;

    public void userJsonParser(String json){

        this.json = json;

    }

    public void userJsonParser(){
        JSONObject jsonObject=null;

        try {

            users = new JSONArray(json);


            userNames = new String[users.length()];
            profileURLS = new String[users.length()];
            profilePhotoURLS = new String[users.length()];
            Users = new ArrayList<GithubUser>();



            for(int i=0;i< users.length();i++){
                GithubUser githubUserObject =  new GithubUser();

                jsonObject = users.getJSONObject(i);

                userNames[i] = jsonObject.getString("login");
                profileURLS[i] = jsonObject.getString("html_url");
                profilePhotoURLS[i] = jsonObject.getString("avatar_url");

                githubUserObject.setUserName(userNames[i]);
                githubUserObject.setProfileURL(profileURLS[i]);
                githubUserObject.setProfilePhoto(profilePhotoURLS[i]);
                Users.add(githubUserObject);



            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public List<GithubUser> getGithubUsers()
    {
        //function to return the final populated list
        return Users;
    }




}
