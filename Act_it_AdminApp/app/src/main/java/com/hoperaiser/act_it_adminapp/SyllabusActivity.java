package com.hoperaiser.act_it_adminapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.transition.Explode;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class SyllabusActivity extends AppCompatActivity {


    Spinner year_register;
    String Selected_year;
    int PICK_IMAGE_REQUEST = 111;
    TextView Show_timetable;



    //Firebase
    FirebaseAuth mAuth;
    FirebaseDatabase rootnode;
    DatabaseReference reference,ref;
    Uri imguri;
    ProgressDialog pd;
    private StorageTask uploadTask;
    StorageReference mStorageRef;

    Button choosepdf,Uploadpdf;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);


        year_register=(Spinner)findViewById(R.id.year_register);
        choosepdf=(Button)findViewById(R.id.choosepdf);
        Uploadpdf=(Button)findViewById(R.id.Uploadpdf);






        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");


        mAuth=FirebaseAuth.getInstance();
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("Syllabus");
        mStorageRef = FirebaseStorage.getInstance().getReference("Syllabus Pdf");


        //Spinner year

        List<String> year_spinner=new ArrayList<>();
        year_spinner.add(0,"Select Semester");
        year_spinner.add("I Semester");
        year_spinner.add("II Semester");
        year_spinner.add("III Semester");
        year_spinner.add("IV Semester");
        year_spinner.add("V Semester");
        year_spinner.add("VI Semester");
        year_spinner.add("VII Semester");
        year_spinner.add("VIII Semester");

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

        choosepdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("application/pdf");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, "Select "), PICK_IMAGE_REQUEST);

            }
        });



        Uploadpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                if(uploadTask!=null && uploadTask.isInProgress()) {

                    Toast.makeText(SyllabusActivity.this, "upload in process", Toast.LENGTH_SHORT).show();
                }else {

                    Fileuploder();

                }
            }
        });



    }


    public  void Fileuploder(){
        pd.show();
        try {
            final StorageReference Ref = mStorageRef.child(System.currentTimeMillis() + "." + getExtension(imguri));
            Ref.putFile(imguri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {


                                    final Uri imgurl = uri;
                                    final String url = imgurl.toString();





                                    timetable u=new timetable(Selected_year,url);


                                    reference.child(Selected_year).setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {


                                                pd.dismiss();
                                                Toast.makeText(SyllabusActivity.this, "Updated succesfully", Toast.LENGTH_SHORT).show();

                                            }
                                        }
                                    });



                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(SyllabusActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }catch(Exception e){
            pd.dismiss();
            Toast.makeText(this, "Kindly select Pdf", Toast.LENGTH_SHORT).show();
        }
    }
    public String getExtension(Uri uri){
        ContentResolver cr=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imguri = data.getData();

            try {
                //getting image from gallery

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imguri);

                Toast.makeText(this, "file Selected Succesfully", Toast.LENGTH_SHORT).show();
                //Setting image to ImageView
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void onBackPressed() {

        Intent i =new Intent(SyllabusActivity.this, ExpolreActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }







}