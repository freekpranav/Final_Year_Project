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
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.hoperaiser.act_it_adminapp.Class.Urlimage;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.Base64;

public class AddBannerActivity extends AppCompatActivity {

    Button chooseImg1,uploadimg1;
    int PICK_IMAGE_REQUEST = 111;
    String uid;
    ImageView one_image,BannerImage;


    // Firebase

    FirebaseDatabase rootnode;
    DatabaseReference reference,ref,reference_image;
    private FirebaseAuth mAuth;

    Uri imguri;
    ProgressDialog pd;
    private StorageTask uploadTask;
    StorageReference mStorageRef;
    public static String selectedcourse;












    // Creating button.
    Button DisplayImageButton;


    ProgressDialog progressDialog ;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_banner);

        chooseImg1=(Button)findViewById(R.id.Uploadoneimage);
        uploadimg1=(Button)findViewById(R.id.uploadimg1);
        one_image=(ImageView)findViewById(R.id.one_image);
        BannerImage=(ImageView)findViewById(R.id.BannerImage);
        DisplayImageButton = (Button)findViewById(R.id.DisplayImagesButton);



        DisplayImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AddBannerActivity.this, DisplayImagesActivity.class);
                startActivity(intent);

            }
        });

        //Firebase

        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("User");
        pd = new ProgressDialog(this);
        pd.setMessage("Uploading....");
        mStorageRef = FirebaseStorage.getInstance().getReference("Images");
        mAuth=FirebaseAuth.getInstance();








        //choseImage

        chooseImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);

            }
        });

        // choose image for 2







        //UploadImage


        uploadimg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                if(uploadTask!=null && uploadTask.isInProgress()) {

                    Toast.makeText(AddBannerActivity.this, "upload in process", Toast.LENGTH_SHORT).show();
                }else {

                    Fileuploder();

                }
            }
        });


        //uploadimage 2






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

                                    rootnode = FirebaseDatabase.getInstance();
                                    ref = rootnode.getReference("Banner");


                                    Urlimage u = new Urlimage(url);

                                    ref.push().setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {


                                                pd.dismiss();
                                                Toast.makeText(AddBannerActivity.this, "Banner image uploded succesfully", Toast.LENGTH_SHORT).show();

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
                            Toast.makeText(AddBannerActivity.this, "Failed", Toast.LENGTH_SHORT).show();
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
                one_image.setImageBitmap(bitmap);

                Toast.makeText(this, "Image Selected Succesfully", Toast.LENGTH_SHORT).show();
                //Setting image to ImageView
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void onBackPressed() {

        Intent i =new Intent(AddBannerActivity.this,MainActivity.class);
        startActivity(i);
        super.onBackPressed();
    }








}