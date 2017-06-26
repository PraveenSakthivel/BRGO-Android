package layout;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tble.brgo.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WebsiteDisplay#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebsiteDisplay extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
  private String Weblink;
    private WebView web;

    public WebsiteDisplay() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WebsiteDisplay.
     */
    // TODO: Rename and change types and number of parameters
    public static WebsiteDisplay newInstance(String link) {
        WebsiteDisplay fragment = new WebsiteDisplay();
        fragment.Weblink = link;
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
        View v = inflater.inflate(R.layout.fragment_website_display, container, false);
        web = (WebView)v.findViewById(R.id.teachWeb);
        web.setWebViewClient(new WebViewClient());
        web.getSettings().setBuiltInZoomControls(true);
        web.canGoBack();
        web.canGoForward();
        web.getSettings().setJavaScriptEnabled(true);
        web.loadUrl(Weblink);
        FloatingActionButton fab = (FloatingActionButton)v.findViewById(R.id.forWeb);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.goForward();
            }
        });
        fab = (FloatingActionButton)v.findViewById(R.id.backWeb);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                web.goBack();
            }
        });
        return v;
    }

}
