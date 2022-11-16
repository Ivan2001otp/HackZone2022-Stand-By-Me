package com.example.standbyme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.standbyme.databinding.ActivityVideoChatBinding;

import java.net.MalformedURLException;
import java.net.URL;

public class VideoChatActivity extends AppCompatActivity {
private ActivityVideoChatBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding = ActivityVideoChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        URL serverURL;
        try{
            serverURL = new URL("https://meet.jit.si");
        }catch(MalformedURLException e){
            e.printStackTrace();
        }




    }
}