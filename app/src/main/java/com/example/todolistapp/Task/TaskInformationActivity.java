package com.example.todolistapp.Task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.todolistapp.Database.TaskDatabase;
import com.example.todolistapp.R;

public class TaskInformationActivity extends AppCompatActivity {

    private EditText titleEditText;
    private EditText descriptionEditText;
    private EditText categoryEditText;
    private CheckBox doneCheckBox;
    private CheckBox notificationCheckBox;
    private Button updateButton;

    private int id = -1;

    private TaskData taskData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_information);

        initialize();

        Intent intent = getIntent();
        if(intent != null){
            id = intent.getIntExtra("Task_id", 1);
        }


        if(id != -1){
            getFromDatabase(id);
            titleEditText.setText(taskData.title);
            descriptionEditText.setText(taskData.description);
            categoryEditText.setText(taskData.category);
            notificationCheckBox.setChecked(taskData.notificationEnable);
            doneCheckBox.setChecked(taskData.taskDone);
            onUpdateClicked();
        }
    }

    private void onUpdateClicked(){
        updateButton.setOnClickListener(l -> {
            TaskDatabase taskDatabase = TaskDatabase.getDbInstance(this);
            taskDatabase.taskDAO().updateTask(
                    titleEditText.getText().toString(),
                    descriptionEditText.getText().toString(),
                    categoryEditText.getText().toString(),
                    doneCheckBox.isChecked(),
                    notificationCheckBox.isChecked(),
                    id
            );
            setResult(RESULT_OK);
            finish();
        });
    }

    private void initialize(){
        titleEditText = findViewById(R.id.newTitleEditText);
        descriptionEditText = findViewById(R.id.newDescriptionEditText);
        categoryEditText = findViewById(R.id.newCategoryEditText);
        notificationCheckBox = findViewById(R.id.newNotificationCheckBox);
        updateButton = findViewById(R.id.updateTaskButton);
        doneCheckBox = findViewById(R.id.taskDone);
    }

    private void getFromDatabase(int id){
        TaskDatabase taskDatabase = TaskDatabase.getDbInstance(this);
        taskData = taskDatabase.taskDAO().getTaskById(id);
    }
}