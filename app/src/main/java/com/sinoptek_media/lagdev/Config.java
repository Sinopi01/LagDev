package com.sinoptek_media.lagdev;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static android.R.attr.key;


/**
 * Created by SINOPI on 3/6/2017.
 */


public class Config {
     //public static final String q="location:lagos+language:java";
    //private static final String key = "90feeb0d14baf2a493dc7fce23f141209f68e187";
    public static final String GITHUB_API_URL = "https://api.github.com/search/users?q=location:lagos+language:java";
    //public static final String GITHUB_API_URL = "https://api.github.com/users?q=";
    private AsyncHttpClient client;
    public static final String USER_NAME = "login";
    public static final String PROFILE_PHOTO = "avatar_url";
    public static final String PROFILE_URL = "html_url";

}
