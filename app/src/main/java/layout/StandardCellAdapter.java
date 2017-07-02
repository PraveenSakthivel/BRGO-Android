package layout;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.Image;
import android.nfc.Tag;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.View;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.tble.brgo.InfoArticle;
import com.tble.brgo.R;
import android.graphics.Typeface;
import android.widget.Filterable;
import android.widget.Filter;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import org.w3c.dom.Text;

/**
 * Created by Praveen on 8/21/16.
 */
public class StandardCellAdapter extends ArrayAdapter<InfoArticle> implements Filterable {
    public ArrayList<InfoArticle> orig;
    public ArrayList<InfoArticle> Teachers;
    public ArrayList<EditText> editors;
    public SwipeRevealLayout swipeV;
    public TextView deleteButton;
    public TextView saveButton;
    public TextView reminderButton;
    public int tableType;
    public displayInterface activity;
    public Context ct;

    //NOT IDEAL. Adapter should be split into multiple adapters instead of accounting for every case
    public StandardCellAdapter(Context context, ArrayList<InfoArticle> titles, int tableType, displayInterface inter) {
        super(context, 0, titles);
        this.Teachers = titles;
        this.tableType = tableType;
        saveButton = new TextView(context);
        deleteButton = new TextView(context);
        editors = new ArrayList<EditText>();
        activity = inter;
        ct = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String title = getItem(position).title;
        String desc = getItem(position).description;
        Holder TagHolder = null;
        TextView Ctitle = new TextView(getContext());
        EditText edit = new EditText(getContext());
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            //Standard textbox filled table
            if (tableType == 0) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.nclayout, parent, false);
                Ctitle = (TextView) convertView.findViewById(R.id.cellTitle);
            }
            //Table with textboxes that can be slid to the right to reveal a save button
            else if(tableType == 1){
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.sclayout, parent, false);
                Ctitle = (TextView) convertView.findViewById(R.id.cellTitle);
                TagHolder = new Holder();
                swipeV = (SwipeRevealLayout)convertView.findViewById(R.id.scSwipe);
                saveButton = (TextView)convertView.findViewById(R.id.saveButton);
                TagHolder.Save = saveButton;
                TagHolder.Cell = Ctitle;
                convertView.setTag(TagHolder);
            }
            //Table with textboxes that can be slid to reveal a delete button
            else{
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.tclayout, parent, false);
                Ctitle = (TextView) convertView.findViewById(R.id.cellTitle);
                TagHolder = new Holder();
                swipeV = (SwipeRevealLayout)convertView.findViewById(R.id.tcSwipe);
                deleteButton = (TextView) convertView.findViewById(R.id.deleteButton);
                TagHolder.Delete = deleteButton;
                TagHolder.Cell = Ctitle;
                convertView.setTag(TagHolder);
            }
        }
        else {
            TagHolder = (Holder) convertView.getTag();
            Ctitle = (TextView) convertView.findViewById(R.id.cellTitle);
        }
        if(tableType == 1) {
            TagHolder.Save.setTag(position);
            TagHolder.Save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    saveTeacher((int)v.getTag());
                }
            });
            TagHolder.Cell.setTag(position);
            TagHolder.Cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.displayWebpage(getItem((int)v.getTag()).description);
                }
            });
        }
        else if(tableType == 2) {
            TagHolder.Delete.setTag(position);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteTeacher((int)v.getTag());
                    //Adds default case if there are no more saved Teachers
                    if(Teachers.size() == 0)
                        Teachers.add(new InfoArticle("Slide to Save Teachers", ""));
                    notifyDataSetChanged();
                }
            });
            TagHolder.Cell.setTag(position);
            TagHolder.Cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.displayWebpage(getItem((int)v.getTag()).description);
                }
            });
        }
        Typeface customFont = Typeface.SERIF;
        Ctitle.setTypeface(customFont);
        Ctitle.setText(title);
        return convertView;
    }
    public Filter getFilter() {
        return new Filter() {

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<InfoArticle> results = new ArrayList<InfoArticle>();
                if (orig == null)
                    orig = Teachers;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final InfoArticle g : orig) {
                            if (g.title.toLowerCase()
                                    .contains(constraint.toString().toLowerCase())) {
                                results.add(g);
                            }

                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,
                                          FilterResults results) {
                Teachers = (ArrayList<InfoArticle>)results.values;
                notifyDataSetChanged();
            }
        };
    }
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return Teachers.size();
    }

    @Override
    public InfoArticle getItem(int position) {
        return Teachers.get(position);
    }

    public void saveTeacher(int position){
        SharedPreferences sharedPref = getContext().getSharedPreferences("MY_PREFERENCES",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<InfoArticle>>() {}.getType();
        ArrayList<InfoArticle> prefs = gson.fromJson(sharedPref.getString("teachPreferences",""),type);
        if(prefs == null) {
        prefs = new ArrayList<InfoArticle>();
        }
        prefs.add(Teachers.get(position));
        String json = gson.toJson(prefs);
        editor.putString("teachPreferences", json);
        editor.apply();
        new AlertDialog.Builder(getContext())
                .setTitle("Teacher Saved")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        swipeV.close(true);
    }
    private void deleteTeacher(int position){
        SharedPreferences sharedPref = getContext().getSharedPreferences("MY_PREFERENCES",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<InfoArticle>>() {}.getType();
        ArrayList<InfoArticle> prefs = gson.fromJson(sharedPref.getString("teachPreferences",""),type);
        if(prefs != null)
        {
            prefs.remove(position);
            Teachers.remove(position);
        }
        String json = gson.toJson(prefs);
        editor.putString("teachPreferences", json);
        editor.apply();
        new AlertDialog.Builder(getContext())
                .setTitle("Teacher Deleted")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                }).setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        swipeV.close(true);
        if(Teachers.size() == 0)
            Teachers.add(new InfoArticle("Slide To Save Teachers",""));
    }

    //Holder class to save specific childs for a list cell
    class Holder{
        TextView Delete;
        TextView Save;
        TextView Cell;
    }
}
