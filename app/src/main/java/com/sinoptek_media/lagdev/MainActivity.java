package com.sinoptek_media.lagdev;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sinoptek_media.lagdev.adapter.GithubUser;
import com.sinoptek_media.lagdev.adapter.UsersListViewAdapter;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        getUsers();
    }

    //This method will get user data from the web api
    public void getUsers(){
        JsonObjectRequest githubUserRequest = new JsonObjectRequest(Request.Method.GET, Config.GITHUB_API_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray githubUsersArray = response.getJSONArray("items");
                    parseData(githubUsersArray);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(githubUserRequest);
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