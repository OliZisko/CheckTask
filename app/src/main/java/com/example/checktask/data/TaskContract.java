package com.example.checktask.data;

import android.provider.BaseColumns;

/**

 @author: Alberto Garcia - Francisco De Oliveira - Jose Cafaro
 CheckTask - App movil para tareas por hacer
 Proyecto de la materia de Programación Bajo Ambiente Android en la UCAB
 @version 1.0.0. / 12-07-2019

 */

/* Sirve como modelo para la tabla de la base de datos.
*/
public class TaskContract {
    public static abstract class TaskEntry implements BaseColumns{
        public static final String TABLE_NAME = "task";
        public static final String DETAIL = "detail";
        public static final String DATE = "date";
        public static final String COMPLETED = "completed";
    }
}
