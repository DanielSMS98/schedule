package com.example.schedule.ui.home;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.schedule.MainActivity;
import com.example.schedule.R;
import com.example.schedule.db.DbHelper;
import com.example.schedule.db.Events_log;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private GridView gridView;
    private ArrayList<String> list_ev = new ArrayList<String>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        gridView = root.findViewById(R.id.list_events);

        Context context = getActivity();
        DbHelper db = new DbHelper(context);

        //Mostrar todo los eventos registrados
        List<Events_log> events_logList = db.getAllEvents();
        for (Events_log ec : events_logList){
            String eve = ec.getEvento()+"\n"+ec.getFecha();
            list_ev.add(eve);
        }//for

        ArrayAdapter adapter;
        adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, list_ev);
        gridView.setAdapter(adapter);



        return root;
    }//onCreateView


}//class