package com.hoperaiser.act_it_adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_StudentActivity extends AppCompatActivity {

    TextView regno,Student_name,Student_year,Student_phone,Parent_phone,Student_blood,Student_email,Student_Address,Student_type,Student_dob;
    CircleImageView profile_image;
    ImageView call,parent_call;
    String retrive_phoneno,retrive_parents_no;

// Firebase
    FirebaseAuth mAuth;
    FirebaseDatabase rootnode;
    DatabaseReference reference,ref;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile__student);

        regno=(TextView)findViewById(R.id.regno);
        Student_name=(TextView)findViewById(R.id.Student_name);
        Student_year=(TextView)findViewById(R.id.Student_year);
        Student_phone=(TextView)findViewById(R.id.Student_phone);
        Parent_phone=(TextView)findViewById(R.id.Student_parent_phone);
        Student_blood=(TextView)findViewById(R.id.Student_blood);
        Student_email=(TextView)findViewById(R.id.Student_email);
        Student_Address=(TextView)findViewById(R.id.Student_Address);
        Student_type=(TextView)findViewById(R.id.Student_type);
        Student_dob=(TextView)findViewById(R.id.Student_dob);
        profile_image=(CircleImageView)findViewById(R.id.profile_image);
        call=(ImageView)findViewById(R.id.call);
        parent_call=(ImageView)findViewById(R.id.parent_call);



        //Firebase
        mAuth=FirebaseAuth.getInstance();
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("User");



        Intent i=getIntent();
        String reg=i.getStringExtra("regno");
        regno.setText(reg);



        Query query = reference.orderByChild("register_no").equalTo(reg);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {

                    update_profile  person = ds.getValue(update_profile.class);

                    String retrivename = person.getName();
                    String retriveyear = person.getYear();
                    String retrive_registerno = person.getRegister_no();
                    String retrive_email = person.getEmail();
                     retrive_phoneno = person.getPhoneno();
                     retrive_parents_no = person.getParents_phoneno();
                    String profile_img_url=person.getProfile_image_url();

                    String Student_dobirth=person.getDob();
                    String student_address=person.getAddress();
                    String student_type=person.getType();
                    String Student_blood_group=person.getBloodgroup();

                    Student_name.setText(retrivename);
                    Student_year.setText(retriveyear +" Year");
                    Student_email.setText(retrive_email);
                    Student_phone.setText(retrive_phoneno);
                    Parent_phone.setText(retrive_parents_no);
                    Student_type.setText(student_type);
                    Student_Address.setText(student_address);
                    Student_dob.setText(Student_dobirth);
                    Student_blood.setText(Student_blood_group);


                    Glide.with(Profile_StudentActivity.this).load(profile_img_url).into(profile_image);



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
                String p="tel:"+retrive_phoneno;
                callintent.setData(Uri.parse(p));
                startActivity(callintent);


            }
        });

// Parent call
        parent_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callintent=new Intent(Intent.ACTION_DIAL);
                String p="tel:"+retrive_parents_no;
                callintent.setData(Uri.parse(p));
                startActivity(callintent);


            }
        });

    }
}