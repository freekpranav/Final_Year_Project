package com.hoperaiser.act_it;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BusrouteActivity extends AppCompatActivity {

    TextView route1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busroute);


        route1=(TextView)findViewById(R.id.route1);

        route1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri geo=Uri.parse("https://www.google.com/maps/dir/?api=1&origin=Thalan+kuppum,Tamil+Nadu&destination=Agni+College+of+Technology,Old+Mahabalipuram+Road,zthalambur,Chennai,Tamil+Nadu&waypoints=Ennore,Chennai,Tamil+Nadu | Ernavur,Ennore,Tamil+Nadu | Municipal+Corporation,Thiruvottiyur+High+Rd,Chennai&travelmode=driving");
                Intent intent=new Intent(Intent.ACTION_VIEW,geo);
                intent.setPackage("com.google.android.apps.maps");
                try{
                    startActivity(intent);
                }catch (ActivityNotFoundException ex){
                    try {
                        Intent unres = new Intent(Intent.ACTION_VIEW, geo);
                        startActivity(unres);
                    }catch (ActivityNotFoundException innerex) {
                        Toast.makeText(BusrouteActivity.this, "pls install map", Toast.LENGTH_SHORT).show();


                    }
                }


            }

        });





    }

    @Override
    public void onBackPressed () {
        Intent i=new Intent(BusrouteActivity.this,MainActivity.class);
        startActivity(i);
        finish();
    }
}