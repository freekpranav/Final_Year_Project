package com.hoperaiser.act_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {


    TextView profile_name;
    ImageSlider mainslider;
    CircleImageView profileimage;

    BottomNavigationView bottombar;



    //Firebase
    FirebaseAuth mAuth;
    FirebaseDatabase rootnode;
    DatabaseReference reference,ref;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        profileimage=(CircleImageView) findViewById(R.id.profileimage);
        profile_name=(TextView)findViewById(R.id.Account_name);
        mainslider=(ImageSlider)findViewById(R.id.image_slider);
        final List<SlideModel> remoteimages=new ArrayList<>();


        //Firebase
        mAuth=FirebaseAuth.getInstance();
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("User");
        uid=mAuth.getCurrentUser().getUid();

profileimage.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(getApplicationContext(),ExploreActivity.class));
        overridePendingTransition(0,0);
    }
});

        bottombar=findViewById(R.id.botttombar);

        bottombar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
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





        // Display name


        Query query = reference.orderByChild("uid").equalTo(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {

                   display_name  person = ds.getValue(display_name.class);

                    String retrivename = person.getName();
                    String imgurl=person.getImgurl();





                    profile_name.setText("Hi "+retrivename);



                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // Images fetch foe image slider

        ref = rootnode.getReference("Banner");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {

                   remoteimages.add(new SlideModel(ds.child("imageURL").getValue().toString(), ScaleTypes.FIT));

                }
                mainslider.setImageList(remoteimages,ScaleTypes.FIT);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }
}