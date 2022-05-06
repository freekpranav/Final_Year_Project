package com.hoperaiser.act_it_adminapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class NotesActivity extends AppCompatActivity {


    Spinner year_register,sem;
    String Selected_year,Selected_sem;
    Button choosepdf,Uploadpdf;
    private static final int PICK_FILE=1;
    ArrayList<Uri> FileList=new ArrayList<Uri>();
    TextInputEditText Subject_code;

    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);


        year_register=(Spinner)findViewById(R.id.year_register);
        sem=(Spinner)findViewById(R.id.semester);
        choosepdf=(Button)findViewById(R.id.Choosepdf);
        Uploadpdf=(Button)findViewById(R.id.Uploadpdf);
        Subject_code=(TextInputEditText)findViewById(R.id.Subject_code);
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");




        //Spinner year

        List<String> year_spinner=new ArrayList<>();
        year_spinner.add(0,"Select Year");
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



        //Spinner sem

        List<String> sem_spinner=new ArrayList<>();
        sem_spinner.add(0,"Select Semester");
        sem_spinner.add("I Semester");
        sem_spinner.add("II Semester");
        sem_spinner.add("III Semester");
        sem_spinner.add("IV Semester");
        sem_spinner.add("V Semester");
        sem_spinner.add("VI Semester");
        sem_spinner.add("VII Semester");
        sem_spinner.add("VIII Semester");

        ArrayAdapter<String> dataAdapters;
        dataAdapters=new ArrayAdapter(this,android.R.layout.simple_spinner_item,sem_spinner);
        dataAdapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sem.setAdapter(dataAdapters);
        sem.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Selected_sem=sem.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        choosepdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                startActivityForResult(intent, PICK_FILE);

            }
        });




        Uploadpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();

                String code=Subject_code.getText().toString();

                for(int j=0;j<FileList.size();j++){
                    Uri PerFile=FileList.get(j);
                    StorageReference folder= FirebaseStorage.getInstance().getReference().child("Notes");
                    StorageReference filename=folder.child("file"+PerFile.getLastPathSegment());

                    filename.putFile(PerFile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                            filename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Notes").child(code);
                                    HashMap<String,String>hashMap=new HashMap<>();
                                    hashMap.put("Year",String.valueOf(Selected_year));
                                    hashMap.put("Semester",String.valueOf(Selected_sem));
                                    hashMap.put("Subject_Code",String.valueOf(code));
                                    hashMap.put("link",String.valueOf(uri));
                                    databaseReference.push().setValue(hashMap);
                                    pd.dismiss();
                                    Toast.makeText(NotesActivity.this, "Succesfully uploded "+FileList.size()+"Files", Toast.LENGTH_LONG).show();
                                    FileList.clear();

                                }
                            });





                        }
                    });

                }





            }
        });





    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==PICK_FILE){
            if(resultCode==RESULT_OK){

                if(data.getClipData()!=null){


                    int count=data.getClipData().getItemCount();

                    int i=0;
                    while(i<count){

                        Uri File=data.getClipData().getItemAt(i).getUri();
                        FileList.add(File);
                        i++;




                    }
                    Toast.makeText(this, "You have Selected "+FileList.size()+"Files", Toast.LENGTH_LONG).show();



                }


            }
        }





















    }


}