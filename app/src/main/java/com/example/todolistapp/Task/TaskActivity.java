package com.example.todolistapp.Task;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.todolistapp.Database.TaskDatabase;
import com.example.todolistapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TaskActivity extends AppCompatActivity {

    private EditText taskTitleEditText;
    private EditText taskDescriptionEditText;
    private EditText categoryNameEditText;
    private CheckBox taskNotification;

    private Button addTaskButton;
    private Button chooseDateButton;

    private String title;
    private String description;
    private String category;
    private String taskEndDate;

    private boolean dataNotFull = true;
    private TaskData taskData;

    DatePickerDialog datePickerDialog;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        taskTitleEditText = findViewById(R.id.titleEditText);
        taskDescriptionEditText = findViewById(R.id.descriptionEditText);
        taskNotification = findViewById(R.id.notificationTaskCheckBox);
        categoryNameEditText = findViewById(R.id.taskCategoryEditText);
        addTaskButton = findViewById(R.id.addTaskButton);
        chooseDateButton = findViewById(R.id.chooseDate);

        chooseDateButton.setOnClickListener(l ->{
                showDateTimePicker();
            }
        );


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
            category = categoryNameEditText.getText().toString();
            if(category.isEmpty()){
                Toast.makeText(this, "No category set", Toast.LENGTH_SHORT).show();
                dataNotFull = true;
            }
            if(taskEndDate == null){
                Toast.makeText(this, "No end date set", Toast.LENGTH_SHORT).show();
                dataNotFull = true;
            }
            boolean notificationEnabled = taskNotification.isChecked();
            if(!dataNotFull){
                String currentDate = getTaskCreationDate();
                taskData = new TaskData(title, description, currentDate, notificationEnabled,
                                        category, taskEndDate);
                saveDataToDatabase(taskData);
            }
        });
    }

    private String getTaskCreationDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return formatter.format(date);
    }

    private void saveDataToDatabase(TaskData taskData){
        TaskDatabase db = TaskDatabase.getDbInstance(this);
        db.taskDAO().insertAll(taskData);

        setResult(RESULT_OK);
        finish();
    }

    private void showDateTimePicker(){
        calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        datePickerDialog = new DatePickerDialog(this,
                (datePicker, y, M, d) -> { //year, month, day
                    taskEndDate = checkDigit(d) + "/" + checkDigit(M) + "/" + y;
                    new TimePickerDialog(this, (view, h, m) -> { //hour, minute
                        taskEndDate = taskEndDate + " " + checkDigit(h) + ":" + checkDigit(m);
                    }, hour, minute, true).show();
                },
                year, month, dayOfMonth);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        datePickerDialog.show();
    }

    private String checkDigit(int number) {
        return number <= 9 ? "0" + number : String.valueOf(number);
    }
}