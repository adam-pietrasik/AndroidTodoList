package com.example.todolistapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.todolistapp.Task.TaskData;

@Database(entities = {TaskData.class}, version = 1)
public abstract class TaskDatabase extends RoomDatabase {
    public abstract TaskDAO taskDAO();

    private static TaskDatabase instance;

    public static TaskDatabase getDbInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    TaskDatabase.class, "task_database")
                        .allowMainThreadQueries()
                        .build();
        }
        return instance;
    }


}
