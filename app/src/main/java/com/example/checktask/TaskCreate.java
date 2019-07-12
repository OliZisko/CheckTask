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

    /**
     * Este metodo callback que se ejecuta al iniciar el Activity. Donde se realiza un savedInstanceState para guardar
     * el estado de la actividad. Ademas recibe el Extra con los parametros y en caso de no ser vacios, rellena
     * los campos con esos datos.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_create);
        create = findViewById(R.id.buttonCreate);
        update = findViewById(R.id.updateCreate);
        create.setVisibility(View.VISIBLE);
        update.setVisibility(View.INVISIBLE);
        /*Recibe los parametros mediante el extra con parametros, y verifica si no esta vacio y de ser ese el caso rellana los campos*/
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
        /*Salva el estado de la actividad*/
        if (savedInstanceState != null) {
            detail = findViewById(R.id.detailCreate);
            dateView = findViewById(R.id.dateView);
            completed = findViewById(R.id.completedCreate);
            detail.setText(savedInstanceState.getString("detail"));
            dateView.setText(savedInstanceState.getString("date"));
            completed.setText(savedInstanceState.getString("completed"));
        }
    }

    /**
     * Este metodo donde obtiene los datos de la tarea a crear, y  procede a crear la tarea para
     * insertarla en la base de datos SQLite
     */
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

    /**
     * Este metodo modificar la tarea existente en la base de datos SQLite
     */
    public void updateTask(View view) {
        /*Obtiene los datos que seran modificados en la tarea*/
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

    /**
     * Este metodo obtiene la data que sera modificada sobre la tarea insertada
     */
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

    /**
     * Este metodo que crea la instancia del fragment del DatePickerFragment para poder abrir el widget del Calendario
     * y asi el usuario pueda escoger las fechas para la tarea
     */
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(),
                getString(R.string.date_picker));
    }

    /**
     * Este metodo recibe los datos de la fecha escogida por el usuario en el widget del Calendario
     */
    public void processDatePickerResult(int year, int month, int day) {
        /*Se reciben los datos de dia, mes y año*/
        String month_string = Integer.toString(month+1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        dateView = findViewById(R.id.dateView);
        /*Concatena los datos para obtener un solo formato para la fecha*/
        fechaDate = (day_string + "/" +
                month_string + "/" + year_string);
        /*Se obtiene la hora de creacion de la tarea*/
        DateFormat dfTime = new SimpleDateFormat("HH:mm");
        String time = dfTime.format(Calendar.getInstance().getTime());
        /*Se presentan los datos al usuario*/
        dateView.setText(fechaDate + " " + time);
    }

    /**
     * Este metodo salva el estado de la actividad y de esa manera no perder los datos
     * en caso de realizar algun cambio como girar la pantalla
     */
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