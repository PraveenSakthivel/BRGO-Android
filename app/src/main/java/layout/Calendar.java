package layout;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.os.StrictMode;
import com.tble.brgo.HTMLPull;
import com.tble.brgo.InfoArticle;
import com.tble.brgo.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link Calendar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Calendar extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ArrayList<InfoArticle> fDataSet;
    public StandardCellAdapter mAdapter;
    public ListView mListView;
    public Calendar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Calendar.
     */
    // TODO: Rename and change types and number of parameters
    public static Calendar newInstance() {
        Calendar fragment = new Calendar();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ArrayList<InfoArticle> data = new ArrayList<InfoArticle>();
        try {
           data = getData();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        fDataSet = data;
        mListView = (ListView) view.findViewById(R.id.CalendarTable);
        mAdapter = new StandardCellAdapter(getActivity(), data, 0,null,null);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ContentDisplay fragment = ContentDisplay.newInstance(fDataSet.get(position).title,fDataSet.get(position).description);
                FragmentTransaction transfer = getActivity().getSupportFragmentManager().beginTransaction();
                transfer.replace(R.id.fragmentcontainer, fragment).addToBackStack("tag").commit();
            }
        });
        mListView.setAdapter(mAdapter);
        final SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.calRefresh);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    fDataSet = getData();
                    mAdapter = new StandardCellAdapter(getContext(), fDataSet, 0,null,null);
                    mListView.setAdapter(mAdapter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                swipeLayout.setRefreshing(false);
            }
        });
        return view;
    }
    public ArrayList<InfoArticle> getData() throws IOException{
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        HTMLPull Connection = new HTMLPull();
        return Connection.XmlPull("brrsd.org/apps/events2/events_rss.jsp?id=0",sharedPref.getInt("School", 14273));
    }
}
