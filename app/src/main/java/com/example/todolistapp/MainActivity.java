package com.example.todolistapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.todolistapp.Task.TaskActivity;
import com.example.todolistapp.Task.TaskData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private TaskData taskData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addTaskButton = findViewById(R.id.addTask);
        addTaskButton.setOnClickListener(l -> {
            changeActivity();
        });
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    if(data != null){
                        Bundle bundle = data.getParcelableExtra("TaskInformations");
                        taskData = bundle.getParcelable("TaskData");
                        if(taskData != null)
                            System.out.println(taskData);
                    }

                }
            }
        );

    private void changeActivity(){
        Intent intent = new Intent(this, TaskActivity.class);
        activityResultLauncher.launch(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(taskData != null){
            outState.putParcelable("TaskData", taskData);
            getIntent().putExtras(outState);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        if(data != null){
            taskData = data.getParcelable("TaskData");
        }
    }
}