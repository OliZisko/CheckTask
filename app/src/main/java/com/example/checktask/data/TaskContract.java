package com.example.checktask.data;

import android.provider.BaseColumns;

public class TaskContract {
    public static abstract class TaskEntry implements BaseColumns{
        public static final String TABLE_NAME = "task";
        public static final String DETAIL = "detail";
        public static final String DATE = "date";
        public static final String COMPLETED = "completed";
    }
}
