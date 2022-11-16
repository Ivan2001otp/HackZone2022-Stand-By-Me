package com.example.standbyme.Controller;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.standbyme.Model.Customer;
import com.example.standbyme.Model.CustomerSlot;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities={CustomerSlot.class},version = 1,exportSchema = false)
public abstract class RemoteRoomDatabase extends RoomDatabase {
    public static final  int  NUMBER_OF_THREADS = 4;
    public static final String DATABASE_NAME="customer_slot_table";
    public static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static volatile RemoteRoomDatabase INSTANCE;


    public static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            //deleteAll

            databaseWriterExecutor.execute(()->{
                CustomerDao Dao = INSTANCE.customerDao_base();
               // Dao.deleteAll();

               // CustomerSlot cs1 = new CustomerSlot(1,"Ivan",2);
                //Dao.insert(cs1);

            });


        }
    };

    public static RemoteRoomDatabase getInstance(final Context context){

    if(INSTANCE == null){
        synchronized (RemoteRoomDatabase.class){
            if(INSTANCE==null){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(),RemoteRoomDatabase.class,DATABASE_NAME)
                                .addCallback(sRoomDatabaseCallback)
                                .build();
            }
        }
    }
        return INSTANCE;
    }

    public abstract CustomerDao customerDao_base();

}
