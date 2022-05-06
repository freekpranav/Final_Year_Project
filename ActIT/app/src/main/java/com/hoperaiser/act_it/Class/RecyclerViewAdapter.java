package com.hoperaiser.act_it.Class;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hoperaiser.act_it.Notes;
import com.hoperaiser.act_it.R;
import com.hoperaiser.act_it.person;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    List<person> MainImageUploadInfoList;
    String url;

    public RecyclerViewAdapter(Context context, List<person> TempList) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.person, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        person UploadInfo = MainImageUploadInfoList.get(position);

        holder.staffname.setText(UploadInfo.getStaff_Name());
        holder.date.setText(UploadInfo.getDate_and_time());
        holder.title.setText(UploadInfo.getTitle());
        Toast.makeText(context, UploadInfo.getStaff_Name(), Toast.LENGTH_SHORT).show();
        //Loading image from Glide library.





    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        public TextView staffname,date,title;
        public Toast toast;


        public ViewHolder(View itemView) {
            super(itemView);


            staffname = (TextView) itemView.findViewById(R.id.staffname);
            date = (TextView) itemView.findViewById(R.id.date);
            title = (TextView) itemView.findViewById(R.id.anno_title);




        }


    }
}
