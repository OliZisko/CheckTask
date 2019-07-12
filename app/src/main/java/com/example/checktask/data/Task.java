package com.example.checktask.data;

import java.util.Date;

/**

 @author: Alberto Garcia - Francisco De Oliveira - Jose Cafaro
 CheckTask - App movil para tareas por hacer
 Proyecto de la materia de Programación Bajo Ambiente Android en la UCAB
 @version 1.0.0. / 12-07-2019

 */

/* Sirve como objeto para las tareas. Nunca fue usado, usamos listas de String con
* cada elemento de la base de datos.
 */
public class Task {
    private Integer id;
    private String detail;
    private String date;
    private String completed;

    public Task(Integer id, String detail, String date, String completed) {
        this.id = id;
        this.detail = detail;
        this.date = date;
        this.completed = completed;
    }

    public Integer getId() {
        return id;
    }

    public String getDetail() {
        return detail;
    }

    public String getDate() {
        return date;
    }

    public String getCompleted() {
        return completed;
    }

}
