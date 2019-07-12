package com.example.checktask;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.checktask.data.TasksDbHelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**

 @author: Alberto Garcia - Francisco De Oliveira - Jose Cafaro
 CheckTask - App movil para tareas por hacer
 Proyecto de la materia de Programación Bajo Ambiente Android en la UCAB
 @version 1.0.0. / 12-07-2019

 */

public class TaskCreate extends AppCompatActivity {
    EditText detail;
    ToggleButton completed;
    String id, detalle, fecha, completado, fechaDate;
    Button create, update;
    Context ctx;
    TextView dateView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create);
        create = findViewById(R.id.buttonCreate);
        update = findViewById(R.id.updateCreate);
        create.setVisibility(View.VISIBLE);
        update.setVisibility(View.INVISIBLE);
        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            id = parametros.getString("id");
            detalle = parametros.getString("detail");
            fecha = parametros.getString("date");
            completado = parametros.getString("completed");
            updateData(id, detalle, fecha, completado);
            create.setVisibility(View.INVISIBLE);
            update.setVisibility(View.VISIBLE);
        }
        if (savedInstanceState != null) {
            detail = findViewById(R.id.detailCreate);
            dateView = findViewById(R.id.dateView);
            completed = findViewById(R.id.completedCreate);
            detail.setText(savedInstanceState.getString("detail"));
            dateView.setText(savedInstanceState.getString("date"));
            completed.setText(savedInstanceState.getString("completed"));
        }
    }

    public void saveTask(View view) {
        ctx = this;
        detail = findViewById(R.id.detailCreate);
        completed = findViewById(R.id.completedCreate);
        dateView = findViewById(R.id.dateView);
        detalle = detail.getText().toString();
        fecha = dateView.getText().toString();
        completado = completed.getText().toString();
        if(!detalle.equals("") && !fecha.equals("")){
            //Creamos el registro a insertar como objeto ContentValues
            ContentValues newTask = new ContentValues();
            newTask.put("detail", detalle);
            newTask.put("date", fecha);
            newTask.put("completed", completado);
            TasksDbHelper dbHelper = new TasksDbHelper(ctx);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //Insertamos el registro en la base de datos
            db.insert("task", null, newTask);
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "La tarea fue creada con éxito!", Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else{
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "Por favor rellene los campos faltantes", Toast.LENGTH_LONG);
            toast.show();
        }

    }

    public void updateTask(View view) {
        ctx = this;
        detail = findViewById(R.id.detailCreate);
        completed = findViewById(R.id.completedCreate);
        dateView = findViewById(R.id.dateView);
        detalle = detail.getText().toString();
        fecha = dateView.getText().toString();
        completado = completed.getText().toString();
        if(!detalle.equals("") && !fecha.equals("")) {
            TasksDbHelper dbHelper = new TasksDbHelper(ctx);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            //Insertamos el registro en la base de datos
            db.execSQL("UPDATE TASK SET DETAIL='" + detalle + "', DATE='" + fecha + "', COMPLETED='" + completado + "' WHERE _id=" + Integer.parseInt(id));
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "La tarea fue editada con éxito!", Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else{
            Context context = getApplicationContext();
            Toast toast = Toast.makeText(context, "Por favor rellene los campos faltantes", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void updateData(String id, String detalle, String fecha, String completado){
        ctx = this;
        detail = findViewById(R.id.detailCreate);
        completed = findViewById(R.id.completedCreate);
        dateView = findViewById(R.id.dateView);
        detail.setText(detalle);
        DateFormat dfTime = new SimpleDateFormat("HH:mm");
        String time = dfTime.format(Calendar.getInstance().getTime());
        dateView.setText(fecha);
        completed.setText(completado);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),
                getString(R.string.date_picker));
    }

    public void processDatePickerResult(int year, int month, int day) {
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        dateView = findViewById(R.id.dateView);
        fechaDate = (day_string + "/" +
                month_string + "/" + year_string);
        DateFormat dfTime = new SimpleDateFormat("HH:mm");
        String time = dfTime.format(Calendar.getInstance().getTime());
        dateView.setText(fechaDate + " " + time);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        detail = findViewById(R.id.detailCreate);
        dateView = findViewById(R.id.dateView);
        completed = findViewById(R.id.completedCreate);
        outState.putString("detail", detail.getText().toString());
        outState.putString("date", dateView.getText().toString());
        outState.putString("completed", completed.getText().toString());
    }//Cierre del método
}