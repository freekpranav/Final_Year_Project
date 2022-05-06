package com.hoperaiser.act_it.Class;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.hoperaiser.act_it.Document;
import com.hoperaiser.act_it.R;
import com.hoperaiser.act_it.Syllabus;

import java.util.List;


public class syllabusRecyclerViewAdapter extends RecyclerView.Adapter<syllabusRecyclerViewAdapter.ViewHolder> {

    Context context;
    List<Syllabus> MainImageUploadInfoList;
    String url;

    public syllabusRecyclerViewAdapter(Context context, List<Syllabus> TempList) {

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
        Syllabus UploadInfo = MainImageUploadInfoList.get(position);

        holder.imageNameTextView.setText(UploadInfo.getYear());
        holder.registerno.setText(" ");
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
                    Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse(MainImageUploadInfoList.get(position).getUrl()));

                    context.startActivity(i);








                }
            });


        }


    }
}
