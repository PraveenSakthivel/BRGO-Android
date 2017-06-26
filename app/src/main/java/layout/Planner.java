package layout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.provider.CalendarContract;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Calendar;
import solar.blaz.date.week.WeekDatePicker;

import com.tble.brgo.InfoArticle;
import com.tble.brgo.R;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.TextStyle;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Planner#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Planner extends Fragment implements reminderInterface {
    private ArrayList<String> Data;
    private ListView table;
    private WeekDatePicker datePicker;
    private TextView label;
    private LocalDate selectedDate;
    private StandardCellAdapter Globadapter;

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
        label = (TextView) view.findViewById(R.id.monthLabel);
        table = (ListView) view.findViewById(R.id.PlannerTable);
        selectedDate = LocalDate.now();
        label.setText(selectedDate.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        Data = new ArrayList<String>(sharedPref.getStringSet(selectedDate.toString(), new HashSet<String>()));
        setTable();
        datePicker = (WeekDatePicker) view.findViewById(R.id.date_picker);
        datePicker.setOnDateSelectedListener(new WeekDatePicker.OnDateSelected() {
            @Override
            public void onDateSelected(LocalDate date) {
                selectedDate = date;
                label.setText(date.getMonth().getDisplayName(TextStyle.FULL, Locale.ENGLISH));
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
                Data = new ArrayList<String>(sharedPref.getStringSet(selectedDate.toString(), new HashSet<String>()));
                setTable();
            }
        });
        datePicker.setOnWeekChangedListener(new WeekDatePicker.OnWeekChanged() {
            @Override
            public void onItemSelected(LocalDate firstDay) {
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
        /**if (table.getChildCount() > 0) {
            TextView border = new TextView(getContext());
            border.setHeight(1);
            border.setBackgroundColor(Color.parseColor("#1995AD"));
            table.addView(border);
        }
        EditText holder = new EditText(getContext());
        holder.setHeight(400);
        holder.setBackgroundColor(Color.parseColor("#FFFFFF"));
        holder.setText(info);
        holder.setTag(1);
        holder.setGravity(Gravity.TOP);
        holder.addTextChangedListener(new TextWatcher() {
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
        });*/
        Data.add("");
        setTable();
    }

    private void setTable() {
        if (Data.size() == 0)
            Data.add("");
        Globadapter = new StandardCellAdapter(getContext(),sendableData(),3,null,this);
        table.setAdapter(Globadapter);
    }

    private ArrayList<InfoArticle> sendableData(){
        ArrayList<InfoArticle> temp = new ArrayList<InfoArticle>();
        for(String a: Data){
            temp.add(new InfoArticle(a,""));
        }
        return temp;
    }
    public void saveData() {
        HashSet<String> temp = new HashSet<String>();
        for (int i = 0; i < table.getCount(); i += 1) {
        }
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        sharedPref.edit().putStringSet(selectedDate.toString(), temp).commit();
    }

    public void addReminder(int position) {
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .putExtra(CalendarContract.Events.DESCRIPTION, Data.get(position));
        startActivity(intent);
    }
}
