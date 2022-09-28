package com.example.newsaggregator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.viewpager2.widget.ViewPager2;
import androidx.annotation.NonNull;
import android.annotation.SuppressLint;
import android.app.Activity;
import androidx.drawerlayout.widget.DrawerLayout;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.view.MenuItem;
import android.text.style.ForegroundColorSpan;
import android.os.Bundle;
import android.content.res.Configuration;
import android.text.SpannableString;

import android.util.Log;
import android.net.NetworkInfo;
import android.view.Menu;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedHashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ViewPager2 pageViewr;
    private ListView drwrViewList;
    private TextView textView;
    private ActionBarDrawerToggle mDrwrToggle;
    private DrawerLayout mDrwrLyout;
    static final ArrayList<SourceOfNews> newsCrrntItems = new ArrayList<>();
    private AdapterArtcl artadap;
    static final ArrayList<SourceOfNews> newsAllItems = new ArrayList<>();
    private  ArrayList<Artcl> artclList = new ArrayList<>();
    private Menu menu;

    private static final String TAG= "MainActivity";
    static final ArrayList<String> tpcs = new ArrayList<>();
    private String [] colorPalette= {"#ad152e", "#d68c45", "#f4d35e", "#60785c", "#77a0a9", "#6c596e", "#ff8c9f"};
    String currentTitle;


    //////
    private int currentSourcePointer;
    private List<SourceOfNews> sources;
    private List<String> categories;
    private List<String> languages;
    private List<String> countries;
    private List<Artcl> artcls;
    private boolean appState;
    private Map<String, SourceOfNews> sourceStore;
    private List<String> sourceList;
    /////


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sources= new ArrayList<>();
        categories= new ArrayList<>();
        countries= new ArrayList<>();
        languages= new ArrayList<>();
        artcls = new ArrayList<>();
        sourceList= new ArrayList<>();
        sourceStore= new HashMap<>();

        drwrViewList= findViewById(R.id.drawerList);
        mDrwrLyout= findViewById(R.id.drawer_layout);


        drwrViewList.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_layout_item, newsAllItems));

        drwrViewList.setOnItemClickListener(
                (parent, view, pos, id) -> {
                    currentSourcePointer = pos;
                    selectItem(pos);
                    mDrwrLyout.closeDrawer(drwrViewList);
                }
        );

        mDrwrToggle= new ActionBarDrawerToggle(
                this,
                mDrwrLyout,
                R.string.drawer_open,
                R.string.drawer_close
        );

        if (getSupportActionBar()!= null) {  // <== Important!
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        artadap= new AdapterArtcl(this, artclList);
        pageViewr= findViewById(R.id.front_page);
        pageViewr.setAdapter(artadap);

    }

    // for menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu= menu;
        NewsSourceDownloader.downloadTopics(this);

        return super.onCreateOptionsMenu(menu);
    }

    // for selected item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrwrToggle.onOptionsItemSelected(item)) {
            return true;
        }
        int j = 0;
        while ( j < menu.size() ){
            if(menu.getItem(j).getTitle().equals(item.getTitle())){
              //  colorMenuOptions(item);
                reloadSources(tpcs.get(j));
                break;
            }
            j++;
        }
        return super.onOptionsItemSelected(item);
    }
// colors
//    private void colorMenuOptions(MenuItem item) {
//
//        String items = item.getTitle().toString();
//        if(items.equals("business")){
//            setColor(item,Color.BLUE);
//        }else if(items.equals("entertainment")){
//            setColor(item,Color.rgb(255,192,203));
//        }else if(items.equals("sports")){
//            setColor(item,Color.GREEN);
//        }else if(items.equals("science")){
//            setColor(item,Color.MAGENTA);
//        }else if(items.equals("technology")){
//            setColor(item,Color.YELLOW);
//        }else if(items.equals("general")){
//            setColor(item,Color.RED);
//        }else if(items.equals("health")){
//            setColor(item,Color.CYAN);
//        }
//
//    }
//
//    private void setColor(MenuItem item, int color) {
//        SpannableString spannableString;
//        spannableString = new SpannableString(item.getTitle());
//        spannableString.setSpan(new ForegroundColorSpan(color), 0, spannableString.length(), 0);
//        item.setTitle(spannableString);
//    }

    // to load topics from menu
    public void makeMenu() {
        menu.clear();
        tpcs.size();

        Set<String> set= new LinkedHashSet<String>();

        set.addAll(tpcs);
        tpcs.clear();
        tpcs.addAll(set);
        int k= 0;
        while( k< tpcs.size()){
            menu.add( tpcs.get(k) );
            if(k > 0){
                int newind= k-1;
                MenuItem itm= menu.getItem(k);
                SpannableString spanString= new SpannableString(itm.getTitle().toString());
                spanString.setSpan(new ForegroundColorSpan(Color.parseColor(colorPalette[newind])), 0, spanString.length(), 0); // fix the color to white
                itm.setTitle(spanString);
            }
            k++;
        }
        hideKeyboard();
    }

// getting news from source
    private void reloadSources(String s) {
        newsCrrntItems.clear();
        if(s.equals("All")){
            newsCrrntItems.addAll(newsAllItems);
        }
        else{
            int i= 0;
            while( i< newsAllItems.size()){
                if(newsAllItems.get(i).getCtgryNews().equals(s)){
                    newsCrrntItems.add(newsAllItems.get(i));
                }
                i++;
            }
        }
        changeTitle(newsCrrntItems.size());
        loadDrawer(false);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrwrToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrwrToggle.onConfigurationChanged(newConfig);
    }

    // to hide keyboard
    public void hideKeyboard() {
        InputMethodManager imm= (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (imm== null) return;
        View view = getCurrentFocus();
        if (view== null)
            view= new View(this);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        Log.d(TAG, "onSaveInstanceState: ");
        LayoutMngr layoutRestore = new LayoutMngr();

        Log.d(TAG, "categories: " + categories);
        layoutRestore.setCatgory(categories);

        Log.d(TAG, "sources: " + sources);
        layoutRestore.setSorcs(sources);

        layoutRestore.setArtcl(pageViewr.getCurrentItem());

        Log.d(TAG, "currentSourcePointer : " + currentSourcePointer);
        layoutRestore.setSource(currentSourcePointer);

        Log.d(TAG, "articles : " + artcls);
        layoutRestore.setArtcls(artcls);

        Log.d(TAG, "languages: "+languages);
        layoutRestore.setLangs(languages);

        Log.d(TAG,"countries :"+countries);
        layoutRestore.setCountries(countries);

        outState.putSerializable("state", layoutRestore);

        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        Log.d(TAG, "onRestoreInstanceState: ");
        super.onRestoreInstanceState(savedInstanceState);

        setTitle(R.string.app_name);
        LayoutMngr layoutMngr = (LayoutMngr) savedInstanceState.getSerializable("state");
        appState = true;

        artcls = layoutMngr.getArtcls();
        Log.d(TAG, "articles: " + artcls);

        categories = layoutMngr.getCatgory();
        Log.d(TAG, "categories: " + categories);

        sources = layoutMngr.getSorcs();
        Log.d(TAG, "sources: " + sources);

        languages= layoutMngr.getLangs();
        Log.d(TAG, "languages:"+languages);

        countries= layoutMngr.getLangs();
        Log.d(TAG, "countries:"+countries);

        int z = 0;
        while ( z < sources.size() ) {
            sourceList.add(sources.get(z).getNameNews());
            sourceStore.put(sources.get(z).getNameNews(), sources.get(z));
            z++;
        }

        drwrViewList.clearChoices();
        artadap.notifyDataSetChanged();
        drwrViewList.setOnItemClickListener((parent, view, position, id) -> {
                    pageViewr.setBackgroundResource(0);
                    currentSourcePointer = position;
                    selectItem(position);
                }
        );
    }

    private boolean hasInternet() {
        ConnectivityManager connectivityManager = getSystemService(ConnectivityManager.class);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnectedOrConnecting());
    }

    public void ErrorDownload() {
        Toast.makeText(MainActivity.this, R.string.error, Toast.LENGTH_SHORT).show();

    }

    @SuppressLint("NotifyDataSetChanged")
    private void selectItem(int position) {
        pageViewr.setBackground(null);
        currentTitle = newsCrrntItems.get(position).getNameNews();
        NewsArtclDownloader news2 = new NewsArtclDownloader(this, newsCrrntItems.get(position).getUniqueKey());
        new Thread(news2).start();

        mDrwrLyout.closeDrawer(drwrViewList);
    }

    public void updateData(Object o) {

    }

    public void loadDrawer(boolean all){
        if(all){
            newsCrrntItems.addAll(newsAllItems);
        }
        drwrViewList.setAdapter(new ArrayAdapter<>(this,
                R.layout.drawer_layout_item, newsCrrntItems));
        mDrwrLyout.setScrimColor(Color.WHITE);
    }

    public static void addTopic(String t){
        tpcs.add(t);
    }

    public void changeTitle(int num){
        String temp = new StringBuilder()
                .append(getString(R.string.app_name))
                .append(" (").append(num)
                .append(")").toString();
        setTitle(temp);
    }

    public void addArticle(ArrayList<Artcl> w) {
        artclList.clear();
        artclList.addAll(w);
        artadap.notifyDataSetChanged();
        setTitle(currentTitle);
        pageViewr.setCurrentItem(0);
    }

    public void changeTitle(String title){
        setTitle(title);
    }
}