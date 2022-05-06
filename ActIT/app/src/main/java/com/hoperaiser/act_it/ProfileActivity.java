package com.hoperaiser.act_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {


    TextView register_no,year,name,email,phoneno,parents_phone,type,address,dob,blood_group;
    ImageView edit_profile;
    ImageView profile;
    Button Logout_profile;
    FloatingActionButton homeback;
    BottomNavigationView bottombar;



    //Firebase
    FirebaseAuth mAuth;
    FirebaseDatabase rootnode;
    DatabaseReference reference,ref;
    String uid;
    int PICK_IMAGE_REQUEST = 111;
    Uri imguri;
    ProgressDialog pd;
    BottomNavigationView bottomBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        profile=(ImageView)findViewById(R.id.profile_image);
        register_no=(TextView)findViewById(R.id.Regsiter_no);
        year=(TextView)findViewById(R.id.student_year);
        name=(TextView)findViewById(R.id.Account_name);
        email=(TextView)findViewById(R.id.Student_email);
        phoneno=(TextView)findViewById(R.id.students_phone);
        parents_phone=(TextView)findViewById(R.id.students_parents_no);
        type=(TextView)findViewById(R.id.Type);
        address=(TextView)findViewById(R.id.student_address);
        dob=(TextView)findViewById(R.id.DOB);
        blood_group=(TextView)findViewById(R.id.blood_group);
        Logout_profile=(Button)findViewById(R.id.Logout_profile);
        homeback=(FloatingActionButton)findViewById(R.id.homeback);
        bottombar=findViewById(R.id.bottomBar);





        //Floating button

        edit_profile=(ImageView)findViewById(R.id.edit_profile);



        //Firebase
        mAuth=FirebaseAuth.getInstance();
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("User");
        uid=mAuth.getCurrentUser().getUid();



        // Back

        homeback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(i);
                finish();
            }
        });



        // fetch data & display



        Query query = reference.orderByChild("uid").equalTo(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {

                    update_profile  person = ds.getValue(update_profile.class);

                    String retrivename = person.getName();
                    String retriveyear = person.getYear();
                    String retrive_registerno = person.getRegister_no();
                    String retrive_email = person.getEmail();
                    String retrive_phoneno = person.getPhoneno();
                    String retrive_parents_no = person.getParents_phoneno();
                    String profile_img_url=person.getProfile_image_url();

                            String Student_dob=person.getDob();
                    String student_address=person.getAddress();
                    String student_type=person.getType();
                    String Student_blood_group=person.getBloodgroup();

                    name.setText(retrivename);
                    year.setText(retriveyear +" Year");
                    register_no.setText(retrive_registerno);
                    email.setText(retrive_email);
                    phoneno.setText(retrive_phoneno);
                    parents_phone.setText(retrive_parents_no);
                    type.setText(student_type);
                    address.setText(student_address);
                    dob.setText(Student_dob);
                    blood_group.setText(Student_blood_group);


                    Glide.with(ProfileActivity.this).load(profile_img_url).into(profile);



                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        // Profile edit


        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(ProfileActivity.this,Profile_edit_Activity.class));





            }
        });

        // Logout


        Logout_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mAuth.signOut();
                Intent i=new Intent(ProfileActivity.this,LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);




            }
        });


//Bottom navigation bar



        bottombar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        break;

                    case R.id.explore:
                        startActivity(new Intent(getApplicationContext(),ExploreActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        overridePendingTransition(0,0);
                        break;

                }


                return true;
            }
        });




    }
}