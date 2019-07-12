package com.example.checktask;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.data.checktask.TasksDbHelper;

import java.util.LinkedList;

/**

 @author: Alberto Garcia - Francisco De Oliveira - Jose Cafaro
 CheckTask - App movil para tareas por hacer
 Proyecto de la materia de Programación Bajo Ambiente Android en la UCAB
 @version 1.0.0. / 12-07-2019

 */

public class TaskCompleted extends AppCompatActivity {
    private LinkedList<String> mIdList = new LinkedList<>();
    private LinkedList<String> mTaskList = new LinkedList<>();
    private LinkedList<String> mDateList = new LinkedList<>();
    private LinkedList<String> mCompletedList = new LinkedList<>();
    private ImageView imageDelete;
    private TextView hideText;
    private RecyclerView mRecyclerViewCompleted;
    private TaskCompletedListAdapter taskCompletedAdapter;
    private Context ctx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_completed);
    }

    @Override
    public void onResume(){
        super.onResume();
        refresh();
    }

    public Cursor selectAllTasks() throws SQLException {
        TasksDbHelper dbHelper = new TasksDbHelper(ctx);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] args = new String[] {"Completada"};
        Cursor c = db.rawQuery("SELECT * FROM TASK WHERE COMPLETED=?",args);
        return c;
    }

    public void refresh(){
        ctx = this;
        mIdList = new LinkedList<>();
        mTaskList = new LinkedList<>();
        mDateList = new LinkedList<>();
        mCompletedList = new LinkedList<>();
        hideText = findViewById(R.id.hideTextC);
        Cursor aux = selectAllTasks();
        if(aux.getCount() == 0){
            hideText.setVisibility(View.VISIBLE);
        }else{
            hideText.setVisibility(View.INVISIBLE);
            aux.moveToFirst();
            while (!aux.isAfterLast()) {
                mIdList.addLast(aux.getString(0));
                mTaskList.addLast(aux.getString(1));
                mDateList.addLast(aux.getString(2));
                mCompletedList.addLast(aux.getString(3));
                aux.moveToNext();
            }
        }
        //for (int i = 1; i < 21; i++) {
        //    mTaskList.addLast("Tarea " + i);
        //}
        // Obtiene un handle para el RecyclerView.
        mRecyclerViewCompleted = findViewById(R.id.recyclerviewCompleted);
        // Crea un adapter y suministra los datos a ser mostrados.
        taskCompletedAdapter = new TaskCompletedListAdapter(this, mIdList, mTaskList, mDateList, mCompletedList);
        // Conecta el adapter con el RecyclerView.
        mRecyclerViewCompleted.setAdapter(taskCompletedAdapter);
        // Le da al RecyclerView un layout manager por defecto.
        mRecyclerViewCompleted.setLayoutManager(new LinearLayoutManager(this));
    }

    public void deleteC(View view){
        TextView textHide;
        textHide = findViewById(R.id.idHideC);
        String id = textHide.getText().toString();
        TasksDbHelper dbHelper = new TasksDbHelper(ctx);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //Insertamos el registro en la base de datos
        db.execSQL("DELETE FROM TASK WHERE _id="+Integer.parseInt(id));
        refresh();
        Toast toast = Toast.makeText(ctx, "La tarea fue eliminada", Toast.LENGTH_LONG);
        toast.show();
    }
}
