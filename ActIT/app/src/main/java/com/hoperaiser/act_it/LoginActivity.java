package com.hoperaiser.act_it;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText register_edittext,password_edittext;
    Button loginbtn;
    TextView registertext,forgot_password;
    ProgressDialog pd;


    //Firebase

    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        register_edittext=(TextInputEditText)findViewById(R.id.registerno_login);
        password_edittext=(TextInputEditText)findViewById(R.id.password_login);
        loginbtn=(Button)findViewById(R.id.loginbtn_login);
        registertext=(TextView)findViewById(R.id.Register_text);
        forgot_password=(TextView)findViewById(R.id.forgot_password);
        pd = new ProgressDialog(this);
        pd.setMessage("Verifying....");


        //Firebase
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();






        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String re=register_edittext.getText().toString();
                String pass=password_edittext.getText().toString();

                if(re.matches("")&& pass.matches("")) {

                    Toast.makeText(LoginActivity.this, " Kindly fill All the field to login", Toast.LENGTH_SHORT).show();
                }else {
                    pd.show();

                    mAuth.signInWithEmailAndPassword(re, pass)
                            .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {

                                        pd.dismiss();
                                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(i);
                                    } else {
                                        pd.dismiss();

                                        Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                }


            }


        });


        registertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));


            }
        });

        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String re=register_edittext.getText().toString();
                if(re.matches("")){
                Toast.makeText(LoginActivity.this, "kindly fill", Toast.LENGTH_SHORT).show();
                        }else {
                    mAuth.sendPasswordResetEmail(re)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(LoginActivity.this, "Email sent.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }
                                }
                            });



        mAuthListner=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser user1=firebaseAuth.getCurrentUser();
                if(user1==null){

                }else{

                    Intent i=new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(i);
                    finish();
                }

            }
        };


        if(mUser!=null){
            Intent i=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
            finish();
        }


    }



}