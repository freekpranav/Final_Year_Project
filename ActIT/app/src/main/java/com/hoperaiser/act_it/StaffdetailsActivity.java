package com.hoperaiser.act_it;

import androidx.annotation.NonNull;
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
import com.hoperaiser.act_it.Class.RecyclerViewAdapters;
import com.hoperaiser.act_it.Class.RecyclerViewAdapters1;

import java.util.ArrayList;
import java.util.List;

public class StaffdetailsActivity extends AppCompatActivity {

    // Creating DatabaseReference.
    DatabaseReference databaseReference;

    // Creating RecyclerView.
    RecyclerView recyclerView;

    // Creating RecyclerView.Adapter.
    RecyclerView.Adapter adapter ;

    // Creating Progress dialog
    ProgressDialog progressDialog;

    // Creating List of ImageUploadInfo class.
    List<staff_info> list = new ArrayList<staff_info>();
    final String Database_Path = "Staff";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staffdetails);

        // Assign activity this to progress dialog.
        progressDialog = new ProgressDialog(StaffdetailsActivity.this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading Staff Data ");

        // Assign id to RecyclerView.
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        // Setting RecyclerView size true.
        recyclerView.setHasFixedSize(true);

        // Setting RecyclerView layout as LinearLayout.
        recyclerView.setLayoutManager(new LinearLayoutManager(StaffdetailsActivity.this));

        list.clear();



        // Setting up Firebase image upload folder path in databaseReference.
        // The path is already defined in MainActivity.
        databaseReference = FirebaseDatabase.getInstance().getReference(Database_Path);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    staff_info ds = postSnapshot.getValue(staff_info.class);

                    list.add(ds);
                }

                adapter = new RecyclerViewAdapters1(getApplicationContext(), list);
                adapter.notifyDataSetChanged();

                recyclerView.setAdapter(adapter);

                // Hiding the progress dialog.
                progressDialog.dismiss();


    }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
