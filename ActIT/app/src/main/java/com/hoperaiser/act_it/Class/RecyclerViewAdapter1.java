package com.hoperaiser.act_it.Class;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hoperaiser.act_it.Document;
import com.hoperaiser.act_it.Notes;
import com.hoperaiser.act_it.R;

import java.util.List;


public class RecyclerViewAdapter1 extends RecyclerView.Adapter<RecyclerViewAdapter1.ViewHolder> {

    Context context;
    List<Document> MainImageUploadInfoList;
    String url;

    public RecyclerViewAdapter1(Context context, List<Document> TempList) {

        this.MainImageUploadInfoList = TempList;

        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_documents, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Document UploadInfo = MainImageUploadInfoList.get(position);

        holder.imageNameTextView.setText(UploadInfo.getReg_no());
        holder.registerno.setText(UploadInfo.getDate_and_time());
        //Loading image from Glide library.





    }

    @Override
    public int getItemCount() {

        return MainImageUploadInfoList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {


        public TextView imageNameTextView,registerno;




        public ViewHolder(View itemView) {
            super(itemView);


            imageNameTextView = (TextView) itemView.findViewById(R.id.name);
            registerno = (TextView) itemView.findViewById(R.id.regno);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {




                    int position=getAdapterPosition();
                    Context context=view.getContext();
                    Intent i=new Intent();
                    i.setType(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(MainImageUploadInfoList.get(position).getUrl()));
                    context.startActivity(i);








                }
            });


        }


    }
}
