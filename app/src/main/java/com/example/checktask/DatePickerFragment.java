package com.example.checktask;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**

 @author: Alberto Garcia - Francisco De Oliveira - Jose Cafaro
 CheckTask - App movil para tareas por hacer
 Proyecto de la materia de Programación Bajo Ambiente Android en la UCAB
 @version 1.0.0. / 12-07-2019

 */
public class DatePickerFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {

    /**
     * Este metodo callback que se activa cuando es iniciado el calendario
     * y muestra un Dialog del calendario en formato de widget
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        /* Se coloca la fecha actual predefinida para que se muestre en el Calendario*/
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        /*Crea la instancia del widget del Calendario para que se le despliegue al usuario*/
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    /**
     * Este metodo obtiene la fecha seleccionada por el usuario y es enviada al activity
     * TaskCreate que fue el que llamo al Dialog del Calendario
     */
    public void onDateSet(DatePicker view, int year, int month, int day) {
        /*Setea la actividad que se va a enviar como TaskCreate*/
        TaskCreate activity = (TaskCreate) getActivity();
        /*Retorna la data al TaskCreate*/
        activity.processDatePickerResult(year, month, day);
    }
}