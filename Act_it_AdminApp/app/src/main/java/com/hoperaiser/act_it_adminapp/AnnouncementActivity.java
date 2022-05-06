package com.hoperaiser.act_it_adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AnnouncementActivity extends AppCompatActivity {

    Spinner year_register;
    String Selected_year,StaffName;
    Button Uploadpdf;
    TextInputEditText Subject_code,Message;
    ProgressDialog pd;
    String datetostr;

    //Firebase
    FirebaseAuth mAuth;
    FirebaseDatabase rootnode;
    DatabaseReference reference,ref;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        year_register=(Spinner)findViewById(R.id.year_register);

        Uploadpdf=(Button)findViewById(R.id.Uploadpdf);
        Subject_code=(TextInputEditText)findViewById(R.id.Subject_code);
        Message=(TextInputEditText)findViewById(R.id.Message);
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");

        Date today=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        datetostr=format.format(today);



        //Spinner year

        List<String> year_spinner=new ArrayList<>();
        year_spinner.add(0,"Select Year");
        year_spinner.add("All year");
        year_spinner.add("I year");
        year_spinner.add("II year");
        year_spinner.add("III year");
        year_spinner.add("IV year");

        ArrayAdapter<String> dataAdapter;
        dataAdapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,year_spinner);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year_register.setAdapter(dataAdapter);
        year_register.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Selected_year=year_register.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //Firebase
        mAuth=FirebaseAuth.getInstance();
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("staff");
        uid=mAuth.getCurrentUser().getUid();


        Query query = reference.orderByChild("uid").equalTo(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {

                    update_profile  person = ds.getValue(update_profile.class);

                    StaffName = person.getName().toString();


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Uploadpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();

                String code=Subject_code.getText().toString();
                String message=Message.getText().toString();





                                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Announcement").child(Selected_year);
                                    HashMap<String,String> hashMap=new HashMap<>();
                                    hashMap.put("Staff_Name",String.valueOf(StaffName));
                                    hashMap.put("Date_and_time",String.valueOf(datetostr));
                                    hashMap.put("Year",String.valueOf(Selected_year));
                                    hashMap.put("Title",String.valueOf(code));
                                    hashMap.put("Message",String.valueOf(message));

                                    databaseReference.push().setValue(hashMap);
                                    pd.dismiss();
                                    Toast.makeText(AnnouncementActivity.this, "Announcement Updated", Toast.LENGTH_SHORT).show();


                                }
                            });





    }
}