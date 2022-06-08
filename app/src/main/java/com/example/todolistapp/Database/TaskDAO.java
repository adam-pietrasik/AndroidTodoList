package com.example.todolistapp.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.todolistapp.Task.TaskData;

import java.util.List;

@Dao
public interface TaskDAO {

    @Query("SELECT * FROM task_data_table")
    List<TaskData> getAllData();

    @Query("SELECT * FROM task_data_table WHERE category LIKE :category")
    List<TaskData> getAllCategories(String category);

    @Query("SELECT * FROM task_data_table WHERE title LIKE :title LIMIT 1")
    TaskData getTaskByTitle(String title);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(TaskData... taskData);

    @Update
    void updateTask(TaskData taskData);
}
