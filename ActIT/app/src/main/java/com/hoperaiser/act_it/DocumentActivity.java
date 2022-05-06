package com.hoperaiser.act_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
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
import com.hoperaiser.act_it.Class.RecyclerViewAdapter1;
import com.hoperaiser.act_it.Class.RecyclerViewAdapters;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class DocumentActivity extends AppCompatActivity {
    EditText Reg_no_doc,Year;
    ImageView doc_back;
    Spinner Document_type;
    String Doc_year,uid,retrivename,year,uid1,datetostr;
    Button choosepdf,Uploadpdf;
    int PICK_IMAGE_REQUEST = 111;
    ArrayList<Uri> FileList=new ArrayList<Uri>();

    FloatingActionButton upload_doc;
    // Creating DatabaseReference.
    //Firebase
    FirebaseAuth mAuth;
    FirebaseDatabase rootnode;
    DatabaseReference reference,ref,databaseReference;
    Uri imguri;
    ProgressDialog pd;
    private StorageTask uploadTask;
    StorageReference mStorageRef;

    // Creating RecyclerView.
    RecyclerView recyclerView;

    // Creating RecyclerView.Adapter.
    RecyclerView.Adapter adapter ;

    // Creating Progress dialog
    ProgressDialog progressDialog;
    List<Document> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        Date today=new Date();
        SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
        datetostr=format.format(today);
        mAuth=FirebaseAuth.getInstance();
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("User");
        mStorageRef = FirebaseStorage.getInstance().getReference("Documents");

        upload_doc=(FloatingActionButton)findViewById(R.id.Document_compose);

        // Assign id to RecyclerView.
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView3);

        // Setting RecyclerView size true.
        recyclerView.setHasFixedSize(true);

        // Setting RecyclerView layout as LinearLayout.
        recyclerView.setLayoutManager(new LinearLayoutManager(DocumentActivity.this));

        // Assign activity this to progress dialog.
        progressDialog = new ProgressDialog(DocumentActivity.this);

        // Setting up message in Progress dialog.
        progressDialog.setMessage("Loading Documents... ");
        mAuth=FirebaseAuth.getInstance();
        uid=mAuth.getCurrentUser().getUid();

//        Query query = reference.orderByChild("uid").equalTo(uid);
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for (DataSnapshot ds : snapshot.getChildren()) {
//
//                    update_profile person = ds.getValue(update_profile.class);
//
//                    retrivename = person.getRegister_no();
//
//                }
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

retrivename="31281820503";

        progressDialog.show();
        list.clear();
        // Setting up Firebase image upload folder path in databaseReference.
        // The path is already defined in MainActivity.
        databaseReference = FirebaseDatabase.getInstance().getReference("Documents");

        Query q=databaseReference.orderByChild("reg_no").equalTo(retrivename);
        q.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    Document ds = postSnapshot.getValue(Document.class);

                    list.add(ds);
                }

                adapter = new RecyclerViewAdapter1(getApplicationContext(), list);
                adapter.notifyDataSetChanged();

                recyclerView.setAdapter(adapter);

                // Hiding the progress dialog.
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                // Hiding the progress dialog.
                progressDialog.dismiss();

            }
        });

        upload_doc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog dialogBuilder=new AlertDialog.Builder(DocumentActivity.this).create();
                LayoutInflater inflater=DocumentActivity.this.getLayoutInflater();
                View dialogview=inflater.inflate(R.layout.custome_document_upload,null);

                Reg_no_doc =(EditText) dialogview.findViewById(R.id.Reg_no_doc);
                Year=(EditText)dialogview.findViewById(R.id.Year);
                doc_back=(ImageView)dialogview.findViewById(R.id.doc_back);
                Document_type=(Spinner)dialogview.findViewById(R.id.Document_type);
                choosepdf=(Button)dialogview.findViewById(R.id.choose_doc);
                Uploadpdf=(Button)dialogview.findViewById(R.id.upload_doc);


                Query query = reference.orderByChild("uid").equalTo(uid);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for (DataSnapshot ds : snapshot.getChildren()) {

                            update_profile person = ds.getValue(update_profile.class);

                            retrivename = person.getRegister_no();
                            uid1 = person.getUid();
                            year = person.getYear();



                            Reg_no_doc.setText(retrivename);
                            Year.setText(year);


                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                doc_back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i=new Intent(getApplicationContext(),DocumentActivity.class);
                        startActivity(i);
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

//Spinner year

                List<String> doc_spinner=new ArrayList<>();
                doc_spinner.add(0,"Select Document Type");
                doc_spinner.add("10th");
                doc_spinner.add("12th");
                doc_spinner.add("SEM - I");
                doc_spinner.add("SEM - II");

                ArrayAdapter<String> dataAdapter;

                dataAdapter=new ArrayAdapter(DocumentActivity.this,android.R.layout.simple_spinner_item,doc_spinner);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                Document_type.setAdapter(dataAdapter);
                Document_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        Doc_year=Document_type.getSelectedItem().toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });

                //Reterive data


                Uploadpdf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        progressDialog.show();

                        if(uploadTask!=null && uploadTask.isInProgress()) {

                            Toast.makeText(DocumentActivity.this, "upload in process", Toast.LENGTH_SHORT).show();
                        }else {

                            Fileuploder();

                        }




                    }
                });



//
                dialogBuilder.setView(dialogview);
                dialogBuilder.show();


            }
        });

    }
    public  void Fileuploder(){
        progressDialog.show();
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





                                    Document u=new Document(uid1,url,retrivename,year,datetostr,Doc_year);
                                    rootnode = FirebaseDatabase.getInstance();
                                    ref = rootnode.getReference("Documents");
                                    ref.push().child(retrivename).setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {


                                                progressDialog.dismiss();
                                                Toast.makeText(DocumentActivity.this, "Updated succesfully", Toast.LENGTH_SHORT).show();

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
                            Toast.makeText(DocumentActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
        }catch(Exception e){
            progressDialog.dismiss();
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

        Intent i =new Intent(DocumentActivity.this, ExploreActivity.class);
        startActivity(i);
        finish();
        super.onBackPressed();
    }



}