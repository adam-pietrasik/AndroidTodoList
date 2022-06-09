package com.example.todolistapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.todolistapp.Database.TaskDatabase;
import com.example.todolistapp.RecyclerView.TasksAdapter;
import com.example.todolistapp.Task.TaskActivity;
import com.example.todolistapp.Task.TaskData;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<TaskData> taskDataList;
    private TaskData taskData;

    private RecyclerView recyclerView;
    private TasksAdapter adapter;

    private EditText searchInput;
    private ImageButton searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskDataList = new ArrayList<>();

        initRecyclerView();
        setTaskListData();

        searchInput = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButtonId);

        FloatingActionButton addTaskButton = findViewById(R.id.addTask);
        addTaskButton.setOnClickListener(l -> {
            changeActivity();
        });

        searchButton.setOnClickListener(l -> {
            searchData();
        });
    }

    private void initRecyclerView(){
        recyclerView = findViewById(R.id.todoListRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TasksAdapter(taskDataList);
        recyclerView.setAdapter(adapter);
    }


    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK){
                    setTaskListData();
                }
            }
        );

    @SuppressLint("NotifyDataSetChanged")
    private void setTaskListData(){
        TaskDatabase db = TaskDatabase.getDbInstance(this);
        taskDataList = db.taskDAO().getAllData();
        adapter.setItems(taskDataList);
        adapter.notifyDataSetChanged();
    }

    private void changeActivity(){
        Intent intent = new Intent(this, TaskActivity.class);
        activityResultLauncher.launch(intent);
    }

    @SuppressLint("NotifyDataSetChanged")
    private void searchData(){
        String searchTitle = searchInput.getText().toString();
        if(searchTitle.isEmpty()){
            setTaskListData();
        }
        else{
            TaskDatabase db = TaskDatabase.getDbInstance(this);
            taskDataList = db.taskDAO().getTaskByTitle(searchTitle);
            adapter.setItems(taskDataList);
            adapter.notifyDataSetChanged();
        }
    }
//    @Override
//    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
//        if(taskData != null){
//            outState.putParcelableArrayList("TaskDataList", (ArrayList<? extends Parcelable>) taskDataList);
//            getIntent().putExtras(outState);
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Intent intent = getIntent();
//        Bundle data = intent.getExtras();
//        if(data != null){
//            taskDataList = data.getParcelableArrayList("TaskData");
//        }
//    }
}