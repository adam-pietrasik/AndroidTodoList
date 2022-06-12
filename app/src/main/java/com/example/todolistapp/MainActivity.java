package com.example.todolistapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.Database.TaskDatabase;
import com.example.todolistapp.RecyclerView.OnTaskClickListener;
import com.example.todolistapp.RecyclerView.TasksAdapter;
import com.example.todolistapp.Settings.SettingsActivity;
import com.example.todolistapp.Task.TaskActivity;
import com.example.todolistapp.Task.TaskData;
import com.example.todolistapp.Task.TaskInformationActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity implements OnTaskClickListener {

    private List<TaskData> taskDataList;

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

        settingsChange();

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

        adapter = new TasksAdapter(taskDataList, this);
        recyclerView.setAdapter(adapter);
    }


    ActivityResultLauncher<Intent> taskActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK){
                    setTaskListData();
                    settingsChange();
                }
            }
        );


    ActivityResultLauncher<Intent> taskInformationResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK){
                    setTaskListData();
                    settingsChange();
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
        taskActivityResultLauncher.launch(intent);
    }


    private void settingsChange(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean hideTasks = preferences.getBoolean("hide_completed_tasks", false);
        String category = preferences.getString("categories", "All");
        String notificationTimer = preferences.getString("notifications", "30");

        if(category.equals("All")){
            setTaskListData();
        }
        else{
            changeTasksByCategory(category);
        }

        if(hideTasks){
            hideCompletedTasks(hideTasks);
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void hideCompletedTasks(boolean hideTasks){
        TaskDatabase db = TaskDatabase.getDbInstance(this);
        taskDataList = db.taskDAO().getTaskByTaskDone(hideTasks);
        adapter.setItems(taskDataList);
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void changeTasksByCategory(String category){
        TaskDatabase db = TaskDatabase.getDbInstance(this);
        taskDataList = db.taskDAO().getTaskByCategories(category);
        adapter.setItems(taskDataList);
        adapter.notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void searchData(){
        String searchTitle = searchInput.getText().toString();
        if(searchTitle.isEmpty()){
            setTaskListData();
        }
        else{
            TaskDatabase db = TaskDatabase.getDbInstance(this);
            taskDataList = db.taskDAO().getTasksByTitle(searchTitle);
            adapter.setItems(taskDataList);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(int position) {
        int id = taskDataList.get(position).id;
        Intent intent = new Intent(this, TaskInformationActivity.class);
        intent.putExtra("Task_id", id);
        taskInformationResultLauncher.launch(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}