package com.example.standbyme.Model;

import android.view.ViewDebug;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "customer_slot_table")
public class CustomerSlot {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name="userName_slot")
    private String userName;

    @ColumnInfo(name="slot_book")
    private int slot_book;

    public CustomerSlot(String userName, int slot_book) {
        this.userName = userName;
        this.slot_book = slot_book;
    }

    public CustomerSlot(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSlot_book() {
        return slot_book;
    }

    public void setSlot_book(int slot_book) {
        this.slot_book = slot_book;
    }
}
