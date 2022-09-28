package com.example.newsaggregator;

import java.util.ArrayList;

public class SourceOfNews {
    private String uniqueKey;
    private String nameNews;
    private String ctgryNews;
    private String linkNews;
    private ArrayList<Artcl> artclList= new ArrayList<>();

    public SourceOfNews(String uniqueKey, String n, String c, String l){
        setUniqueKey(uniqueKey);
        setNameNews(n);
        setCtgryNews(c);
        setLinkNews(l);
    }

    public void setNameNews(String nameNews) {
        this.nameNews= nameNews;
    }
    public void setLinkNews(String linkNews) {
        this.linkNews= linkNews;
    }
    public void setCtgryNews(String ctgryNews) {
        this.ctgryNews= ctgryNews;
    }
    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey= uniqueKey;
    }
    public String getUniqueKey() {
        return uniqueKey;
    }
    public String getCtgryNews() {
        return ctgryNews;
    }
    public String getNameNews() {
        return nameNews;
    }
    public void addArticle(Artcl a){
        artclList.add(a);
    }
    public ArrayList<Artcl> getArtclList(){
        return artclList;
    }
    public String getLinkNews() {
        return linkNews;
    }

    @Override
    public String toString() {
        return nameNews;
    }
}
