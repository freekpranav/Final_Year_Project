package com.hoperaiser.act_it.Class;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hoperaiser.act_it.R;
import com.hoperaiser.act_it.Staff_details_mainActivity;
import com.hoperaiser.act_it.staff_info;

import java.util.List;



public class RecyclerViewAdapters1 extends RecyclerView.Adapter<RecyclerViewAdapters1.ViewHolder> {

    Context context;
    List<staff_info> MainImageUploadInfoList;
    String url;

    public RecyclerViewAdapters1(Context context, List<staff_info> TempList) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_staff_details, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        staff_info UploadInfo = MainImageUploadInfoList.get(position);

        holder.imageNameTextView.setText(UploadInfo.getName());
//        holder.registerno.setText(UploadInfo.getRegister_no());
//        url=UploadInfo.getProfile_image_url();
//        //Loading image from Glide library.
//        Glide.with(context).load(url).into(holder.profile);





    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView profile,more;
        public TextView imageNameTextView,registerno;
        public FloatingActionButton delete_image;
        FirebaseAuth mAuth;
        FirebaseDatabase rootnode;
        DatabaseReference reference,ref;



        public ViewHolder(View itemView) {
            super(itemView);



            imageNameTextView = (TextView) itemView.findViewById(R.id.name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    Context context=view.getContext();
                    Intent intent=new Intent(context, Staff_details_mainActivity.class);
                    intent.putExtra("name",MainImageUploadInfoList.get(position).getName());
                    context.startActivity(intent);
                }
            });


        }


    }
}
