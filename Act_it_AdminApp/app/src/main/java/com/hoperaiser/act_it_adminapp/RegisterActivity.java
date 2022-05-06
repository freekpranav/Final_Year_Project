package com.hoperaiser.act_it_adminapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hoperaiser.act_it_adminapp.Class.Register;

public class RegisterActivity extends AppCompatActivity {

    EditText  name_register,dept_register,phoneno_register,designation_register,email_register,password_register,confrompassword_register;
    Button registerbtn;
    FloatingActionButton back;
    String Email_register,Password_register,Name_register,Dept_register,Phoneno_register,Designation_register,Confrompassword_register;
    String uid;

    //Firebase
    private FirebaseAuth mAuth;
    FirebaseDatabase root;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name_register=(EditText)findViewById(R.id.name_register);
        dept_register=(EditText)findViewById(R.id.Department_register);
        phoneno_register=(EditText)findViewById(R.id.phoneno_register);
        designation_register=(EditText)findViewById(R.id.Designation_register);
        email_register=(EditText)findViewById(R.id.emailid_register);
        password_register=(EditText)findViewById(R.id.password_register);
        confrompassword_register=(EditText)findViewById(R.id.confpassword_register);
        registerbtn=(Button)findViewById(R.id.uploadpdf);
        back=(FloatingActionButton)findViewById(R.id.loginback);

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        root = FirebaseDatabase.getInstance();
        reference = root.getReference("Staff");









        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get value of edittext



try {



    Email_register = email_register.getText().toString().trim();
    Password_register = password_register.getText().toString().trim();
    Name_register = name_register.getText().toString().trim();
    Dept_register = dept_register.getText().toString().trim();
    Phoneno_register = phoneno_register.getText().toString().trim();
    Designation_register = designation_register.getText().toString().trim();
    Confrompassword_register = confrompassword_register.getText().toString().trim();


     if (Name_register.matches(" ")) {
        Toast.makeText(RegisterActivity.this, "Kindly Fill Name", Toast.LENGTH_SHORT).show();
        return;
    } else if (Dept_register.matches("")) {
        Toast.makeText(RegisterActivity.this, "Kindly Fill Department", Toast.LENGTH_SHORT).show();
        return;


    } else if (Designation_register.matches("")) {
         Toast.makeText(RegisterActivity.this, "Kindly Fill Designation", Toast.LENGTH_SHORT).show();
         return;

     }else if (Phoneno_register.matches("")) {
         Toast.makeText(RegisterActivity.this, "Kindly Fill Phoneno", Toast.LENGTH_SHORT).show();
         return;

     }
    else if (Email_register.matches("")) {
        Toast.makeText(RegisterActivity.this, "Kindly Fill Email", Toast.LENGTH_SHORT).show();
        return;


    }  else if (Password_register.matches("")) {
        Toast.makeText(RegisterActivity.this, "Kindly Fill Password", Toast.LENGTH_SHORT).show();
        return;


    } else if (Confrompassword_register.matches("")) {
        Toast.makeText(RegisterActivity.this, "Kindly Fill Password", Toast.LENGTH_SHORT).show();
        return;


    }
    else if(Password_register.length()<6 && Confrompassword_register.length()<6){
         Toast.makeText(RegisterActivity.this, "Password Must be above 6 character", Toast.LENGTH_SHORT).show();
         return;

     }
    else if(Phoneno_register.length()<10){
         Toast.makeText(RegisterActivity.this, "Phone no must be 10 charecter", Toast.LENGTH_SHORT).show();
         return;

     }

    if(Password_register.matches(Confrompassword_register)) {

        mAuth.createUserWithEmailAndPassword(Email_register, Password_register)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            uid=mAuth.getCurrentUser().getUid();
                            Register u=new Register(uid,Name_register,Dept_register,Email_register,Phoneno_register,Designation_register,Password_register);

                            reference.child(uid).setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(RegisterActivity.this, "Signed in Succesfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                    }
                                }
                            });


                        } else {
                            Toast.makeText(RegisterActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }else{
        Toast.makeText(RegisterActivity.this, "Passsword must be same", Toast.LENGTH_SHORT).show();
    }

}catch (Exception e){
    Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
}
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));

            }
        });










    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
        super.onBackPressed();
    }
}