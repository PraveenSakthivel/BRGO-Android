package com.tble.brgo;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.net.URLConnection;
import java.util.prefs.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
//import org.apache.http.HttpEntity;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import java.net.HttpURLConnection;
import javax.net.ssl.HttpsURLConnection;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.OutputStream;
import java.net.URL;
import com.google.gson.stream.JsonReader;

public class HTMLPull {
    public ArrayList<InfoArticle> Results = new ArrayList<InfoArticle>();
    String id = "";
    public static void main(String[] args) throws IOException{
        // TODO Auto-generated method stub

    }
    /**
     *
     * @param Rssfeed takes in url to XML file
     * @return array list containing an array of all title elements and a array of all description elements
     * @throws IOException
     */
    public HTMLPull(){

    }
    public ArrayList XmlPull(String Rssfeed, int school) throws IOException{
        ArrayList<InfoArticle> fullnews = new ArrayList<InfoArticle>();
        ArrayList<String> newstitle = new ArrayList<String>();
        ArrayList<String> newsdesc = new ArrayList<String>();
        String rssFeedUrl = convertForSchool(Rssfeed, school);
        Document doc = Jsoup.connect(rssFeedUrl).userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                .referrer("http://www.google.com").parser(Parser.xmlParser()).get();

        Elements items = doc.select("item");

        for (Element item : items) {
            String title = extractData(item, "title", "<NO TITLE>");
            String description = extractData(item, "description", "<NO DESCRIPTION>");

            /**if (description.endsWith("... (Continued)")) {
                // Fetch full description
                String newsUrl = extractData(item, "guid", null);
                description += " [UNABLE TO GET FULL DESCRIPTION]";

                if (newsUrl != null) {
                    Document news = Jsoup.connect(newsUrl).get();
                    Element newsContent = news.select("#content > table > tbody > tr > td").first();

                    if (newsContent != null) {
                        Elements tmp = newsContent.select("span.sw-newsHeader");
                        title = tmp.text();
                        tmp.remove();

                        description = newsContent.text();
                    }
                }
            }
*/
            fullnews.add(new InfoArticle(title,description));
        }
        return fullnews;
    }

    /**
     *
     * @param item taken from JSoup parse of webpage
     * @param dataName Item to be extracted
     * @param defaultValue default value of item if there are no items
     * @return dataValue of a dataName item in parameter item
     */
    private String extractData(Element item, String dataName, String defaultValue) {
        Element data = item.select(dataName).first();
        String dataValue;

        if (data == null) {
            dataValue = defaultValue;
        } else {
            dataValue = data.text();
        }

        return dataValue;
    }
    /**
     *
     * @return the name of all faculty members of the school saved in the School.txt file
     * @throws IOException
     */
    public void getStaff(int School) throws IOException
    {   ArrayList<String> a = new ArrayList<String>();
        URL url = new URL("https://app.oncoursesystems.com/json.axd/direct/router");

        HttpURLConnection conn=  (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestProperty( "Content-Type", "application/JSON");
        conn.setRequestMethod("POST");
        String data = String.format("{\"action\":\"Websites\",\"method\":\"school_webpage\",\"data\":[{\"schoolId\": %d}],\"type\":\"rpc\",\"tid\":2}", School);
        byte[] outputInBytes = data.getBytes("UTF-8");
        OutputStream os = new BufferedOutputStream(conn.getOutputStream());
        os.write( outputInBytes );
        os.flush();
        os.close();
        try {

            InputStream inStream = conn.getInputStream();
            readJsonStream(inStream);
        }
        catch(IOException ex){
           System.out.println("Message: " + conn.getResponseMessage());
            System.out.println(conn.getResponseCode());
            conn.getPermission();
            System.out.println("{Error}:" + ex);
        }
    }

    /**
     *
     * @return an ArrayList of teacher website links based on the school value in school.txt
     * @throws IOException
     */
    public void readJsonStream(InputStream in) throws IOException {
        JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        try {
            readMessage(reader);
        } finally {
            reader.close();
        }
    }

    public void readArray(JsonReader reader) throws IOException {

        reader.beginArray();
        while (reader.hasNext()) {
            readMessage(reader);
        }
        reader.endArray();
        //return messages;
    }

    public void readMessage(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("result")) {
                readMessage(reader);
            }
            else if(name.equals("ReturnValue"))
            {
                readMessage(reader);
            }
            else if(name.equals("tree"))
            {
                readArray(reader);
            }
            else if (name.equals("children"))
            {
                readArray(reader);
            }
            else if (name.equals("id")){
                id = "https://www.oncoursesystems.com/school/webpage/" + reader.nextString() + "/689493";
            }
            else if (name.equals("text")) {
                InfoArticle ab = new InfoArticle(reader.nextString(),id);
                Results.add(ab);
            }

            else {
                reader.skipValue();
            }
        }
        reader.endObject();
    }
    public static String convertForSchool(String url, int School){
        switch(School)
        {
            case 14273:
                url = "hs." + url;
                break;
            case 14276:
                url = "ms." + url;
                break;
            case 14274:
                url = "hi." + url;
                break;
            case 14271:
                url = "ei." + url;
                break;
            case 14278:
                url = "vh." + url;
                break;
            case 14277:
                url = "mi." + url;
                break;
            case 14275:
                url = "jfk." + url;
                break;
            case 14272:
                url = "ha." + url;
                break;
            case 14269:
                url = "cr." + url;
                break;
            case 14268:
                url = "bg." + url;
                break;
            case 14264:
                url = "ad." + url;
                break;
            default:
                url = "hs." + url;
                break;
        }
        url = "http://www." + url;
        return url;
    }

}
