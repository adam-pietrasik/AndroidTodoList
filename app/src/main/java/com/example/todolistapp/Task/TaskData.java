package com.example.todolistapp.Task;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "task_data_table")
public class TaskData {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public String title;
    public String description;

    @ColumnInfo(name = "creation_time")
    public String creationTime;
    public String category;

    @ColumnInfo(name = "task_end_date")
    public String taskEndDate;

    @ColumnInfo(name = "notification_enable")
    public boolean notificationEnable;

    @ColumnInfo(name = "task_done")
    public boolean taskDone;

    public TaskData(){}

    public TaskData(String title, String description, String creationTime,
                    boolean notificationEnable, String category, String taskEndDate) {
        this.title = title;
        this.description = description;
        this.creationTime = creationTime;
        this.notificationEnable = notificationEnable;
        this.category = category;
        this.taskEndDate = taskEndDate;
    }

//    public String getTitle() {
//        return title;
//    }
//
//    public void setTitle(String title) {
//        this.title = title;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getCreationTime() {
//        return creationTime;
//    }
//
//    public void setCreationTime(String creationTime) {
//        this.creationTime = creationTime;
//    }
//
//    public boolean isNotificationEnable() {
//        return notificationEnable;
//    }
//
//    public void setNotificationEnable(boolean notificationEnable) {
//        this.notificationEnable = notificationEnable;
//    }
//
//    public String getCategory() {
//        return category;
//    }
//
//    public void setCategory(String category) {
//        this.category = category;
//    }
//
//    public String getTaskEndDate() {
//        return taskEndDate;
//    }
//
//    public void setTaskEndDate(String taskEndDate) {
//        this.taskEndDate = taskEndDate;
//    }
//
//
//    @Override
//    public String toString() {
//        return "TaskData{" +
//                "title='" + title + '\'' +
//                ", description='" + description + '\'' +
//                ", creationTime='" + creationTime + '\'' +
//                ", category='" + category + '\'' +
//                ", taskEndDate='" + taskEndDate + '\'' +
//                ", notificationEnable=" + notificationEnable +
//                '}';
//    }
}
