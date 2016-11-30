package com.cochina.owner.lacochina;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private EditText name,lastName,password,email;

    // Authentication Firebase
    private FirebaseAuth mAuth;
    private FirebaseDatabase db;
    private DatabaseReference ref;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Instanciate Auth
        mAuth = FirebaseAuth.getInstance();
        db= FirebaseDatabase.getInstance();
        ref= db.getReference("Users");

        // Users Parameters
        name= (EditText) findViewById(R.id.nameEditText);
        lastName=(EditText) findViewById(R.id.lastNameEditText);
        password=(EditText) findViewById(R.id.passwordEditText);
        email=(EditText) findViewById(R.id.emailEditText);

    }


    public void addUsers(View v){
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Users user = new Users(name.getText().toString(), lastName.getText().toString(),email.getText().toString(), password.getText().toString());
                        ref.child(task.getResult().getUser().getUid()).setValue(user);
                        if (!task.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "nell", Toast.LENGTH_LONG).cancel();

                        }
                    }
                });
        Toast.makeText(this, "User Created", Toast.LENGTH_LONG).cancel();
        finish();

    }


}
