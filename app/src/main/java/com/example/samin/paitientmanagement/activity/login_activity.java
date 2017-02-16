package com.example.samin.paitientmanagement.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.samin.paitientmanagement.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Samin on 02-02-2017.
 */

public class login_activity  extends AppCompatActivity implements View.OnClickListener{

    private EditText et_email, et_pass;
    private Button loginButton;
    private ProgressDialog PGD;
    private FirebaseAuth firebaseAuth;

    @Override
    public void onCreate(Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        PGD = new ProgressDialog(this);
        firebaseAuth = FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser() != null)
        {
            //User Already logged In
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        et_email = (EditText) findViewById(R.id.input_Email);
        et_pass =(EditText) findViewById(R.id.input_Password);
        loginButton = (Button) findViewById(R.id.login_button);

        //On click listener for login button
        loginButton.setOnClickListener(this);

        //Change the Fonts of email and Password Test
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Bold.ttf");
        et_email.setTypeface(type);
        et_pass.setTypeface(type);

    }

    @Override
    public void onClick(View v) {
        if(v == loginButton)
        {
            userLogin();
        }

    }

    private void userLogin() {
        String email = et_email.getText().toString().trim();
        String pass = et_pass.getText().toString().trim();
        if(TextUtils.isEmpty(email)){
            //Email is Empty
            Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show();
            //stop the function for Executing further
            return;
        }
        if(TextUtils.isEmpty(pass)){
            //Password is Empty
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
            //stop the function for Executing further
            return;
        }
        PGD.setMessage("Please Wait...");
        PGD.show();

        firebaseAuth.signInWithEmailAndPassword(email,pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        PGD.dismiss();

                        if(task.isSuccessful()){
                            //Toast.makeText(login_activity.this, "Register Successfully. ", Toast.LENGTH_LONG).show();
                            //PGD.cancel();
                            //callLogin();
                            finish();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));


                        }else{
                            //Toast.makeText(login_activity.this, "Could Not Register, Try Again.", Toast.LENGTH_SHORT).show();
                            //PGD.cancel();
                        }

                    }
                });
    }


//    public void clickOn_LoginButton(View view) {
//        if(et_email.getText().toString().equals("admin") && et_pass.getText().toString().equals("admin"))
//        {
//
//            Toast.makeText(this, "LoggedIn", Toast.LENGTH_SHORT).show();
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            Intent i = new Intent(this,MainActivity.class);
//            startActivity(i);
//            finish();
//        }
//    }







    public void signup_form(View view) {
        Intent i = new Intent(this,Create_account.class);
        startActivity(i);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}
