package com.example.checktask;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.checktask.data.TasksDbHelper;

import java.util.LinkedList;

/**

 @author: Alberto Garcia - Francisco De Oliveira - Jose Cafaro
 CheckTask - App movil para tareas por hacer
 Proyecto de la materia de Programación Bajo Ambiente Android en la UCAB
 @version 1.0.0. / 12-07-2019

 */

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.TaskViewHolder> {

    private LinkedList<String> mTaskList, mIdList, mDateList, mCompletedList;
    private LayoutInflater mInflater;
    Context ctx;

    /*Constructor del Adaptador de las tareas incompletas*/
    public TaskListAdapter(Context context, LinkedList<String> idList, LinkedList<String> taskList, LinkedList<String> dateList, LinkedList<String> completedList) {
        mInflater = LayoutInflater.from(context);
        ctx = context;
        this.mIdList = idList;
        this.mTaskList = taskList;
        this.mDateList = dateList;
        this.mCompletedList = completedList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mItemView = mInflater.inflate(R.layout.tasks_imcomplete, viewGroup, false);
        return new TaskViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
        String mId = mIdList.get(i);
        String mTask = mTaskList.get(i);
        String mDate = mDateList.get(i);
        String mCompleted = mCompletedList.get(i);
        taskViewHolder.detailItemView.setText(mTask);
        taskViewHolder.dateItemView.setText(mDate);
        taskViewHolder.checkItemView.setText(mId);
        taskViewHolder.checkItemView.setVisibility(View.INVISIBLE);
    }

    /*Sirve para obtener el numero de tareas de la lista de tareas incompletas.
     */
    @Override
    public int getItemCount() {
        return mTaskList.size();
    }

    class TaskViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView detailItemView, dateItemView, checkItemView;
        final TaskListAdapter taskAdapter;

        /* Constructor de la clase.
         */
        public TaskViewHolder(View itemView, TaskListAdapter adapter) {
            super(itemView);
            detailItemView = itemView.findViewById(R.id.detailTask);
            dateItemView = itemView.findViewById(R.id.dateTaskf);
            checkItemView = itemView.findViewById(R.id.idHide);
            this.taskAdapter = adapter;
            itemView.setOnClickListener(this);
        }

        /*Este metodo abre el activity para editar la tarea seleccionada.
        */
        @Override
        public void onClick(View v) {
            // Obtiene la posición del elemento al que se le hizo clic.
            int mPosition = getLayoutPosition();
            String id = mIdList.get(mPosition);
            String detail = mTaskList.get(mPosition);
            String date = mDateList.get(mPosition);
            String completed = mCompletedList.get(mPosition);
            Intent intent = new Intent(ctx, TaskCreate.class);
            intent.putExtra("id", id);
            intent.putExtra("detail", detail);
            intent.putExtra("date", date);
            intent.putExtra("completed", completed);
            ctx.startActivity(intent);
        }

    }

}
