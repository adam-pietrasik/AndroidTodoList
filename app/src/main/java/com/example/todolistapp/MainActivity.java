package com.example.todolistapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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
import com.example.todolistapp.Notification.ReminderBroadcast;
import com.example.todolistapp.RecyclerView.OnTaskClickListener;
import com.example.todolistapp.RecyclerView.TasksAdapter;
import com.example.todolistapp.Settings.SettingsActivity;
import com.example.todolistapp.Task.TaskActivity;
import com.example.todolistapp.Task.TaskData;
import com.example.todolistapp.Task.TaskInformationActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnTaskClickListener {

    private List<TaskData> taskDataList;

    private RecyclerView recyclerView;
    private TasksAdapter adapter;

    private EditText searchInput;
    private ImageButton searchButton;

    private String notificationTimer;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        taskDataList = new ArrayList<>();

        initRecyclerView();
        setTaskListData();

        settingsChange();
        createNotificationChannel();

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
                    if(taskDataList.get(taskDataList.size() - 1 ).notificationEnable)
                        setReminder();
                }
            }
        );


    ActivityResultLauncher<Intent> taskInformationResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK){
                    setTaskListData();
                    settingsChange();
                    if(taskDataList.get(id).notificationEnable)
                        setReminder();
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
        notificationTimer = preferences.getString("notifications", "30");

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

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "TaskReminderChannel";
            String desc = "Channel for task reminder";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("TaskReminder", name, importance);
            channel.setDescription(desc);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void setReminder(){
        Intent intent = new Intent(this, ReminderBroadcast.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this,
                taskDataList.size() - 1,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            );

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        String taskEndDate = taskDataList.get(taskDataList.size() - 1).taskEndDate;

        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
        System.out.println(taskEndDate);

        long timer = System.currentTimeMillis();
        System.out.println("Current time = " + timer);
        try{
            Date date = format.parse(taskEndDate);
            System.out.println(date.toString());
            timer = date.getTime();
            System.out.println("Timer after parsing date = " + timer);
        }
        catch (ParseException e){
            e.printStackTrace();
        }

        long notification = Long.parseLong(notificationTimer);
        notification = notification * 60 * 1000;
        timer -= notification;
        System.out.println("Timer after adding notification timer = " + timer);
        alarmManager.set(AlarmManager.RTC_WAKEUP,
                timer,
                pendingIntent);

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
        id = taskDataList.get(position).id;
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