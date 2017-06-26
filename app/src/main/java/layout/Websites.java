package layout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.os.StrictMode;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tble.brgo.HTMLPull;
import com.tble.brgo.InfoArticle;
import com.tble.brgo.R;
import android.widget.AdapterView.OnItemClickListener;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import android.text.TextUtils;
import java.util.HashSet;
import java.util.Set;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Websites#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Websites extends Fragment implements SearchView.OnQueryTextListener, displayInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private SearchView mSearchView;
    private ListView mListView;
    public Boolean toggle = false;
    public ArrayList<InfoArticle> mData = new ArrayList<InfoArticle>();
    public StandardCellAdapter mAdapter;
    public ArrayList<InfoArticle> fDataSet = new ArrayList<InfoArticle>();
    public Websites webTable;
    public Websites() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment Websites.
     */
    // TODO: Rename and change types and number of parameters
    public static Websites newInstance() {
        Websites fragment = new Websites();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        toggle = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_websites, container, false);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ArrayList<InfoArticle> data = new ArrayList<InfoArticle>();
        webTable = this;
        try {
            data = getData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mListView = (ListView) view.findViewById(R.id.WebsiteTable);
        final StandardCellAdapter adapter = new StandardCellAdapter(getActivity(), data, 1,this,null);
        fDataSet = data;
        mData = data;
        mListView.setAdapter(adapter);
        mAdapter = adapter;
        mSearchView = (SearchView) view.findViewById(R.id.searchView);
        mListView.setTextFilterEnabled(true);
        FloatingActionButton myFab = (FloatingActionButton) view.findViewById(R.id.dataToggle);
        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            toggleView();
            }
        });
        setupSearchView();
        final SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.webRefresh);
        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    mData = getData();
                    mAdapter = new StandardCellAdapter(getContext(), mData, 1,webTable,null);
                    mListView.setAdapter(mAdapter);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                swipeLayout.setRefreshing(false);
            }
        });
        return view;
    }

    private void setupSearchView() {
        mSearchView.setOnQueryTextListener(this);
        mSearchView.setIconifiedByDefault(true);
    }

    private ArrayList<InfoArticle> getData() throws IOException {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getContext());
        int school = sharedPref.getInt("School", 14273);
        HTMLPull Connection = new HTMLPull();
        Connection.getStaff(school);
        Connection.Results.remove(0);
        return Connection.Results;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (TextUtils.isEmpty(newText)) {
            mListView.clearTextFilter();
        } else {
            mListView.setFilterText(newText);
        }
        return true;
    }

    public ArrayList<InfoArticle> toData(ArrayList<String> t, ArrayList<String> d) {
        ArrayList<InfoArticle> temp = new ArrayList<InfoArticle>();
        if (t.size() == 0) {
            temp.add(new InfoArticle("Slide to Save Teachers", "google.com"));
        } else {
            for (int i = 0; i < t.size(); i++) {
                temp.add(new InfoArticle(t.get(i), d.get(i)));
            }
        }
        return temp;
    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }
    private void toggleView(){
        if (!toggle) {
            SharedPreferences sharedPref = getContext().getSharedPreferences("MY_PREFERENCES",Context.MODE_PRIVATE);
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<InfoArticle>>() {}.getType();
            ArrayList<InfoArticle> prefs = gson.fromJson(sharedPref.getString("teachPreferences",""),type);
            if(prefs==null) {
                prefs = new ArrayList<InfoArticle>();
                prefs.add(new InfoArticle("Slide To Save Teachers",""));
            }
            mAdapter = new StandardCellAdapter(getContext(),prefs,2,this,null);
            mListView.setAdapter(mAdapter);
            toggle = true;
        } else {
            mAdapter = new StandardCellAdapter(getContext(), mData, 1,this,null);
            mListView.setAdapter(mAdapter);
            toggle = false;
        }
    }
    public void displayWebpage(String Link){
        WebsiteDisplay fullView = WebsiteDisplay.newInstance(Link);
        FragmentTransaction transfer = getActivity().getSupportFragmentManager().beginTransaction();
        transfer.replace(R.id.fragmentcontainer, fullView).addToBackStack("tag").commit();
    }
}

