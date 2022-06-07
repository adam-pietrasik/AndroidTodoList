package com.example.todolistapp.RecyclerView;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView titleTv;
    private TextView descriptionTv;
    private TextView taskCreatedTimeTv;
    private TextView taskCompletedTimeTv;
    private TextView doneTv;
    private TextView categoryTv;

    private CheckBox notificationCb;

    public RecyclerViewHolder(@NonNull View itemView){
        super(itemView);

        titleTv = itemView.findViewById(R.id.titleTextView);
        descriptionTv = itemView.findViewById(R.id.descriptionTextView);
        taskCreatedTimeTv = itemView.findViewById(R.id.timeTaskCreatedTextView);
        taskCompletedTimeTv = itemView.findViewById(R.id.timeTaskCompletedTextView);
        doneTv = itemView.findViewById(R.id.taskDoneTextView);
        categoryTv = itemView.findViewById(R.id.taskCategory);
        notificationCb = itemView.findViewById(R.id.notificationCheckBox);
        notificationCb.setClickable(false);

    }

    public TextView getTitleTv() {
        return titleTv;
    }

    public TextView getDescriptionTv() {
        return descriptionTv;
    }

    public TextView getTaskCreatedTimeTv() {
        return taskCreatedTimeTv;
    }

    public TextView getTaskCompletedTimeTv() {
        return taskCompletedTimeTv;
    }

    public TextView getDoneTv() {
        return doneTv;
    }

    public TextView getCategoryTv() {
        return categoryTv;
    }

    public CheckBox getNotificationCb() {
        return notificationCb;
    }
}
