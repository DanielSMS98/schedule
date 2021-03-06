package com.example.schedule.ui.calendar;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schedule.R;
import com.example.schedule.db.DbHelper;
import com.example.schedule.db.Events_log;
import com.example.schedule.ui.events.EventsFragment;
import com.example.schedule.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class CalendarFragment extends Fragment {

    private CalendarView mCalendar;
    private GridView mList_event;
    private CalendarViewModel calendarViewModel;
    private ArrayList<String> list_ev = new ArrayList<String>();
    private ArrayList<Events_log> list = new ArrayList<Events_log>();
    private int frag;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        calendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.calendar_fragment, container, false);
        mCalendar = root.findViewById(R.id.cal_events);
        final TextView textView = root.findViewById(R.id.text_calendar);
        mList_event = root.findViewById(R.id.list_events);


        CalendarView cal_events = (CalendarView) root.findViewById(R.id.cal_events);// Agregado por leo
        cal_events.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int i, int i1, int i2) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
                CharSequence []items = new CharSequence[3];
                items[0]="Agregar evento";
                items[1]="Ver eventos";
                items[2]="Cancelar";

                final int dia, mes, a??o;

                dia = i;
                mes = i1+1;
                a??o = i2;

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment2 = new EventsFragment();

                alert.setTitle("Seleccione una tarea")
                        .setItems(items, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                if (i==0) {
                                    //agregar eventos
                                    //Intent intent = new Intent(getActivity(),  EventsFragment.class);
                                    textView.setText("");
                                    mList_event.setAdapter(null);
                                    list_ev.clear();
                                    list.clear();

                                    Bundle bundle = new Bundle();
                                    bundle.putInt("dia",dia);
                                    bundle.putInt("mes",mes);
                                    bundle.putInt("a??o",a??o);
                                    //intent.putExtras(bundle);
                                    //startActivity(intent);
                                    //
                                    fragment2.setArguments(bundle);
                                    fragmentManager.beginTransaction().replace(R.id.frame_calendar_down,
                                            fragment2).addToBackStack(null).commit();
                                    frag=1;

                                }else if (i==1) {
                                    textView.setText("Lista de eventos");
                                    mList_event.setAdapter(null);
                                    list_ev.clear();
                                    if (frag==1){
                                        //Metodo en la parte de abajo para cerrar fragment
                                        fragmentManager.beginTransaction().remove(fragmentManager.findFragmentById
                                                (R.id.frame_calendar_down)).commit();
                                        frag=0;
                                    }//if

                                    Bundle bundle = new Bundle();
                                    bundle.putInt("dia",dia);
                                    bundle.putInt("mes",mes);
                                    bundle.putInt("a??o",a??o);

                                    String textComp = bundle.getInt("a??o")+"/"+bundle.getInt("mes")
                                            +"/"+bundle.getInt("dia");

                                    Context context = getActivity();
                                    DbHelper db = new DbHelper(context);


                                    //Mostrar todos los eventos registrados
                                    List<Events_log> events_logList = db.getAllEvents();
                                    for (Events_log ec : events_logList) {
                                        if (ec.getFecha().equals(textComp)) {
                                            String eve = "\nEvento: "+ec.getEvento()+
                                                    "\nFecha: "+ec.getFecha();
                                            list.add(ec);
                                            list_ev.add(eve);
                                        }//if
                                    }//for

                                    if (list_ev.size() >= 1){
                                        ArrayAdapter adapter = new ArrayAdapter<String>(context,
                                                android.R.layout.simple_list_item_1, list_ev);
                                        mList_event.setAdapter(adapter);
                                    }//if
                                    else{
                                        Toast.makeText(getActivity(),"No hay eventos en la fecha",Toast.LENGTH_LONG).show();
                                    }//else

                                    mList_event = root.findViewById(R.id.list_events);
                                    mList_event.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
                                                                //Borrar eventos
                                                                Context context = getActivity();
                                                                DbHelper db = new DbHelper(context);
                                                                Events_log evento = new Events_log();
                                                                evento = db.getEvents(list.get(position));
                                                                db.deleteEvents(evento);

                                                                //recargar lista
                                                                mList_event.setAdapter(null);
                                                                list_ev.clear();
                                                                list.clear();

                                                                List<Events_log> events_logList = db.getAllEvents();
                                                                for(Events_log ec : events_logList){
                                                                    String eve = "\nEvento: "+ec.getEvento()+
                                                                            "\nFecha: "+ec.getFecha();
                                                                    list.add(ec);
                                                                    list_ev.add(eve);
                                                                }//for
                                                                ArrayAdapter com = new ArrayAdapter<String>(context,
                                                                        android.R.layout.simple_list_item_1, list_ev);
                                                                mList_event.setAdapter(com);
                                                                Toast.makeText(getActivity(),"Evento eliminado con ??xito",Toast.LENGTH_LONG).show();
                                                            }else {
                                                                return;
                                                            }
                                                        }// OnClick
                                                    });

                                            AlertDialog dialog = alert.create();
                                            dialog.show();
                                        }// OnItemClick
                                    });
                                }else {
                                    return;
                                }
                            }// OnClick
                        });

                AlertDialog dialog = alert.create();
                dialog.show();
            }// OnSelect

        });// Agregado por leo --- setOnDataChange


        return root;
    }//onCreat



}//class