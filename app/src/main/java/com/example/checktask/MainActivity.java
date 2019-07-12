package com.example.checktask;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.checktask.data.Task;
import com.example.checktask.data.TasksDbHelper;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

/**

 @author: Alberto Garcia - Francisco De Oliveira - Jose Cafaro
 CheckTask - App movil para tareas por hacer
 Proyecto de la materia de Programación Bajo Ambiente Android en la UCAB
 @version 1.0.0. / 12-07-2019

 */

public class MainActivity extends AppCompatActivity {
    private LinkedList<String> mIdList = new LinkedList<>();
    private LinkedList<String> mTaskList = new LinkedList<>();
    private LinkedList<String> mDateList = new LinkedList<>();
    private LinkedList<String> mCompletedList = new LinkedList<>();
    private ImageView imageDelete;
    private TextView hideText;
    private RecyclerView mRecyclerView;
    private TaskListAdapter taskAdapter;
    private Context ctx;

    /* Este metodo callback se activa cuando es iniciada la pantalla
    * del mainactivity y tiene la funcionalidad del boton FAB para ir
    * a la pantalla de crear tarea.
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, TaskCreate.class);
                startActivity(intent);
            }
        });
    }

    /* Este metodo callback se activa cuando se reanuda la activity
    * y refresca la lista de tareas.
     */
    @Override
    public void onResume(){
        super.onResume();
        refresh();
    }

    /* Infla el menu de opciones de los 3 botones de settings.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /* Este metodo verifica cual opcion se selecciono del menu.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_completed) {
            Intent intent = new Intent(MainActivity.this, TaskCompleted.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.action_delete) {
            dropTable();
            refresh();
            Toast toast = Toast.makeText(ctx, "Todas las tareas fueron eliminadas", Toast.LENGTH_LONG);
            toast.show();
            return true;
        }

        if (id == R.id.action_deleteC) {
            deleteCompletadas();
            refresh();
            Toast toast = Toast.makeText(ctx, "Todas las tareas completadas fueron eliminadas", Toast.LENGTH_LONG);
            toast.show();
            return true;
        }

        if (id == R.id.action_deleteI) {
            deleteIncompletas();
            refresh();
            Toast toast = Toast.makeText(ctx, "Todas las tareas incompletas fueron eliminadas", Toast.LENGTH_LONG);
            toast.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /* Este metodo refresca la lista de tareas incompletas
     */
    public void refresh(){
        ctx = this;
        mIdList = new LinkedList<>();
        mTaskList = new LinkedList<>();
        mDateList = new LinkedList<>();
        mCompletedList = new LinkedList<>();
        hideText = findViewById(R.id.hideTextI);
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
        mRecyclerView = findViewById(R.id.recyclerview);
        // Crea un adapter y suministra los datos a ser mostrados.
        taskAdapter = new TaskListAdapter(this, mIdList, mTaskList, mDateList, mCompletedList);
        // Conecta el adapter con el RecyclerView.
        mRecyclerView.setAdapter(taskAdapter);
        // Le da al RecyclerView un layout manager por defecto.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /* Este metodo borra todas las tareas de la base de datos.
     */
    public void dropTable(){
        TasksDbHelper dbHelper = new TasksDbHelper(ctx);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM TASK");
    }

    /* Este metodo borra las tareas completadas de la base de datos.
     */
    public void deleteCompletadas(){
        TasksDbHelper dbHelper = new TasksDbHelper(ctx);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM TASK WHERE COMPLETED='Completada'");
    }

    /* Este metodo borra las tareas incompletas de la base de datos.
     */
    public void deleteIncompletas(){
        TasksDbHelper dbHelper = new TasksDbHelper(ctx);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("DELETE FROM TASK WHERE COMPLETED='Incompleta'");
    }

    /* Este metodo selecciona todas las tareas incompletas de la base de datos.
     */
    public Cursor selectAllTasks() throws SQLException {
        TasksDbHelper dbHelper = new TasksDbHelper(ctx);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] args = new String[]{"Incompleta"};
        Cursor c = db.rawQuery("SELECT * FROM TASK WHERE COMPLETED=?", args);
        return c;
    }

    /* Verifica que tarea se selecciono de la lista y completa la tarea, esto
    * para abrir el activity de tareas completadas y mostrarla alli. Se puede
    * ver un mensaje toast.
     */
    public void onCheckboxClicked(View view) {
        CheckBox checkBox;
        TextView textHide;
        checkBox = findViewById(R.id.checkTask);
        textHide = findViewById(R.id.idHide);
        boolean checked = checkBox.isChecked();
        String fecha = getDate();
        String completado = "Completada";
        String id = textHide.getText().toString();
        if(checked){
            TasksDbHelper dbHelper = new TasksDbHelper(ctx);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //Insertamos el registro en la base de datos
            db.execSQL("UPDATE TASK SET DATE='"+fecha+"', COMPLETED='"+completado+"' WHERE _id="+Integer.parseInt(id));
            Toast toast = Toast.makeText(ctx, "La tarea fue completada con éxito!", Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(ctx, TaskCompleted.class);
            ctx.startActivity(intent);
        }
        else{
            //nothing here
        }
    }

    /* Este metodo elimina la tarea seleccionada de la lista.
     */
    public void deleteI(View view){
        TextView textHide;
        textHide = findViewById(R.id.idHide);
        String id = textHide.getText().toString();
        TasksDbHelper dbHelper = new TasksDbHelper(ctx);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        //Insertamos el registro en la base de datos
        db.execSQL("DELETE FROM TASK WHERE _id="+Integer.parseInt(id));
        refresh();
        Toast toast = Toast.makeText(ctx, "La tarea fue eliminada", Toast.LENGTH_LONG);
        toast.show();
    }

    /* Este metodo obtiene la fecha actual para finalizar una tarea incompleta.
     */
    private String getDate(){
        DateFormat dfDate = new SimpleDateFormat("dd/MM/yyyy");
        String date=dfDate.format(Calendar.getInstance().getTime());
        DateFormat dfTime = new SimpleDateFormat("HH:mm");
        String time = dfTime.format(Calendar.getInstance().getTime());
        return date + " " + time;
    }
}
