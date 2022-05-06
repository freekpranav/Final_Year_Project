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

import java.util.List;


public class RecyclerViewAdapters extends RecyclerView.Adapter<RecyclerViewAdapters.ViewHolder> {

    Context context;
    List<Notes> MainImageUploadInfoList;
    String url;

    public RecyclerViewAdapters(Context context, List<Notes> TempList) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_notes, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Notes UploadInfo = MainImageUploadInfoList.get(position);
        holder.imageNameTextView.setText(UploadInfo.getSemester());
        holder.registerno.setText(UploadInfo.getSemester());
        holder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context=view.getContext();
                Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse(UploadInfo.getLink()));
                context.startActivity(i);
            }
        });



    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView profile,more;
        public TextView imageNameTextView,registerno;



        public ViewHolder(View itemView) {
            super(itemView);

            profile = (ImageView) itemView.findViewById(R.id.profile_image);
            more = (ImageView) itemView.findViewById(R.id.more);

            imageNameTextView = (TextView) itemView.findViewById(R.id.name);
            registerno = (TextView) itemView.findViewById(R.id.regno);
            more=itemView.findViewById(R.id.more);

//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Context context=view.getContext();
//                    int position=getAdapterPosition();
//
//                    Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse(MainImageUploadInfoList.get(position).getLink()));
//                    context.startActivity(i);
//
//                }
//            });


        }


    }
}
