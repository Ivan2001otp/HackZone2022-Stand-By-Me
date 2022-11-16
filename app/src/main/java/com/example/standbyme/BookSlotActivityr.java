package com.example.standbyme;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.standbyme.databinding.ActivityBookSlotActivityrBinding;

public class BookSlotActivityr extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
private ActivityBookSlotActivityrBinding binding;
private String username_slot;
private String book_time_slot;
private Intent replyIntent;
public final static String USERNAME = "username";
public final static String SLOT_TIME="slot_time";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookSlotActivityrBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        ArrayAdapter<CharSequence>adapter =  ArrayAdapter.createFromResource(BookSlotActivityr.this,R.array.slots,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(adapter);

        binding.spinner.setOnItemSelectedListener(this);


        //username_slot = binding.usernameEt.getText().toString().trim();

        binding.saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!username_slot.isEmpty()){
                    replyIntent = getIntent();
                    replyIntent.putExtra(USERNAME,binding.usernameEt.getText().toString().trim());
                    int slot = Integer.parseInt(book_time_slot);
                    replyIntent.putExtra(SLOT_TIME,slot);
                    setResult(RESULT_OK,replyIntent);
                    Log.d("tag", "onClick: "+binding.usernameEt.getText().toString().trim());
                    Log.d("tag", "onClick: "+slot);

                    //    Toast.makeText(BookSlotActivityr.this, "Please fill in the username", Toast.LENGTH_SHORT).show();

                }else{
                    setResult(RESULT_CANCELED,replyIntent);
                }

                finish();
            }
        });





    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String temp_book_time_slot = adapterView.getItemAtPosition(i).toString();
        if(temp_book_time_slot.contentEquals("1")){
            book_time_slot = "1";
        }else if(temp_book_time_slot.contentEquals("2")){
            book_time_slot = "2";

        }else if(temp_book_time_slot.contentEquals("3")){
            book_time_slot = "3";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}