package com.hoperaiser.act_it_adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ExpolreActivity extends AppCompatActivity {

    BottomNavigationView bottomBar;
    CardView card1,card2,card3,card4,card6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expolre);


        bottomBar=(BottomNavigationView)findViewById(R.id.bottomBar);
        card1=(CardView)findViewById(R.id.card1);
        card2=(CardView)findViewById(R.id.card2);
        card3=(CardView)findViewById(R.id.card3);
        card4=(CardView)findViewById(R.id.card4);
        card6=(CardView)findViewById(R.id.card6);






        // card1

        card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(ExpolreActivity.this,TimetableActivity.class);
                startActivity(i);

            }
        });


        // card2

        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(ExpolreActivity.this,Student_detailsActivit.class);
                startActivity(i);

            }
        });

        // card3

        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(ExpolreActivity.this,SyllabusActivity.class);
                startActivity(i);

            }
        });


        // card4

        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(ExpolreActivity.this,NotesActivity.class);
                startActivity(i);

            }
        });


        //card 5

        //card 6
        card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(ExpolreActivity.this,AnnouncementActivity.class);
                startActivity(i);

            }
        });




        bottomBar.getMenu().findItem(R.id.explore).setChecked(true);

        //Bottom


        bottomBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){

                    case R.id.home :

                        startActivity(new Intent(ExpolreActivity.this,MainActivity.class));

                        break;

                    case R.id.explore :
                        Toast.makeText(ExpolreActivity.this, "message", Toast.LENGTH_SHORT).show();

                        break;
                    case 3 :
                        Toast.makeText(ExpolreActivity.this, "noti", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.profile :

                        break;
                    default:
                        break;
                }

                return true;
            }
        });





    }
}