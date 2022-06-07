package com.example.todolistapp.Task;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.todolistapp.R;

public class TaskActivity extends AppCompatActivity {

    private EditText taskTitleEditText;
    private EditText taskDescriptionEditText;
    private CheckBox taskNotification;
    private Button addTaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        taskTitleEditText = findViewById(R.id.titleEditText);
        taskDescriptionEditText = findViewById(R.id.descriptionEditText);
        taskNotification = findViewById(R.id.notificationTaskCheckBox);
        addTaskButton = findViewById(R.id.addTaskButton);
    }
}