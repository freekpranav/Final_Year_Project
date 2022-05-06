package com.hoperaiser.act_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TimeTableActivity extends AppCompatActivity {

    ListView myPdfListView;
    DatabaseReference databaseReference;
    List<RetrivePdf> retrivePdfs;
    FloatingActionButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

        myPdfListView=(ListView)findViewById(R.id.myListView);
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                Intent i=new Intent(getApplicationContext(),ExploreActivity.class);
                startActivity(i);
                finish();



            }
        });
        retrivePdfs=new ArrayList<>();
        viewAllFiles();

        myPdfListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                RetrivePdf retrivePdf=retrivePdfs.get(position);

                Intent i=new Intent(Intent.ACTION_VIEW,Uri.parse(retrivePdf.getUrl()));
                startActivity(i);


            }
        });
    }

    private  void viewAllFiles(){
        databaseReference= FirebaseDatabase.getInstance().getReference("TimeTable");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot postSnapshot : snapshot.getChildren()){

                    RetrivePdf retrivePdf=postSnapshot.getValue(RetrivePdf.class);
                    retrivePdfs.add( retrivePdf);
                }

                String[] retrive=new String [retrivePdfs.size()];
                for(int i=0;i<retrive.length;i++){
                    retrive[i]=retrivePdfs.get(i).getYear();
                }

                ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,retrive){

                    @Override
                    public  View getView(int position, View convertView, ViewGroup parent){

                        View view=super.getView(position,convertView,parent);
                        TextView mytext=(TextView) view.findViewById(android.R.id.text1);
                        mytext.setTextColor(Color.BLACK);
                        mytext.setGravity(Gravity.CENTER);

                        return view;
                    }

                };
                myPdfListView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}