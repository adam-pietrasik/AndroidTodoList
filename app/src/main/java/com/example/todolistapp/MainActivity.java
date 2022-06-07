package com.example.todolistapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import com.example.todolistapp.RecyclerView.TasksAdapter;
import com.example.todolistapp.Task.TaskActivity;
import com.example.todolistapp.Task.TaskData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<TaskData> taskDataList;
    private TaskData taskData;

    RecyclerView recyclerView;
    private TasksAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskDataList = new ArrayList<>();

        initRecyclerView();

        FloatingActionButton addTaskButton = findViewById(R.id.addTask);
        addTaskButton.setOnClickListener(l -> {
            changeActivity();
        });
    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.todoListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TasksAdapter(taskDataList);
        recyclerView.setAdapter(adapter);
    }

    @SuppressLint("NotifyDataSetChanged")
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent data = result.getData();
                    if(data != null){
                        Bundle bundle = data.getParcelableExtra("TaskInformations");
                        taskData = bundle.getParcelable("TaskData");
                        if(taskData != null) {
                            if(taskDataList == null){
                                taskDataList = adapter.getItems();
                            }
                            addDataToTaskList(taskData);
                        }
                    }

                }
            }
        );

    private void addDataToTaskList(TaskData taskData){
        taskDataList.add(taskData);
        adapter.setItems(taskDataList);
        adapter.notifyDataSetChanged();
    }

    private void changeActivity(){
        Intent intent = new Intent(this, TaskActivity.class);
        activityResultLauncher.launch(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(taskData != null){
            outState.putParcelableArrayList("TaskDataList", (ArrayList<? extends Parcelable>) taskDataList);
            getIntent().putExtras(outState);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        Bundle data = intent.getExtras();
        if(data != null){
            taskDataList = data.getParcelableArrayList("TaskData");
        }
    }
}