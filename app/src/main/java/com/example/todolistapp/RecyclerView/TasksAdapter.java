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

    public TasksAdapter(List<TaskData> taskDataList){
        this.taskDataList = taskDataList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.getTitleTv().setText(taskDataList.get(position).getTitle());
        holder.getDescriptionTv().setText(taskDataList.get(position).getDescription());
        holder.getTaskCreatedTimeTv().setText(taskDataList.get(position).getCreationTime());
        holder.getNotificationCb().setChecked(taskDataList.get(position).isNotificationEnable());
        holder.getCategoryTv().setText(taskDataList.get(position).getCategory());
        holder.getTaskCompletedTimeTv().setText(taskDataList.get(position).getTaskEndDate());
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
