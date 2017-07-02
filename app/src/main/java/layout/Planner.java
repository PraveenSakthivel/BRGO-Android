package layout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.*;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.EditText;
import android.widget.TextView;
import android.view.inputmethod.EditorInfo;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Calendar;
import solar.blaz.date.week.WeekDatePicker;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tble.brgo.InfoArticle;
import com.tble.brgo.R;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.TextStyle;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Planner#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Planner extends Fragment {
    private ArrayList<String> Data;
    private WeekDatePicker datePicker;
    private TextView label;
    private LocalDate selectedDate;
    private TableLayout table;
    private Editor prefEditor;
    private SharedPreferences prefs;


    public Planner() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Planner.
     */
    // TODO: Rename and change types and number of parameters
    public static Planner newInstance() {
        Planner fragment = new Planner();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_planner, container, false);
        prefEditor = PreferenceManager.getDefaultSharedPreferences(getContext()).edit();
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        label = (TextView) view.findViewById(R.id.monthLabel);
        table = (TableLayout) view.findViewById(R.id.PlannerTable);
        selectedDate = LocalDate.now();
        label.setText(selectedDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        Data = new ArrayList<String>(prefs.getStringSet(selectedDate.toString(), new HashSet<String>()));
        setTable();
        datePicker = (WeekDatePicker) view.findViewById(R.id.date_picker); //Custom Library
        datePicker.setOnDateSelectedListener(new WeekDatePicker.OnDateSelected() {
            @Override
            public void onDateSelected(LocalDate date) {
                selectedDate = date;
                System.out.println(getContext());
                label.setText(date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
                Data = new ArrayList<String>(prefs.getStringSet(selectedDate.toString(), new HashSet<String>()));
                setTable();
            }
        });
        datePicker.setOnWeekChangedListener(new WeekDatePicker.OnWeekChanged() {
            @Override
            public void onItemSelected(LocalDate firstDay) { //Changes what month is shown based on the first day in the week
                label.setText(firstDay.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
            }
        });
        FloatingActionButton addButton = (FloatingActionButton) view.findViewById(R.id.addRowButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRow("");
            }
        });
        return view;
    }

    private void addRow(String info) {
        EditText editor = new EditText(getContext());
        editor.setText(info);
        editor.setWidth(AppBarLayout.LayoutParams.MATCH_PARENT);
        editor.setHeight(400);
        editor.setGravity(Gravity.TOP);
        table.addView(editor);
        editor.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveData();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void setTable() {
        table.removeAllViews();
        if (Data.size() == 0)
            Data.add("");
        for (String temp: Data){
            addRow(temp);
        }
    }

    //Saves the data as a hashset CUATION: hashset not ideal should be changed
    public void saveData() {
        HashSet<String> temp = new HashSet<String>();
        for (int i = 0; i < table.getChildCount(); i += 1) {
        temp.add(((EditText)table.getChildAt(i)).getText().toString());
        }
        prefEditor.putStringSet(selectedDate.toString(),temp).apply();
    }
}
