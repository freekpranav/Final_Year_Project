
package com.hoperaiser.act_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_edit_Activity extends AppCompatActivity {



    //Firebase
    FirebaseAuth mAuth;
    FirebaseDatabase rootnode;
    DatabaseReference reference,ref;
    String uid;
    int PICK_IMAGE_REQUEST = 111;
    Uri imguri;
    ProgressDialog pd;
    private StorageTask uploadTask;
    StorageReference mStorageRef;

    CircleImageView profile_image;
    EditText Name,Register_no,Email,Parents_Phoneno,Phoneno,Student_address,dob,bloodgrp;
    Spinner type_student,Year_text;
    Button update;
    String Selected_type,Selected_year,datetostr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit_);



         profile_image=(CircleImageView) findViewById(R.id.profile_image);
         Name =(EditText)  findViewById(R.id.Name);
         Register_no =(EditText)  findViewById(R.id.Register_no);
         Year_text=(Spinner) findViewById(R.id.Year);
         Email=(EditText) findViewById(R.id.Email);
         Parents_Phoneno=(EditText) findViewById(R.id.Parents_phone_no);
         Phoneno=(EditText) findViewById(R.id.Phone_no);
         Student_address=(EditText) findViewById(R.id.Address);
         dob=(EditText) findViewById(R.id.dob_update);
         bloodgrp=(EditText) findViewById(R.id.blood_group_update);
         type_student=(Spinner) findViewById(R.id.Student_type);
         update=(Button) findViewById(R.id.update);

        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");


        mAuth=FirebaseAuth.getInstance();
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("User");
        uid=mAuth.getCurrentUser().getUid();
        mStorageRef = FirebaseStorage.getInstance().getReference("Profile Images");



        //fetch data & display data in edittext

        Query query = reference.orderByChild("uid").equalTo(uid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()) {

                    update_profile  person = ds.getValue(update_profile.class);

                    String retrivename = person.getName();
                    String retriveyear = person.getYear().toString();
                    String retrive_registerno = person.getRegister_no();
                    String retrive_email = person.getEmail();
                    String retrive_phoneno = person.getPhoneno();
                    String retrive_parents_no = person.getParents_phoneno();
                    String profile_img_url=person.getProfile_image_url();

                    String Student_dob=person.getDob();
                    String student_address=person.getAddress();
                    String student_type=person.getType();
                    String Student_blood_group=person.getBloodgroup();


                    Name .setText(retrivename);
                    Register_no.setText(retrive_registerno);
                    Email.setText(retrive_email);
                    Phoneno.setText(retrive_phoneno);
                    Parents_Phoneno.setText(retrive_parents_no);

                    Student_address.setText(student_address);
                    dob.setText(Student_dob);
                    bloodgrp.setText(Student_blood_group);





                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        //Spinner year

        List<String> year_spinner=new ArrayList<>();
        year_spinner.add(0,"Select Year");
        year_spinner.add("I");
        year_spinner.add("II");
        year_spinner.add("III");
        year_spinner.add("IV");

        ArrayAdapter<String> dataAdapter;

        dataAdapter=new ArrayAdapter(Profile_edit_Activity.this,android.R.layout.simple_spinner_item,year_spinner);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Year_text.setAdapter(dataAdapter);
        Year_text.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                   Selected_year=Year_text.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //Spinner type

        List<String> type_spinner=new ArrayList<>();
        type_spinner.add(0,"Select Type");
        type_spinner.add("Dayscholre");
        type_spinner.add("Hosteller");


        ArrayAdapter<String> dataAdapters;

        dataAdapters=new ArrayAdapter(Profile_edit_Activity.this,android.R.layout.simple_spinner_item,type_spinner);
        dataAdapters.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_student.setAdapter(dataAdapters);
        type_student.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Selected_type=type_student.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        //profile image

        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);



            }
        });


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pd.show();
                if(uploadTask!=null && uploadTask.isInProgress()) {

                    Toast.makeText(Profile_edit_Activity.this, "upload in process", Toast.LENGTH_SHORT).show();
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
                                    final String profile_image_url = imgurl.toString();


                                    rootnode = FirebaseDatabase.getInstance();
                                    ref = rootnode.getReference("User");

                                    String uname = Name.getText().toString();
                                    String uregisterno = Register_no.getText().toString();
                                    String uparents_phoneno = Parents_Phoneno.getText().toString();
                                    String uemail = Email.getText().toString();
                                    String uphone = Phoneno.getText().toString();
                                    String uaddress = Student_address.getText().toString();
                                    String udob = dob.getText().toString();
                                    String ubloodbgroup = bloodgrp.getText().toString();
                                    if (uname.isEmpty()) {
                                        pd.dismiss();
                                        Name.setError("Enter Name...");
                                        Name.requestFocus();

                                    } else if (uregisterno.isEmpty()) {
                                        pd.dismiss();
                                        Register_no.setError("Enter Register number...");
                                        Register_no.requestFocus();
                                    } else if (uparents_phoneno.isEmpty()) {
                                        pd.dismiss();

                                        Parents_Phoneno.setError("Enter Parents number...");
                                        Parents_Phoneno.requestFocus();
                                    } else if (uaddress.isEmpty()) {
                                        pd.dismiss();

                                        Student_address.setError("Enter Address...");
                                        Student_address.requestFocus();
                                    } else if (uemail.isEmpty()) {
                                        pd.dismiss();
                                        Email.setError("Enter Email...");
                                        Email.requestFocus();
                                    } else if (uphone.isEmpty()) {
                                        pd.dismiss();

                                        Phoneno.setError("Enter Batch No...");
                                        Phoneno.requestFocus();
                                    } else if (udob.isEmpty()) {
                                        pd.dismiss();

                                        dob.setError("Enter Batch No...");
                                        dob.requestFocus();
                                    } else if (ubloodbgroup.isEmpty()) {
                                        pd.dismiss();

                                        bloodgrp.setError("Enter Batch No...");
                                        bloodgrp.requestFocus();
                                    } else {

                                        update_profile u = new update_profile(uid, uname, uregisterno, Selected_year, udob, ubloodbgroup, uemail, uphone, uparents_phoneno, Selected_type, uaddress, profile_image_url);

                                        rootnode = FirebaseDatabase.getInstance();
                                        reference = rootnode.getReference("User");
                                        reference.child(uid).setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {


                                                    pd.dismiss();
                                                    Toast.makeText(Profile_edit_Activity.this, "Updated succesfully", Toast.LENGTH_SHORT).show();
                                                    Intent i = new Intent(Profile_edit_Activity.this, ProfileActivity.class);
                                                    startActivity(i);
                                                    finish();
                                                }
                                            }
                                        });


                                    }
                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(Profile_edit_Activity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }catch(Exception e){
            pd.dismiss();
            Toast.makeText(this, "Kindly select Image", Toast.LENGTH_SHORT).show();
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
                profile_image.setImageBitmap(bitmap);

                Toast.makeText(this, "Image Selected Succesfully", Toast.LENGTH_SHORT).show();
                //Setting image to ImageView
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void onBackPressed() {

        Intent i =new Intent(Profile_edit_Activity.this,ProfileActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }




}




