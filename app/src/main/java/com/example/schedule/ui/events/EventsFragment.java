package com.example.schedule.ui.events;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.schedule.R;
import com.example.schedule.ui.calendar.CalendarViewModel;

public class EventsFragment extends Fragment {

    private EventsViewModel eventsViewModel;
    private EditText edt_nombre,edt_descripcion,edt_fecha;
    private Button btn_agregar,btn_cancelar;

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

        return root;

    }//onCreate



}