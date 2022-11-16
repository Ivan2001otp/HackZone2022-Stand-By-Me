package com.example.standbyme.Data;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.standbyme.Controller.CustomerDao;
import com.example.standbyme.Controller.RemoteRoomDatabase;
import com.example.standbyme.Model.Customer;
import com.example.standbyme.Model.CustomerSlot;

import java.util.List;

public class SlotRepository {
    private LiveData<List<CustomerSlot>> slotListRepo;
    private CustomerDao customerDao_repo;

    public SlotRepository(Application application){
        Log.d("tag", "slotRepository: invoked");

        RemoteRoomDatabase db = RemoteRoomDatabase.getInstance(application);
            customerDao_repo =  db.customerDao_base();
            slotListRepo = customerDao_repo.getAllCustomerSlots();
    }

    public void insert_repo(CustomerSlot customerSlot){
        RemoteRoomDatabase.databaseWriterExecutor.execute(()->{
            customerDao_repo.insert(customerSlot);
        });
    }

    public void deleteAll_repo(){
        RemoteRoomDatabase.databaseWriterExecutor.execute(()->{
            customerDao_repo.deleteAll();
        });
    }

    public void  update_repo(CustomerSlot customerSlot){
        RemoteRoomDatabase.databaseWriterExecutor.execute(()->{
            customerDao_repo.update(customerSlot);
        });
    }

    public void delete_repo(CustomerSlot cs_id){
        RemoteRoomDatabase.databaseWriterExecutor.execute(()->{
            customerDao_repo.delete(cs_id);
        });
    }

    public LiveData<List<CustomerSlot>>getAllList_repo(){
        return this.slotListRepo;
    }
}
