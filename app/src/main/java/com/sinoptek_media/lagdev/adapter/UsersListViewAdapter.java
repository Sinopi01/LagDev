package com.sinoptek_media.lagdev.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.sinoptek_media.lagdev.ProfileViewDesign;
import com.sinoptek_media.lagdev.R;
import com.sinoptek_media.lagdev.GithubUser;

import java.util.List;

/**
 * Created by SINOPI on 3/5/2017.
 */

public class UsersListViewAdapter extends RecyclerView.Adapter<UsersListViewAdapter.ViewHolder> {
    private Context context;
    public String UserNameString;
    public String UserProfileUrl;
    public String ProfileImageUrl;
    public List<GithubUser> userDataSet;
    public class ViewHolder extends RecyclerView.ViewHolder {

    public TextView userName;
    public ImageView profilePhoto;
    public TextView html_Url;
    public CardView userCardView;


        public ViewHolder(View v) {
            super(v);
            userName = (TextView) v.findViewById(R.id.user_name);
            profilePhoto = (ImageView) v.findViewById(R.id.user_photo);
            html_Url = (TextView) v.findViewById(R.id.profile_url);
            userCardView = (CardView)v.findViewById(R.id.user_card_view);


            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) userCardView.getTag();
                    UserNameString = userDataSet.get(pos).getUserName();
                    UserProfileUrl = userDataSet.get(pos).getProfileURL();
                    ProfileImageUrl = userDataSet.get(pos).getProfilePhoto();

                    Intent profileViewIntent = new Intent(v.getContext(), ProfileViewDesign.class);
                    profileViewIntent.putExtra("username", UserNameString);
                    profileViewIntent.putExtra("profileUrl", UserProfileUrl);
                    profileViewIntent.putExtra("profileImageUrl", ProfileImageUrl);
                    v.getContext().startActivity(profileViewIntent);
                }


            });

        }

    }
    public void add(int position, GithubUser user) {
        userDataSet.add(position, user);
        notifyItemInserted(position);
    }
    public void remove(GithubUser user) {
        int position = userDataSet.indexOf(user);
        userDataSet.remove(position);
        notifyItemRemoved(position);
    }
    public UsersListViewAdapter(List<GithubUser> userDataSet) {
       this.userDataSet = userDataSet;
    }
    @Override
    public UsersListViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.userName.setText(userDataSet.get(position).getUserName());
        Glide.with(holder.profilePhoto.getContext()).load(userDataSet.get(position).getProfilePhoto()).into(holder.profilePhoto);
        UserNameString = userDataSet.get(position).getUserName();
        UserProfileUrl = userDataSet.get(position).getProfileURL();
        ProfileImageUrl = userDataSet.get(position).getProfilePhoto();
        holder.userCardView.setTag(position);

    }
    @Override
    public int getItemCount() {
        return userDataSet.size();
    }

}
