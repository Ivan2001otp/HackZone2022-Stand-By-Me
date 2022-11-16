package com.example.standbyme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.standbyme.Major.MajorActivity;
import com.example.standbyme.Utils.Utility;
import com.example.standbyme.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
private ActivityMainBinding binding;
private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();




        binding.loginBtn.setOnClickListener(view -> {

            Utility.hideKeypad(view);
            Log.d("tag", "onClick: login act");
            if(!binding.usernameEt.getText().toString().isEmpty()|| !binding.passwordEt.getText().toString().isEmpty()){
                Log.d("tag", "onCreate: login act --  ");
                auth.signInWithEmailAndPassword(binding.usernameEt.getText().toString().trim(),binding.passwordEt.getText().toString().trim())
                        .addOnCompleteListener(MainActivity.this, task -> {
                            if(task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                Intent majorIntent = new Intent(MainActivity.this, MajorActivity.class);
                                startActivity(majorIntent);
                                finish();
                            }else{
                                Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }else{
                Log.d("tag", "onCreate: login act stop");
                Toast.makeText(MainActivity.this,"Please fill credentials",Toast.LENGTH_SHORT)
                        .show();
            }
        });

        binding.RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("tag", "onClick: register act");
                Intent registerIntent = new Intent(MainActivity.this,RegisterActivity.class);
                registerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(registerIntent);
            }
        });



    }
}