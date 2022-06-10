package com.example.todolistapp.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.R;
import com.example.todolistapp.Task.TaskData;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {

    private List<TaskData> taskDataList;
    private OnTaskClickListener onTaskClickListener;

    public TasksAdapter(List<TaskData> taskDataList, OnTaskClickListener onTaskClickListener){
        this.taskDataList = taskDataList;
        this.onTaskClickListener = onTaskClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new RecyclerViewHolder(view, onTaskClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.getTitleTv().setText(taskDataList.get(position).title);
        holder.getDescriptionTv().setText(taskDataList.get(position).description);
        holder.getTaskCreatedTimeTv().setText(taskDataList.get(position).creationTime);
        holder.getNotificationCb().setChecked(taskDataList.get(position).notificationEnable);
        holder.getCategoryTv().setText(taskDataList.get(position).category);
        holder.getTaskCompletedTimeTv().setText(taskDataList.get(position).taskEndDate);
//        String done = taskDataList.get(position).taskDone ? "Done" : "In progress";
//        holder.getDoneCheckBox().setText(done);
    }

    @Override
    public int getItemCount(){
        return taskDataList.size();
    }

    public void setItems(List<TaskData> taskDataList){
        this.taskDataList = taskDataList;
    }

    public List<TaskData> getItems(){
        return this.taskDataList;
    }
}
