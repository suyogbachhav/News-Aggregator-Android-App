package com.example.newsaggregator;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NewsSourceDownloader {
    public static MainActivity ma;
    private static RequestQueue resquestQ;

    private static String urlToUse;
    private static final String APIKey= "92d3b7ca1743413d89f57940f28a2a1b";
    private static final String link= "https://newsapi.org/v2/sources?apiKey=";

    private static boolean all= false;

    // download news topics
    public static void downloadTopics(MainActivity main){
        ma= main;
        resquestQ= Volley.newRequestQueue(ma);
        urlToUse= link+ APIKey;

        Response.Listener<JSONObject> listener=
                response -> topicParser(response.toString());
        Response.ErrorListener error=
                error1 -> ma.ErrorDownload();

        JsonObjectRequest jsonObjectRequest=
                new JsonObjectRequest(Request.Method.GET, urlToUse,
                        null, listener, error) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String, String> h= new HashMap<>();
                        h.put("User-Agent", "val");
                        return h;

                    }
                };
        resquestQ.add(jsonObjectRequest);
    }


    private static void topicParser(String s) {
        try {
            MainActivity.tpcs.clear();
            // Add All Topics
            MainActivity.tpcs.add("All");
            JSONObject jMain= new JSONObject(s);
            JSONArray sources= jMain.getJSONArray("sources");
            int t= 0;
            while( t < sources.length() ) {

                JSONObject sourceSpec= sources.getJSONObject(t);
                String idSpec= sourceSpec.getString("id");
                String nameSpec= sourceSpec.getString("name");
                String topicSpec= sourceSpec.getString("category");
                String urlSpec= sourceSpec.getString("url");

                MainActivity.newsAllItems.add(new SourceOfNews(idSpec, nameSpec, topicSpec, urlSpec));
                MainActivity.addTopic(topicSpec);

                t++;
            }
            ma.loadDrawer(true);
            ma.makeMenu();
            ma.changeTitle(sources.length());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
