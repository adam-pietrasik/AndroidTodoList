package com.example.todolistapp.Database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.todolistapp.Task.TaskData;

import java.util.List;

@Dao
public interface TaskDAO {

    @Query("SELECT * FROM task_data_table ORDER BY task_end_date ASC")
    List<TaskData> getAllData();

    @Query("SELECT * FROM task_data_table WHERE category LIKE :category")
    List<TaskData> getAllCategories(String category);

    @Query("SELECT * FROM task_data_table WHERE title LIKE :title || '%' ")
    List<TaskData> getTasksByTitle(String title);

    @Query("SELECT * FROM task_data_table WHERE id LIKE :id LIMIT 1")
    TaskData getTaskById(int id);

    @Query("SELECT category FROM task_data_table GROUP BY category")
    List<String> getCategories();

    @Query("SELECT * FROM task_data_table WHERE category LIKE :category")
    List<TaskData> getTaskByCategories(String category);

    @Query("SELECT * FROM task_data_table WHERE task_done NOT LIKE :taskDone")
    List<TaskData> getTaskByTaskDone(boolean taskDone);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(TaskData... taskData);

    @Query("UPDATE task_data_table SET title = :title, description = :description, " +
            "category = :category, task_done = :taskDone, notification_enable = :notificationChecked WHERE id = :id")
    void updateTask(String title, String description,
                    String category, boolean taskDone, boolean notificationChecked, int id);
}
