package com.example.schedule.ui.home;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CalendarView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.schedule.MainActivity;
import com.example.schedule.R;
import com.example.schedule.db.DbHelper;
import com.example.schedule.db.Events_log;
import com.example.schedule.ui.events.EventsFragment;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment<events_logList> extends Fragment {

    private HomeViewModel homeViewModel;
    private GridView gridView;
    private ArrayList<String> list_ev = new ArrayList<String>();
    private ArrayList<Events_log> list = new ArrayList<Events_log>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        gridView = root.findViewById(R.id.list_events);

        //cargar base de datos
        Context context = getActivity();
        DbHelper db = new DbHelper(context);

        //Mostrar todos los eventos registrados
        gridView.setAdapter(null);
        list_ev.clear();
        list.clear();
        List<Events_log> events_logList = db.getAllEvents();
        for(Events_log ec : events_logList){
            String eve = "\nEvento: "+ec.getEvento()+"\nFecha: "+ec.getFecha();
            list.add(ec);
            list_ev.add(eve);
        }//for

        if (list_ev.size() >= 1){
            ArrayAdapter adapter = new ArrayAdapter<String>(context,
                    android.R.layout.simple_list_item_1, list_ev);
            gridView.setAdapter(adapter);
        }//if
        else{
            Toast.makeText(getActivity(),"No hay eventos",Toast.LENGTH_LONG).show();
        }//else

        // Agregado por leo
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                CharSequence []items = new CharSequence[2];
                items[0]="Eliminar";
                items[1]="Cancelar";

                alert.setTitle("Seleccione una tarea")
                        .setItems(items, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if (i==0) {
                                    //Elimimar eventos
                                    db.deleteEvents(list.get(position));
                                    //recargar lista
                                    gridView.setAdapter(null);
                                    list_ev.clear();
                                    list.clear();
                                    List<Events_log> events_logList = db.getAllEvents();
                                    for(Events_log ec : events_logList){
                                        String eve = "Evento: "+ec.getEvento()+"\nFecha: "+ec.getFecha();
                                        list.add(ec);
                                        list_ev.add(eve);
                                    }//for
                                    ArrayAdapter com = new ArrayAdapter<String>(context,
                                            android.R.layout.simple_list_item_1, list_ev);
                                    gridView.setAdapter(com);
                                    Toast.makeText(getActivity(),"Evento eliminado con éxito",Toast.LENGTH_LONG).show();
                                }else {
                                    return;
                                }
                            }// OnClick
                        });

                AlertDialog dialog = alert.create();
                dialog.show();
            }// OnItemClick
        });

        return root;
    }//onCreateView


}//class