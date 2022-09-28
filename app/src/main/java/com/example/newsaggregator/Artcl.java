package com.example.newsaggregator;

import android.annotation.SuppressLint;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class Artcl {

    private String authr;
    private String url;
    private String title;
    private String summ;
    private String date;
    private String urlToImage;
    private String newsTitle;
    private String content;

    public Artcl(String a, String t, String d, String url, String url_i, String date, String c, String newsTitle){
        this.newsTitle = newsTitle;
        setAuthr(a);
        setTitle(t);
        setSumm(d);
        setUrl(url);
        setUrlToImage(url_i);
        setDate(date);
        setContent(c);
    }

    public String getAuthr() {
        return authr;
    }

    public String getTitle() {
        return title;
    }

    public String getSumm() {
        return summ;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public void setAuthr(String authr) {
        if(authr.equals("null")){
            this.authr= newsTitle;
        }
        else{
            this.authr= authr;
        }

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setSumm(String summ) {
        if(authr.equals("null")){
            this.summ= "No description available";
        }
        else{
            this.summ= summ;
        }
    }

    public void setUrl(String url) {
        this.url= url;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage= urlToImage;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setContent(String content) {
        if(content.equals("null")){
            this.content= "";
        }
        else {
            this.content= content;
        }
    }

    @SuppressLint("NewApi")
    public String dateTimeZulu() {
        try {
            DateTimeFormatter parser= DateTimeFormatter.ISO_DATE_TIME;
            Instant instant= parser.parse(this.getDate(), Instant::from);
            LocalDateTime ldt= LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

            DateTimeFormatter dtf= DateTimeFormatter.ofPattern("MMM d, yyyy HH:mm");

            return ldt.format(dtf);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }


    @Override
    public String toString() {
        return "Article{" +
                "author='" +authr + '\'' +
                ", title='" +title + '\'' +
                ", desc='" +summ + '\'' +
                ", url='" +url + '\'' +
                ", urlToImage='" +urlToImage + '\'' +
                ", date='" +date + '\'' +
                ", content='" +content + '\'' +
                '}';
    }
}
