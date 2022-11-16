package com.example.standbyme.Controller;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.standbyme.Model.Customer;
import com.example.standbyme.Model.CustomerSlot;

import java.util.List;

@Dao
public interface CustomerDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(CustomerSlot customerSlot);

    @Update
    void update(CustomerSlot customerSlot);

    @Query("SELECT * FROM customer_slot_table ")
    LiveData<List<CustomerSlot>>getAllCustomerSlots();

    @Query("DELETE  FROM  customer_slot_table")
    void deleteAll();

    @Delete
    void delete(CustomerSlot customerSlot);


}
