package com.hoperaiser.act_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;
import com.hoperaiser.act_it.Class.Register;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    EditText registerno_register, name_register,dept_register,phoneno_register,parentsno_register,email_register,uniqueid_register,password_register,confrompassword_register;
    Button registerbtn;
    Spinner year_register;
    FloatingActionButton back;
    String Email_register,Password_register,Registerno_register,Name_register,Dept_register,Phoneno_register,Parentsno_register,Uniqueid_register,Confrompassword_register;
    String uid;
    String   Selected_year;
    FloatingActionButton homeback;

    ProgressDialog pd;

    //Firebase
    private FirebaseAuth mAuth;
    FirebaseDatabase root;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerno_register=(EditText)findViewById(R.id.registerno_register);
        name_register=(EditText)findViewById(R.id.name_register);
        year_register=(Spinner)findViewById(R.id.year_register);
        dept_register=(EditText)findViewById(R.id.Department_register);
        phoneno_register=(EditText)findViewById(R.id.phoneno_register);
        parentsno_register=(EditText)findViewById(R.id.parents_phoneno_register);
        email_register=(EditText)findViewById(R.id.emailid_register);
        uniqueid_register=(EditText)findViewById(R.id.uniqueid_register);
        password_register=(EditText)findViewById(R.id.password_register);
        confrompassword_register=(EditText)findViewById(R.id.confpassword_register);
        registerbtn=(Button)findViewById(R.id.registerbtn);
        back=(FloatingActionButton)findViewById(R.id.loginback);
        homeback=(FloatingActionButton)findViewById(R.id.loginback);
        pd = new ProgressDialog(this);
        pd.setMessage("Registering....");


        //Firebase
        mAuth = FirebaseAuth.getInstance();
        root = FirebaseDatabase.getInstance();
        reference = root.getReference("User");



        // Back

        homeback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
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






        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get value of edittext



try {



    Email_register = email_register.getText().toString().trim();
    Password_register = password_register.getText().toString().trim();
    Registerno_register = registerno_register.getText().toString().trim();
    Name_register = name_register.getText().toString().trim();
    Dept_register = dept_register.getText().toString().trim();
    Phoneno_register = phoneno_register.getText().toString().trim();
    Parentsno_register = parentsno_register.getText().toString().trim();
    Uniqueid_register = uniqueid_register.getText().toString().trim();
    Confrompassword_register = confrompassword_register.getText().toString().trim();


    if (Registerno_register.matches("")) {
        Toast.makeText(RegisterActivity.this, "Kindly Fill Register number", Toast.LENGTH_SHORT).show();
        return;


    } else if (Name_register.matches(" ")) {
        Toast.makeText(RegisterActivity.this, "Kindly Fill Name", Toast.LENGTH_SHORT).show();
        return;
    } else if (Dept_register.matches("")) {
        Toast.makeText(RegisterActivity.this, "Kindly Fill Department", Toast.LENGTH_SHORT).show();
        return;

    } else if (Phoneno_register.matches("")) {
        Toast.makeText(RegisterActivity.this, "Kindly Fill Phoneno", Toast.LENGTH_SHORT).show();
        return;


    } else if (Parentsno_register.matches("")) {
        Toast.makeText(RegisterActivity.this, "Kindly Fill Parents number", Toast.LENGTH_SHORT).show();
        return;


    } else if (Email_register.matches("")) {
        Toast.makeText(RegisterActivity.this, "Kindly Fill Email", Toast.LENGTH_SHORT).show();
        return;


    } else if (Uniqueid_register.matches("")) {
        Toast.makeText(RegisterActivity.this, "Kindly Fill Unique id ", Toast.LENGTH_SHORT).show();
        return;


    } else if (Password_register.matches("")) {
        Toast.makeText(RegisterActivity.this, "Kindly Fill Password", Toast.LENGTH_SHORT).show();
        return;


    } else if (Confrompassword_register.matches("")) {
        Toast.makeText(RegisterActivity.this, "Kindly Fill Password", Toast.LENGTH_SHORT).show();
        return;


    }
    else if(Password_register.length()<6  && Confrompassword_register.length()<6){
        Toast.makeText(RegisterActivity.this, "Password must be 6 chracter", Toast.LENGTH_SHORT).show();
        return;
    }
    if(Password_register.matches(Confrompassword_register)) {

        pd.show();
        mAuth.createUserWithEmailAndPassword(Email_register, Password_register)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            uid=mAuth.getCurrentUser().getUid();
                            Register u=new Register(uid,Name_register,Selected_year,Dept_register,Email_register,Phoneno_register,Parentsno_register,Registerno_register,Uniqueid_register,Password_register);

                            reference.child(uid).setValue(u).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        pd.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Signed in Succesfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                    }
                                }
                            });


                        } else {
                            pd.dismiss();

                            Toast.makeText(RegisterActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }else{
        Toast.makeText(RegisterActivity.this, "Password must be same.", Toast.LENGTH_SHORT).show();
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