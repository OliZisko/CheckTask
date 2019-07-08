package com.example.checktask;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedList;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    private LinkedList<String> mTaskList;
    private LayoutInflater mInflater;

    public TaskListAdapter(Context context, LinkedList<String> taskList) {
        mInflater = LayoutInflater.from(context);
        this.mTaskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mItemView = mInflater.inflate(R.layout.tasks_imcomplete, viewGroup, false);
        return new TaskViewHolder(mItemView, this);

    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
        String mCurrent = mTaskList.get(i);
        taskViewHolder.detailItemView.setText(mCurrent);

    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView detailItemView;
        final TaskListAdapter taskAdapter;

        public TaskViewHolder(View itemView, TaskListAdapter adapter) {
            super(itemView);
            detailItemView = itemView.findViewById(R.id.detailTask);
            this.taskAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Obtiene la posición del elemento al que se le hizo clic.
            int mPosition = getLayoutPosition();
            // Usado para tener acceso al elemento afectado en mWordList.
            String element = mTaskList.get(mPosition);
            // Cambia la palabra en la mWordList.
            mTaskList.set(mPosition, "Clicked! " + element);
            // Notifica al adapter, que los datos han cambiado de manera que
            // actualice el RecyclerView para que muestre los datos.
            taskAdapter.notifyDataSetChanged();
        }
    }

}
