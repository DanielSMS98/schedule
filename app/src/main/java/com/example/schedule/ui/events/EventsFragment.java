package com.example.schedule.ui.events;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.FragmentManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schedule.MainActivity;
import com.example.schedule.R;
import com.example.schedule.db.DbHelper;
import com.example.schedule.db.Events_log;
import com.example.schedule.ui.calendar.CalendarFragment;
import com.example.schedule.ui.calendar.CalendarViewModel;
import com.example.schedule.ui.home.HomeFragment;

import static com.example.schedule.Helpers.NotificacionHelper.CANAL_1_ID;

public class EventsFragment extends Fragment {

    private EventsViewModel eventsViewModel;
    private EditText edt_nombre,edt_descripcion,edt_fecha;
    private Button btn_agregar,btn_cancelar;
    private GridView gridView;
    public static FragmentManager fragmentManager;

    private NotificationManagerCompat notificationManager;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        eventsViewModel =
                new ViewModelProvider(this).get(EventsViewModel.class);
        View root = inflater.inflate(R.layout.events_fragment, container, false);
        final TextView textView = root.findViewById(R.id.text_events);
        edt_nombre = root.findViewById(R.id.edt_nombre);
        edt_descripcion = root.findViewById(R.id.edt_descripcion);
        edt_fecha = root.findViewById(R.id.edt_Fecha);
        btn_agregar = root.findViewById(R.id.btn_agregar);
        btn_cancelar = root.findViewById(R.id.btn_cancelar);
        notificationManager = NotificationManagerCompat.from(getContext());

        Context context = getActivity();
        DbHelper db = new DbHelper(context);

        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edt_nombre.getText().toString().isEmpty() &&
                        !edt_fecha.getText().toString().isEmpty()){

                    Events_log evento = new Events_log();
                    int i = db.getEventsCount();
                    evento.setId(i);
                    evento.setEvento(edt_nombre.getText().toString());
                    evento.setFecha(edt_fecha.getText().toString());
                    evento.setDescripcion(edt_descripcion.getText().toString());
                    db.addEvents(evento);
                    Toast.makeText(getActivity(),"Evento creado",Toast.LENGTH_LONG).show();
                    getActivity().onBackPressed();

                    //Crea una notificaci??n
                    String nombre = edt_nombre.getText().toString();
                    String descripcion = edt_descripcion.getText().toString();


                    Notification notification = new NotificationCompat.Builder(
                            getContext(),
                            CANAL_1_ID).setSmallIcon(R.drawable.ic_uno)
                            .setContentTitle(nombre)
                            .setContentText(descripcion)
                            .setPriority(NotificationCompat.PRIORITY_HIGH)
                            .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                            .setColor(Color.GREEN)
                            
                            .build();
                    notificationManager.notify(1, notification);
                    //Fin de la notificaci??n
                }//if
                else {
                    Toast.makeText(getActivity(),"Introduce los datos",Toast.LENGTH_LONG).show();
                }//else

            }
        });//Boton de agregar

        // Revision para su correcto funcionaiento
        btn_cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Regresar a fragment home o resumen
                //getActivity().finish();
                getActivity().onBackPressed();
            }
        });


        return root;

    }//onCreate


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstance) {
        Bundle datosRecuperados = getArguments();
        if (datosRecuperados == null) {
            //No hay datos, manejar la excepci??n
            return;
        }
        //podemos usar get en lugar de put
        int a??o = datosRecuperados.getInt("dia");
        int mes = datosRecuperados.getInt("mes");
        int dia = datosRecuperados.getInt("a??o");

        Log.d("EventoAgregar", "El dia: " + dia);
        Log.d("EventoAgregar", "El mes: " + mes);
        Log.d("EventoAgregar", "El a??o: " + a??o);

        edt_fecha.setText(dia+"/"+mes+"/"+a??o);
    }

}//class