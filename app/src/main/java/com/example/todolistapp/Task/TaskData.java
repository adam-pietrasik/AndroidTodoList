package com.example.todolistapp.Task;

import android.os.Parcel;
import android.os.Parcelable;

public class TaskData implements Parcelable {

    private String title;
    private String description;
    private String creationTime;
    private String category;
    private String taskEndDate;

    private boolean notificationEnable;

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

    protected TaskData(Parcel in) {
        title = in.readString();
        description = in.readString();
        creationTime = in.readString();
        notificationEnable = in.readByte() != 0;
        category = in.readString();
        taskEndDate = in.readString();
    }

    public static final Creator<TaskData> CREATOR = new Creator<TaskData>() {
        @Override
        public TaskData createFromParcel(Parcel in) {
            return new TaskData(in);
        }

        @Override
        public TaskData[] newArray(int size) {
            return new TaskData[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public boolean isNotificationEnable() {
        return notificationEnable;
    }

    public void setNotificationEnable(boolean notificationEnable) {
        this.notificationEnable = notificationEnable;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTaskEndDate() {
        return taskEndDate;
    }

    public void setTaskEndDate(String taskEndDate) {
        this.taskEndDate = taskEndDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.title);
        parcel.writeString(this.description);
        parcel.writeString(this.creationTime);
        parcel.writeBoolean(this.notificationEnable);
        parcel.writeString(this.category);
        parcel.writeString(this.taskEndDate);
    }


    @Override
    public String toString() {
        return "TaskData{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", category='" + category + '\'' +
                ", taskEndDate='" + taskEndDate + '\'' +
                ", notificationEnable=" + notificationEnable +
                '}';
    }
}
