package com.sinoptek_media.lagdev;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sinoptek_media.lagdev.R;

import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.sinoptek_media.lagdev.R.id.profile_url;


/**
 * Created by SINOPI on 3/6/2017.
 */

public class ProfileViewDesign extends AppCompatActivity{
    ImageView ShareButtonImage, profileImage;
    public String username;
    public String profileURL;
    public String profilePhotoUrl;
    TextView userNametxt, profileURLtxt;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_view);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        profileURL = intent.getStringExtra("profileUrl");
        profilePhotoUrl = intent.getStringExtra("profileImageUrl");
        ShareButtonImage = (ImageView) findViewById(R.id.share_profile);
        //Toast.makeText(this, username,Toast.LENGTH_LONG).show();
        userNametxt =(TextView)findViewById(R.id.user_name);
        profileURLtxt =(TextView)findViewById(R.id.github_profile_url);
        profileImage =(CircleImageView) findViewById(R.id.user_profile_photo);

        try {
            URL shareUrl = new URL(profileURL);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        userNametxt.setText(username);
        profileURLtxt.setText(profileURL);
        Glide.with(this).load(profilePhotoUrl).into(profileImage);



        ShareButtonImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Check out this awesome developer @ " + username+", "+ profileURL );
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        profileURLtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(profileURL));
                startActivity(i);
            }
        });
    }
}
