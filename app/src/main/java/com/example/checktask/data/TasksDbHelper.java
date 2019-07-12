package com.example.checktask.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**

 @author: Alberto Garcia - Francisco De Oliveira - Jose Cafaro
 CheckTask - App movil para tareas por hacer
 Proyecto de la materia de Programación Bajo Ambiente Android en la UCAB
 @version 1.0.0. / 12-07-2019

 */

/* Clase que sirve como helper para usar comandos en la base de datos.
 */
public class TasksDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "tasks.db";

    /* Constructor de la clase.
     */
    public TasksDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /* Crea la tabla Task en la base de datos de Sqlite.
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL("CREATE TABLE " + TaskContract.TaskEntry.TABLE_NAME + " ("
                + TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + TaskContract.TaskEntry.DETAIL + " TEXT NOT NULL,"
                + TaskContract.TaskEntry.DATE + " TEXT NOT NULL,"
                + TaskContract.TaskEntry.COMPLETED + " TEXT NOT NULL,"
                + "UNIQUE (" + TaskContract.TaskEntry._ID + "))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // No hay operaciones
    }
}
