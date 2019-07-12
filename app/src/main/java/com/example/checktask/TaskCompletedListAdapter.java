package com.example.checktask;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

/**

 @author: Alberto Garcia - Francisco De Oliveira - Jose Cafaro
 CheckTask - App movil para tareas por hacer
 Proyecto de la materia de Programación Bajo Ambiente Android en la UCAB
 @version 1.0.0. / 12-07-2019

 */

public class TaskCompletedListAdapter extends RecyclerView.Adapter<TaskCompletedListAdapter.TaskCompletedViewHolder> {

    private LinkedList<String> mTaskList, mIdList, mDateList, mCompletedList;
    private LayoutInflater mInflater;
    Context ctx;

    public TaskCompletedListAdapter(Context context, LinkedList<String> idList, LinkedList<String> taskList, LinkedList<String> dateList, LinkedList<String> completedList) {
        mInflater = LayoutInflater.from(context);
        ctx = context;
        this.mIdList = idList;
        this.mTaskList = taskList;
        this.mDateList = dateList;
        this.mCompletedList = completedList;
    }

    @NonNull
    @Override
    public TaskCompletedViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mItemView = mInflater.inflate(R.layout.tasks_complete, viewGroup, false);
        return new TaskCompletedViewHolder(mItemView, this);

    }

    @Override
    public void onBindViewHolder(@NonNull TaskCompletedViewHolder taskCompletedViewHolder, int i) {
        String mId = mIdList.get(i);
        String mTask = mTaskList.get(i);
        String mDate = mDateList.get(i);
        String mCompleted = mCompletedList.get(i);
        taskCompletedViewHolder.detailItemView.setText(mTask);
        taskCompletedViewHolder.dateItemView.setText(mDate);
        taskCompletedViewHolder.checkItemView.setText(mCompleted+"!");
        taskCompletedViewHolder.hideItemC.setText(mId);
        taskCompletedViewHolder.hideItemC.setVisibility(View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    class TaskCompletedViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView detailItemView, dateItemView, checkItemView, hideItemC;
        final TaskCompletedListAdapter taskAdapter;

        public TaskCompletedViewHolder(View itemView, TaskCompletedListAdapter adapter) {
            super(itemView);
            detailItemView = itemView.findViewById(R.id.detailTask);
            dateItemView = itemView.findViewById(R.id.dateTaskf);
            checkItemView = itemView.findViewById(R.id.checkTask);
            hideItemC = itemView.findViewById(R.id.idHideC);
            this.taskAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast toast = Toast.makeText(ctx, "Esta tarea ya fue completada, Felicidades!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

}
