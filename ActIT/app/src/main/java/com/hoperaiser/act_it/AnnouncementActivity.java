package com.hoperaiser.act_it;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hoperaiser.act_it.Class.RecyclerViewAdapter;
import com.hoperaiser.act_it.Class.RecyclerViewAdapters;

import java.util.ArrayList;
import java.util.List;


public class AnnouncementActivity extends AppCompatActivity {



    // Creating DatabaseReference.
    DatabaseReference databaseReference;

    // Creating RecyclerView.
    RecyclerView recyclerView1;

    // Creating RecyclerView.Adapter.
    RecyclerView.Adapter adapter ;

    // Creating Progress dialog
    ProgressDialog progressDialog;

    // Creating List of ImageUploadInfo class.
    List<person> list = new ArrayList<>();
    public static final String Database_Path = "Announcement";
    String Selected_year="I year";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement);

        // Assign id to RecyclerView.
        recyclerView1 = (RecyclerView) findViewById(R.id.recyclerView1);

        // Setting RecyclerView size true.
        recyclerView1.setHasFixedSize(true);

        // Setting RecyclerView layout as LinearLayout.
        recyclerView1.setLayoutManager(new LinearLayoutManager(AnnouncementActivity.this));

        // Assign activity this to progress dialog.
        progressDialog = new ProgressDialog(AnnouncementActivity.this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading Announcement ");

        // Showing progress dialog.
        progressDialog.show();
        list.clear();



        // Setting up Firebase image upload folder path in databaseReference.
        // The path is already defined in MainActivity.
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        Query q=databaseReference.orderByChild("Year").equalTo(Selected_year);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    person ds = postSnapshot.getValue(person.class);

                    list.add(ds);
                }

                adapter = new RecyclerViewAdapter(getApplicationContext(), list);
                adapter.notifyDataSetChanged();

                recyclerView1.setAdapter(adapter);

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
}