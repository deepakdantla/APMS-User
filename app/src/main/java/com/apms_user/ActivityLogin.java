package com.apms_user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class ActivityLogin extends AppCompatActivity {


    private FirebaseUser user;
    private FirebaseAuth auth;
    private EditText email, pswd;
    private TextView frgtPswd;
    private ProgressDialog p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.input_email);
        pswd = findViewById(R.id.input_password);

        auth = FirebaseAuth.getInstance();

        frgtPswd = findViewById(R.id.changePswd);

        p = new ProgressDialog(ActivityLogin.this);
        p.setCanceledOnTouchOutside(false);

        Button signIn = findViewById(R.id.btn_login);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String emailStr = email.getText().toString();
                String pswdStr = pswd.getText().toString();

                p.setMessage("Authenticating user...Please wait!");
                p.show();


                auth.signInWithEmailAndPassword(emailStr, pswdStr)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful())
                                {
                                    Intent i = new Intent(ActivityLogin.this, MainActivity.class);
                                    p.dismiss();
                                    startActivity(i);
                                }
                                else
                                {
                                    p.dismiss();
                                    Toast.makeText(ActivityLogin.this, "Sign in Failed", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }

        });

        final AlertDialog.Builder builder = new AlertDialog.Builder(ActivityLogin.this);
        LayoutInflater inflater = ActivityLogin.this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        builder.setView(dialogView);

        final EditText frgtMail = dialogView.findViewById(R.id.email_editText);
        Button b = dialogView.findViewById(R.id.submit_button);

        final AlertDialog alertDialog = builder.create();


        frgtPswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                alertDialog.show();


            }
        });


        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                p.setMessage("Sending reset mail...Please wait!");
                p.show();

                auth.sendPasswordResetEmail(frgtMail.getText().toString()).addOnCompleteListener(
                        new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    p.dismiss();
                                    Toast.makeText(ActivityLogin.this, "Password reset mail has been sent to your registered mail id", Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                }
                                else
                                {
                                    p.dismiss();
                                    Toast.makeText(ActivityLogin.this, "Problem in sending reset mail", Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                }
                            }
                        }
                );

            }
        });

    }

    boolean doubleBackToExitPressedOnce = false;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {


        if (doubleBackToExitPressedOnce) {
            finishAffinity();
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }
}
