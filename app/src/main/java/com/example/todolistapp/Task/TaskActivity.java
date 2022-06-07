package com.example.todolistapp.Task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.todolistapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskActivity extends AppCompatActivity {

    private EditText taskTitleEditText;
    private EditText taskDescriptionEditText;
    private CheckBox taskNotification;
    private Button addTaskButton;

    private String title;
    private String description;

    private boolean dataNotFull = true;
    private TaskData taskData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        taskTitleEditText = findViewById(R.id.titleEditText);
        taskDescriptionEditText = findViewById(R.id.descriptionEditText);
        taskNotification = findViewById(R.id.notificationTaskCheckBox);
        addTaskButton = findViewById(R.id.addTaskButton);

        taskCreate();
    }

    private void taskCreate(){
        addTaskButton.setOnClickListener(l -> {
            dataNotFull = false;
            title = taskTitleEditText.getText().toString();
            if(title.isEmpty()){
                Toast.makeText(this, "No title set", Toast.LENGTH_SHORT).show();
                dataNotFull = true;
            }
            description = taskDescriptionEditText.getText().toString();
            if(description.isEmpty()){
                Toast.makeText(this, "No description set", Toast.LENGTH_SHORT).show();
                dataNotFull = true;
            }
            boolean notificationEnabled = taskNotification.isChecked();
            if(!dataNotFull){
                String currentDate = getTaskCreationDate();
                taskData = new TaskData(title, description, currentDate, notificationEnabled);
                sendBackToMainActivity(taskData);
            }
        });
    }

    private String getTaskCreationDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return formatter.format(date);
    }

    private void sendBackToMainActivity(TaskData taskData){
        Intent mainActivity = new Intent();
        Bundle taskInformations = new Bundle();
        taskInformations.putParcelable("TaskData", taskData);
        mainActivity.putExtra("TaskInformations", taskInformations);
        setResult(RESULT_OK, mainActivity);
        finish();
    }
}