package com.example.owner.lacochina;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;
import com.facebook.FacebookSdk;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {

    private EditText email,password;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private LoginButton boton_facebbok;
    private CallbackManager callbackManager;

    private FirebaseDatabase database;
    private DatabaseReference ref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        database = FirebaseDatabase.getInstance();
        ref = database.getReference("Users");

        callbackManager = CallbackManager.Factory.create();
        boton_facebbok = (LoginButton) findViewById(R.id.login_facebbok);
        boton_facebbok.setReadPermissions("email", "public_profile");

        email=(EditText) findViewById(R.id.emailLogin);
        password=(EditText) findViewById(R.id.passwordLogin);

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("Usuario","onAuthStateChanged:signed_in:" + user.getUid());
                    startActivity(new Intent(Login.this, Menu_Principal.class));
                } else {
                    // User is signed out
                    Log.d("Usuario","onAuthStateChanged:signed_out");
                }
            }
        };

        boton_facebbok.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });

    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Users usuario = new Users(task.getResult().getUser().getDisplayName(), "",task.getResult().getUser().getEmail(),"");
                        ref.child(task.getResult().getUser().getUid()).setValue(usuario);
                        if (!task.isSuccessful()) {
                            //Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    protected void onStop(){
        super.onStop();
        if(mAuthListener != null){
            mAuth.removeAuthStateListener(mAuthListener);
        }
        mAuth.signOut();
        LoginManager.getInstance().logOut();
    }




    public void changeSignIn(View v){


        Intent i = new Intent(this,RegisterActivity.class);
        startActivity(i);

        mAuth = FirebaseAuth.getInstance();


    }
    public void Login(View v){
        if(TextUtils.isEmpty(email.getText().toString())){
            Toast.makeText(this,"Correo Vacio",Toast.LENGTH_SHORT).show();
        }else{
            if(TextUtils.isEmpty(password.getText().toString())){
                Toast.makeText(this,"Constraseña vacia",Toast.LENGTH_LONG).show();
            }else{
                mAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(Login.this,"Correo o Contraseña no valida",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        }
    }

    public void changeActivity(){
        Intent intent = new Intent(this,MiMapa.class);
        startActivity(intent);
    }

}
