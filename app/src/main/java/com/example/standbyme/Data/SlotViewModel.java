package com.example.standbyme.Data;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.standbyme.Model.CustomerSlot;

import java.util.List;

public class SlotViewModel extends AndroidViewModel {
    private final LiveData<List<CustomerSlot>>slot_list_model;
    private static SlotRepository repository;

    public SlotViewModel(@NonNull Application application){
        super(application);
        repository =  new SlotRepository(application);
        slot_list_model = repository.getAllList_repo();
        Log.d("tag", "SlotViewModel: invoked");
    }

    public static void saveSlot(CustomerSlot customerSlot){
        repository.insert_repo(customerSlot);
    }

    public static void deleteAllSlots(){
        repository.deleteAll_repo();
    }

    public static void updateSlot(CustomerSlot customerSlot){
        repository.update_repo(customerSlot);
    }

    public void deleteSlotModel(CustomerSlot cs_id){
        repository.delete_repo(cs_id);
    }

    public LiveData<List<CustomerSlot>>getAllSlotsModel(){
        return  this.slot_list_model;
    }
}
