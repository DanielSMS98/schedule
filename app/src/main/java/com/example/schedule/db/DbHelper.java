package com.example.schedule.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NOMBRE = "Agenda.db";
    public static final String TABLE_EVENTS = "t_events";

    //Columnas de la tabla
    private static final String KEY_ID = "id";
    private static final String KEY_EVENTS = "events";
    private static final String KEY_DATE = "date";
    private static final String KEY_DESCRIPTION = "description";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }//Constructor

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String para crear la tabla
        String CREATE_EVENTS_TABLE = "CREATE TABLE "+ TABLE_EVENTS +
                "("+KEY_ID+" INTEGER PRIMARY KEY," +
                KEY_EVENTS+" TEXT," +
                KEY_DATE+ " TEXT," +
                KEY_DESCRIPTION+" TEXT)";
        //crear tabla de eventos
        db.execSQL(CREATE_EVENTS_TABLE);
    }//Metodo Oncreate


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Borrar tabla si existe
        db.execSQL("DROP TABLE IF EXISTS " +TABLE_EVENTS);
        //Crear tabla otra vez
        onCreate(db);
    }//Actualizacion de base de datos

    //Agregar nuevo evento
    public void addEvents(Events_log eventsLog){
        SQLiteDatabase db = this.getWritableDatabase();
        //llenar los valores
        ContentValues values = new ContentValues();
        values.put(KEY_EVENTS,eventsLog.getEvento());
        values.put(KEY_DATE,eventsLog.getFecha());
        values.put(KEY_DESCRIPTION,eventsLog.getDescripcion());
        //Insertar en las filas de la tablas
        db.insert(TABLE_EVENTS,null,values);
        //Cerra conexion de la base de datos
        db.close();
    }//addEvents

   /** //obtener un evento
    public Events_log getEvents(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_EVENTS,
                new String[] { KEY_ID,
                        KEY_EVENTS, KEY_DATE, KEY_DESCRIPTION},
                KEY_ID + "=?",
                new String[] {String.valueOf(id)},
                null,null,null,null);
        if(cursor != null)
            cursor.moveToFirst();
        Events_log eventsLog = new Events_log(Integer.
                parseInt(cursor.getString(0)),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3));
        //regresar contacto
        return eventsLog;
    } //obtener un contacto
    */


   //obtener todos los contactos
   public List<Events_log> getAllEvents(){
       //lista vacia donde se agregaran los eventos
       ArrayList<Events_log> allEvents = new ArrayList<>();

       //Consulta_todo_lo_que_tiene_la_tabla
       String selectQuery = "SELECT * FROM " + TABLE_EVENTS;
       SQLiteDatabase db = this.getWritableDatabase();
       Cursor cursor = db.rawQuery(selectQuery, null);

       // Interacion sobre todas las filas y las agrega a la lista
       if(cursor.moveToFirst()){
           do{
               Events_log eventsLog = new Events_log();
               eventsLog.setId(Integer.parseInt(cursor.getString(0)));
               eventsLog.setEvento(cursor.getString(1));
               eventsLog.setFecha(cursor.getColumnName(2));
               eventsLog.setDescripcion(cursor.getString(3));

               allEvents.add(eventsLog);
               //agregar contatos a la lista hasta que ya no haya mas
           } while (cursor.moveToNext());
       }//if

       return allEvents;
   }//obtener todos los contactos


    /**
    //actualizar la base de datos
    public void updateEvents(int id, String events, String date, String description){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EVENTS, events);
        values.put(KEY_DATE, date);
        values.put(KEY_DESCRIPTION, description);

        //actualizar fila
        db.update(TABLE_EVENTS, values, KEY_ID + " = ?", new String[] {String.valueOf(id)});
    }//actualizar la base de datos
     */

    /**
    //borrar evento de la base de datos
    public void deleteEvents(Events_log eventsLog){
        SQLiteDatabase db =this.getWritableDatabase();
        db.delete(TABLE_EVENTS,
                KEY_ID + " = ?",
                new String[] { String.valueOf(eventsLog.getId())});
        db.close();
    }//borrar contacto de la base de datos
    */

    //Conteo de contactos
    public int getEventsCount(){
        String countQuery = "SELECT * FROM " + TABLE_EVENTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery,null);
        return cursor.getCount();
    }//Conteo de contactos


}//class
