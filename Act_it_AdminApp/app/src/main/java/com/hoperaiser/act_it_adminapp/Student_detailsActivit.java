package com.hoperaiser.act_it_adminapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hoperaiser.act_it_adminapp.Class.ImageUploadInfo;
import com.hoperaiser.act_it_adminapp.Class.RecyclerViewAdapter;
import com.hoperaiser.act_it_adminapp.Class.RecyclerViewAdapters;

import java.util.ArrayList;
import java.util.List;

public class Student_detailsActivit extends AppCompatActivity {

    String Selected_year;
    Spinner year_register;
    CardView button;

    // Creating DatabaseReference.
    DatabaseReference databaseReference;

    // Creating RecyclerView.
    RecyclerView recyclerView;

    // Creating RecyclerView.Adapter.
    RecyclerView.Adapter adapter ;

    // Creating Progress dialog
    ProgressDialog progressDialog;

    // Creating List of ImageUploadInfo class.
    List<Student_details_info> list = new ArrayList<Student_details_info>();
    public static final String Database_Path = "User";



    ImageView more;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);




        year_register=(Spinner)findViewById(R.id.year_register);
        button=(CardView)findViewById(R.id.button);



        // Assign id to RecyclerView.
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // Setting RecyclerView size true.
        recyclerView.setHasFixedSize(true);

        // Setting RecyclerView layout as LinearLayout.
        recyclerView.setLayoutManager(new LinearLayoutManager(Student_detailsActivit.this));

        // Assign activity this to progress dialog.
        progressDialog = new ProgressDialog(Student_detailsActivit.this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading Student Data ");

        // Showing progress dialog.




        //Spinner year

        List<String> year_spinner=new ArrayList<>();
        year_spinner.add(0,"I");
        year_spinner.add("II");
        year_spinner.add("III");
        year_spinner.add("IV");

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



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                progressDialog.show();
                list.clear();



                // Setting up Firebase image upload folder path in databaseReference.
        // The path is already defined in MainActivity.
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        Query q=databaseReference.orderByChild("year").equalTo(Selected_year);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    Student_details_info ds = postSnapshot.getValue(Student_details_info.class);

                    list.add(ds);
                }

                adapter = new RecyclerViewAdapters(getApplicationContext(), list);
                adapter.notifyDataSetChanged();

                recyclerView.setAdapter(adapter);

                // Hiding the progress dialog.
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // Hiding the progress dialog.
                progressDialog.dismiss();

            }
        });

            }
        });

    }
}