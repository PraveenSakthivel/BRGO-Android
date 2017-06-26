package layout;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import com.tble.brgo.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Handbook extends Fragment {


    public Handbook() {
        // Required empty public constructor
    }
    public static Handbook newInstance(){
        Handbook fragment = new Handbook();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_handbook, container, false);
        WebView mWebView = (WebView)v.findViewById(R.id.HandbookwebView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        SharedPreferences sharedPref =  getActivity().getPreferences(Context.MODE_PRIVATE);
        String url = "http://docs.google.com/gview?embedded=true&url=";
        switch (sharedPref.getInt("School",14273))
        {
            case 14273:
                url += "www.hs.brrsd.org/Forms%20and%20Documents//Opening%20of%20School%20Documents/Other%20Forms%20and%20Documents/Student%20Handbook%202016-2017.pdf";
                break;
            case 14276:
                url += "www.ms.brrsd.org/ourpages/auto/2015/9/12/66702192/Student%20Agenda%202016-2017.pdf";
                break;
            case 14274:
                url += "www.hi.brrsd.org/Forms%20and%20Documents//Student%20Handbook/Student%20Handbook.pdf";
                break;
            case 14264:
                url += "www.ad.brrsd.org/ourpages/auto/2015/8/24/43630268/Adamsville%20Student-Parent%20Handbook%202015-2016%20even%20further%20revisions.pdf";
                break;
            default:
                url += "www.hs.brrsd.org/Forms%20and%20Documents//Opening%20of%20School%20Documents/Other%20Forms%20and%20Documents/Student%20Handbook%202016-2017.pdf";
                break;
        }
        mWebView.getSettings().setBuiltInZoomControls(true);
        mWebView.canGoBack();
        mWebView.canGoForward();
        mWebView.loadUrl(url);
        return v;
    }

}
