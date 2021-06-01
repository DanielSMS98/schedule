package com.example.schedule.ui.calendar;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.schedule.R;
import com.example.schedule.ui.home.HomeViewModel;

import java.util.Calendar;

public class CalendarFragment extends Fragment {

    private CalendarView mCalendar;
    private ListView mList_event;
    private CalendarViewModel calendarViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        calendarViewModel =
                new ViewModelProvider(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.calendar_fragment, container, false);
        mCalendar = root.findViewById(R.id.cal_events);
        final TextView textView = root.findViewById(R.id.text_calendar);
        mList_event = root.findViewById(R.id.list_events);

        return root;
    }//onCreate


}//class