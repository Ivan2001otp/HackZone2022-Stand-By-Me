package com.example.standbyme.Major;

import static com.example.standbyme.BookSlotActivityr.*;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.standbyme.Adapter.RecyclerViewAdapter;
import com.example.standbyme.BookSlotActivityr;
import com.example.standbyme.Data.SlotViewModel;
import com.example.standbyme.MainActivity;
import com.example.standbyme.Model.CustomerSlot;
import com.example.standbyme.R;
import com.example.standbyme.Receiver.NotifyReciever;
import com.example.standbyme.VideoChatActivity;
import com.example.standbyme.databinding.ActivityMajorBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.List;

public class MajorActivity extends AppCompatActivity  implements RecyclerViewAdapter.OnCustomerClickListener {
private SlotViewModel slotViewModel;
private ActivityMajorBinding binding;
private FirebaseAuth auth;
private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onStart() {
        super.onStart();
        auth = FirebaseAuth.getInstance();
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            CharSequence name="notificationChannel";
            String description = "Channel for alarm notification";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("notification",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMajorBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

        //setting the recycler view;
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        createNotificationChannel();
        slotViewModel = new ViewModelProvider.AndroidViewModelFactory(MajorActivity.this.getApplication())
                        .create(SlotViewModel.class);

       slotViewModel.getAllSlotsModel().observe(this, customerSlots -> {
                   recyclerViewAdapter = new RecyclerViewAdapter(customerSlots, this, MajorActivity.this);
                   binding.recyclerView.setAdapter(recyclerViewAdapter);
       });



        binding.slotBook.setOnClickListener(v->{
            //fragment manager
            Intent bookSlotIntent = new Intent(MajorActivity.this, BookSlotActivityr.class);
            someActivityResultLauncher.launch(bookSlotIntent);
        });


        binding.videoChat.setOnClickListener(v->{
                Intent videoIntent = new Intent(MajorActivity.this, VideoChatActivity.class);
                finish();
                startActivity(videoIntent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        if (item.getItemId() == R.id.logOut) {
            auth.signOut();
            Intent MainActIntent = new Intent(MajorActivity.this, MainActivity.class);
            finish();
            startActivity(MainActIntent);
        }
        else if(item.getItemId() == R.id.deleteAl){
            if(recyclerViewAdapter.getItemCount()!=0)
                SlotViewModel.deleteAllSlots();
            else{
                Toast.makeText(MajorActivity.this, "No items to delete", Toast.LENGTH_SHORT).show();
            }
        }
        return true;
    }



    ActivityResultLauncher<Intent>someActivityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        assert data!=null;
                        String username = data.getStringExtra(BookSlotActivityr.USERNAME);
                        int timeSlot = data.getIntExtra(SLOT_TIME,1);


                        CustomerSlot curr_customer_slot = new CustomerSlot(username,timeSlot);
                        SlotViewModel.saveSlot(curr_customer_slot);

                        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                        Intent intent = new Intent(MajorActivity.this, NotifyReciever.class);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(MajorActivity.this,77,intent,PendingIntent.FLAG_UPDATE_CURRENT);

                        Calendar calendar = Calendar.getInstance();


                        switch(timeSlot){
                            case 1:
                                calendar.set(Calendar.HOUR_OF_DAY,9);
                                calendar.set(Calendar.MINUTE,0);
                                calendar.set(Calendar.SECOND,0);
                                calendar.set(Calendar.MILLISECOND,0);

                                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
                                break;
                            case 2:
                                calendar.set(Calendar.HOUR_OF_DAY,14);
                                calendar.set(Calendar.MINUTE,0);
                                calendar.set(Calendar.SECOND,0);
                                calendar.set(Calendar.MILLISECOND,0);
                                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

                                break;
                            case 3:
                                calendar.set(Calendar.HOUR_OF_DAY,18);
                                calendar.set(Calendar.MINUTE,0);
                                calendar.set(Calendar.SECOND,0);
                                calendar.set(Calendar.MILLISECOND,0);
                                alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);

                                break;
                        }

                        Toast.makeText(MajorActivity.this,"Saved Successfully",Toast.LENGTH_SHORT)
                                .show();
                    }else{
                        Toast.makeText(MajorActivity.this,"Error-Major act "+result,Toast.LENGTH_SHORT)
                                .show();
                    }
        }
    });

    @Override
    public void onClickCustomerSlot(int position) {
        CustomerSlot customerSlotdelete = slotViewModel.getAllSlotsModel().getValue().get(position);
        slotViewModel.deleteSlotModel(customerSlotdelete);
        Toast.makeText(MajorActivity.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
    }
}