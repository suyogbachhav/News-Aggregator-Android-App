package com.example.newsaggregator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LayoutMngr implements Serializable {

    private int source;
    private int artcl;

    private List<SourceOfNews> sorcs = new ArrayList<>();
    private List<String> langs = new ArrayList<>();
    private List<Artcl> artcls = new ArrayList<>();
    private List<String> catgory = new ArrayList<>();
    private List<String> countries= new ArrayList<>();


    public void setSource(int source) {
        this.source= source;
    }
    public void setArtcl(int artcl) {
        this.artcl = artcl;
    }

    public void setSorcs(List<SourceOfNews> sorcs) {
        this.sorcs = sorcs;
    }
    public List<SourceOfNews> getSorcs() {
        return sorcs;
    }


    public void setCatgory(List<String> catgory) {
        this.catgory= catgory;
    }
    public List<String> getCatgory() {
        return catgory;
    }


    public void setCountries(List<String> countries) {
        this.countries= countries;
    }
    public List<String> getCountries() {
        return countries;
    }


    public void setArtcls(List<Artcl> artcls) {
        this.artcls = artcls;
    }
    public List<Artcl> getArtcls() {
        return artcls;
    }


    public void setLangs(List<String> langs) {
        this.langs = langs;
    }
    public List<String> getLangs() {
        return langs;
    }


}