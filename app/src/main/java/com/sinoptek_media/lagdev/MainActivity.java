package com.sinoptek_media.lagdev;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.sinoptek_media.lagdev.adapter.UsersListViewAdapter;
import com.sinoptek_media.lagdev.parser.UserJsonParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.android.volley.Request.Method.GET;
import static com.sinoptek_media.lagdev.Config.GITHUB_API_URL;
//import static com.sinoptek_media.lagdev.Config.q;

public class MainActivity extends AppCompatActivity {

    //Creating a List of Lagos Java GithubUsers
    public List<GithubUser> userDataSet;

    //Creating Views
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    JSONObject requestQuery;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         requestQuery = new JSONObject();
        try {
            requestQuery.put("location","lagos");
            requestQuery.put("language", "java");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //Initializing Views
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Initializing our users list
        userDataSet = new ArrayList<>();

        //Calling method to get data
        getData();
    }

        //This method will get data from the web api
    private void getData() {
        //Showing a progress dialog
        final ProgressDialog loading = ProgressDialog.show(this, "Loading Developers", "Please wait...", false, false);

        //Creating a json array request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(GITHUB_API_URL,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        //Dismissing progress dialog
                        loading.dismiss();

                        //calling method to parse json array
                        parseData(response);
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });


        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(jsonArrayRequest);
    }

    //This method will parse json data
    private void parseData(JSONArray array){
        for(int i = 0; i<array.length(); i++) {
        //for(int i = 0; i<138; i++) {

            GithubUser user = new GithubUser();
            JSONObject json = null;
            try {
                json = array.getJSONObject(i);
                user.setUserName(json.getString(Config.USER_NAME));
                user.setProfilePhoto(json.getString(Config.PROFILE_PHOTO));
                user.setProfileURL(json.getString(Config.PROFILE_URL));


            } catch (JSONException e) {
                e.printStackTrace();
            }
            userDataSet.add(user);
        }

        //initializing our adapter
          adapter = new UsersListViewAdapter(userDataSet);

        //Adding adapter to recyclerView
          recyclerView.setAdapter(adapter);
    }

}