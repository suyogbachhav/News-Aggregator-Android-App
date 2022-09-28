package com.example.newsaggregator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class NewsArtclDownloader implements Runnable {

    public static MainActivity ma;
    private static String newsString;

    private static final String firstUrl= "https://newsapi.org/v2/top-headlines?sources=" ;
    private static final String APIKey= "&apiKey=92d3b7ca1743413d89f57940f28a2a1b";

    NewsArtclDownloader(MainActivity main, String sourceOfNews) {
        this.ma= main;
        this.newsString= sourceOfNews;

    }

    @Override
    public void run(){
        String urlToUse= firstUrl+ newsString+ APIKey;
        StringBuilder sb= new StringBuilder();
        try {
            URL url= new URL(urlToUse);

            // connect to http
            HttpsURLConnection connection= (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.addRequestProperty("User-Agent","");
            connection.connect();

            // if http connection is set
            if (connection.getResponseCode()!= HttpsURLConnection.HTTP_OK) {
                handleResults(null);
                return;

            }
            InputStream inStr= connection.getInputStream();
            BufferedReader BuffRead= new BufferedReader((new InputStreamReader(inStr)));

            // read each line
            String line;
            while ((line= BuffRead.readLine())!= null) {
                sb.append(line).append('\n');

            }
        } catch (Exception e) {
            handleResults(null);
            return;

        }
        handleResults(sb.toString());

    }

    public void handleResults(final String jsonStr) {
        final ArrayList<Artcl> ar= jsonArticleParse(jsonStr);
        ma.runOnUiThread(() -> ma.addArticle(ar));

    }

    //parsing json
    private ArrayList<Artcl> jsonArticleParse(String s) {
        try {
            JSONObject jsonMainObj= new JSONObject(s);
            JSONArray articalArray= jsonMainObj.getJSONArray("articles");
            ArrayList<Artcl> artcls = new ArrayList<>();

            int i = 0;
            while( i < articalArray.length()) {
                JSONObject specific_article= (JSONObject) articalArray.get(i);

                // get all information about news
                JSONObject source= (JSONObject) specific_article.getJSONObject("source");
                String name = source.getString("name");
                String authorName= specific_article.getString("author");
                String newsTitle= specific_article.getString("title");
                String newsDescription= specific_article.getString("description");
                String url= specific_article.getString("url");
                String urlToImage= specific_article.getString("urlToImage");
                String publisherName= specific_article.getString("publishedAt");
                String newsContent= specific_article.getString("content");

                Artcl artcl = new Artcl(authorName,newsTitle,newsDescription,url,urlToImage,publisherName,newsContent, name);
                artcls.add(artcl);

                i++;
            }
            return artcls;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
