package layout;


import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tble.brgo.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContentDisplay#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContentDisplay extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private String title;
    private String description;


    public ContentDisplay() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ContentDisplay.
     */
    // TODO: Rename and change types and number of parameters
    public static ContentDisplay newInstance(String t, String d) {
        ContentDisplay fragment = new ContentDisplay();
        fragment.title = t;
        fragment.description = d;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_content_dispay, container, false);
        TextView titlebox = (TextView)v.findViewById(R.id.dTitle);
        titlebox.setText(title);
        titlebox.setTypeface(Typeface.SERIF);
        titlebox.setTextSize(19);
        titlebox.setBackgroundResource(R.color.colorPrimaryDark);
        titlebox.setTextColor(Color.WHITE);
        TextView descbox = (TextView)v.findViewById(R.id.dDescription);
        descbox.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        descbox.setText(description);
        descbox.setTypeface(Typeface.SERIF);
        descbox.setTextSize(14);
        return v;
    }

}
