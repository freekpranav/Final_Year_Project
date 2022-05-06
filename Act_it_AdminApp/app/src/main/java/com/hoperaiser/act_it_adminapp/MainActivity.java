package com.hoperaiser.act_it_adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextView Student_count,Staff_Count;
    FloatingActionButton manageapp_go;
    BottomNavigationView bottomBar;


    //Firebase
    FirebaseAuth mAuth;
    FirebaseDatabase rootnode;
    DatabaseReference reference,ref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Student_count=(TextView)findViewById(R.id.Student_count);
        Staff_Count=(TextView)findViewById(R.id.Staff_Count);
        manageapp_go=(FloatingActionButton)findViewById(R.id.manageapp_go);
        bottomBar=(BottomNavigationView)findViewById(R.id.bottomBar);



        //Firebase
        mAuth=FirebaseAuth.getInstance();
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("User");





        //bottom
        bottomBar.getMenu().findItem(R.id.home).setChecked(true);


        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){

                    case R.id.home :

                        Toast.makeText(MainActivity.this, "home", Toast.LENGTH_SHORT).show();
                        break;

                    case  R.id.explore  :
                        startActivity(new Intent(MainActivity.this,ExpolreActivity.class));


                        break;
                    case 3 :
                        Toast.makeText(MainActivity.this, "noti", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.profile :
                        startActivity(new Intent(MainActivity.this,ProfileActivity.class));



                        break;
                    default:
                        break;
                }

                return true;
            }
        });






        //Retrive count of staff
        ref = rootnode.getReference("Staff");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                  long Student_value=snapshot.getChildrenCount();
                  String lv=Long.toString(Student_value);
                Staff_Count.setText(lv);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Retrive count of Student

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                long Student_value=snapshot.getChildrenCount();
                String lv=Long.toString(Student_value);
                Student_count.setText(lv);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Common floating button

        manageapp_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,AddBannerActivity.class);
                startActivity(i);
            }
        });




    }

}