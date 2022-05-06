package com.hoperaiser.act_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Staff_details_mainActivity extends AppCompatActivity {

TextView name,des,phone,email,name1;
ImageView call;
    String retrivephone;

    // Firebase
    FirebaseAuth mAuth;
    FirebaseDatabase rootnode;
    DatabaseReference reference,ref;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_details_main);
        name=(TextView)findViewById(R.id.regno);
        name1=(TextView)findViewById(R.id.Student_name);
        des=(TextView)findViewById(R.id.Student_year);
        phone=(TextView)findViewById(R.id.Student_phone);
        email=(TextView)findViewById(R.id.Student_email);

        call=(ImageView)findViewById(R.id.call);


        //Firebase
        mAuth=FirebaseAuth.getInstance();
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("Staff");



        Intent i=getIntent();
        String reg=i.getStringExtra("name");
        name.setText(reg);


        Query query = reference.orderByChild("name").equalTo(reg);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {

                    staff_info  person = ds.getValue(staff_info.class);

                    String retrivename = person.getName();
                  String retrivedes=person.getDesignation();
                   retrivephone=person.getPhonenumber();
                   String retriveemail=person.getEmail();
                    name.setText(retrivename);
                    name1.setText(retrivename);
                    des.setText(retrivedes);
                    phone.setText(retrivephone);
                    email.setText(retriveemail);




                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

// Student call

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callintent=new Intent(Intent.ACTION_DIAL);
                String p="tel:"+retrivephone;
                callintent.setData(Uri.parse(p));
                startActivity(callintent);


            }
        });

    }
}