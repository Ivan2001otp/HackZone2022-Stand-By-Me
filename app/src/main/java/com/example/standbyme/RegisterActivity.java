package com.example.standbyme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.standbyme.Major.MajorActivity;
import com.example.standbyme.Model.Customer;
import com.example.standbyme.Utils.Utility;
import com.example.standbyme.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {
private ActivityRegisterBinding binding;
private FirebaseAuth auth;
private FirebaseDatabase database;


    @Override
    protected void onStart() {
        super.onStart();
        database = FirebaseDatabase.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        String email = binding.emailEt.getText().toString().trim();
        String password = binding.passwordEt.getText().toString().trim();
        String username = binding.usernameEt.getText().toString().trim();


        binding.loginBtn.setOnClickListener(v->{
            Log.d("Tag", "onCreate: login in register act");
            Intent loginIntent = new Intent(RegisterActivity.this,MainActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginIntent);
        });

        binding.RegisterBtn.setOnClickListener(v->{
            Log.d("Tag", "onCreate: register act");
            Utility.hideKeypad(v);
            if(binding.emailEt.getText().toString().isEmpty() ||  binding.passwordEt.getText().toString().isEmpty() || binding.usernameEt.getText().toString().isEmpty()){
                Toast.makeText(RegisterActivity.this,"Please fill in Credentials",Toast.LENGTH_SHORT)
                        .show();
            }else{
                auth.createUserWithEmailAndPassword(binding.emailEt.getText().toString().trim(),binding.passwordEt.getText().toString().trim())
                        .addOnCompleteListener(RegisterActivity.this, task -> {
                            if(task.isSuccessful()){
                                Customer customer = new Customer(binding.usernameEt.getText().toString(),
                                        binding.passwordEt.getText().toString().trim(),
                                        binding.emailEt.getText().toString().trim());
                                        Log.d("tag", "onCreate: inside");
                                        String uniq_id = Objects.requireNonNull(task.getResult().getUser()).getUid();
                                        database.getReference().child("CUSTOMER-USER")
                                                .child(uniq_id)
                                                .setValue(customer);

                                        Log.d("tag", "onCreate: inside");
                                        Intent majorIntent = new Intent(RegisterActivity.this, MajorActivity.class);
                                        majorIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        Log.d("tag", "onCreate: inside");

                                        startActivity(majorIntent);

                                Log.d("tag", "onComplete: registration successfull");
                                Toast.makeText(RegisterActivity.this,"Register successfull",Toast.LENGTH_SHORT)
                                        .show();
                            }else{
                                Log.d("tag", "onComplete: registration failed "+ Objects.requireNonNull(task.getException()).getMessage());
                            }
                        });
            }
        });


    }
}