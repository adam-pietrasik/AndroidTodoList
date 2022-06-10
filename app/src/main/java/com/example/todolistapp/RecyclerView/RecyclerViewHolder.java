package com.example.todolistapp.RecyclerView;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolistapp.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView titleTv;
    private TextView descriptionTv;
    private TextView taskCreatedTimeTv;
    private TextView taskCompletedTimeTv;
    private TextView categoryTv;

    private CheckBox notificationCb;
    private CheckBox doneCheckBox;


    private OnTaskClickListener onTaskClickListener;

    public RecyclerViewHolder(@NonNull View itemView, OnTaskClickListener onTaskClickListener){
        super(itemView);

        titleTv = itemView.findViewById(R.id.titleTextView);
        descriptionTv = itemView.findViewById(R.id.descriptionTextView);
        taskCreatedTimeTv = itemView.findViewById(R.id.timeTaskCreatedTextView);
        taskCompletedTimeTv = itemView.findViewById(R.id.timeTaskCompletedTextView);

        categoryTv = itemView.findViewById(R.id.taskCategory);
        notificationCb = itemView.findViewById(R.id.notificationCheckBox);
        notificationCb.setClickable(false);

        doneCheckBox = itemView.findViewById(R.id.taskDoneCheckBox);
        doneCheckBox.setClickable(false);
        this.onTaskClickListener = onTaskClickListener;

        itemView.setOnClickListener(this);
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

    public CheckBox getDoneCheckBox() {
        return doneCheckBox;
    }

    public TextView getCategoryTv() {
        return categoryTv;
    }

    public CheckBox getNotificationCb() {
        return notificationCb;
    }

    @Override
    public void onClick(View view) {
        onTaskClickListener.onItemClick(getAdapterPosition());
    }
}
