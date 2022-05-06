package com.hoperaiser.act_it_adminapp.Class;

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
import com.hoperaiser.act_it_adminapp.AddBannerActivity;
import com.hoperaiser.act_it_adminapp.Profile_StudentActivity;
import com.hoperaiser.act_it_adminapp.R;
import com.hoperaiser.act_it_adminapp.Student_details_info;

import java.util.List;



public class RecyclerViewAdapters extends RecyclerView.Adapter<RecyclerViewAdapters.ViewHolder> {

    Context context;
    List<Student_details_info> MainImageUploadInfoList;
    String url;

    public RecyclerViewAdapters(Context context, List<Student_details_info> TempList) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_student_details, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Student_details_info UploadInfo = MainImageUploadInfoList.get(position);

        holder.imageNameTextView.setText(UploadInfo.getName());
        holder.registerno.setText(UploadInfo.getRegister_no());
        url=UploadInfo.getProfile_image_url();
        //Loading image from Glide library.
        Glide.with(context).load(url).into(holder.profile);





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

            profile = (ImageView) itemView.findViewById(R.id.profile_image);
            more = (ImageView) itemView.findViewById(R.id.more);

            imageNameTextView = (TextView) itemView.findViewById(R.id.name);
            registerno = (TextView) itemView.findViewById(R.id.regno);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    Context context=view.getContext();
                    Intent intent=new Intent(context, Profile_StudentActivity.class);
                    intent.putExtra("regno",MainImageUploadInfoList.get(position).getRegister_no());
                    context.startActivity(intent);
                }
            });


        }


    }
}
