package com.example.newsaggregator;

import android.content.Intent;
import android.net.Uri;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterArtcl extends RecyclerView.Adapter<ViewHolderArtcl>{
    private final MainActivity main;
    private final ArrayList<Artcl> artclist;

    public AdapterArtcl(MainActivity main, ArrayList<Artcl> artcls){
        this.main= main;
        this.artclist = artcls;
    }

    @NonNull
    @Override
    public ViewHolderArtcl onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolderArtcl(
                LayoutInflater.from(parent.getContext()).
                        inflate(R.layout.article_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderArtcl holder, int postn) {
        Artcl artcl= artclist.get(postn);
        holder.header.setText(artcl.getTitle());


        holder.date.setText(artcl.dateTimeZulu());
        holder.author.setText(artcl.getAuthr());
        holder.artclImage.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        Picasso.get().load(artcl.getUrlToImage()).placeholder(R.drawable.noimage).error(R.drawable.brokenimage).placeholder(R.drawable.loading).into(holder.artclImage);
        holder.artclImage.setOnClickListener(var -> imageClick(artcl.getUrl()));

        holder.artclContent.setMovementMethod(new ScrollingMovementMethod());

        holder.artclContent.setText(artcl.getSumm());

        holder.header.setOnClickListener(var -> imageClick(artcl.getUrl()));
        holder.artclContent.setOnClickListener(var -> imageClick(artcl.getUrl()));

        int pos= postn+ 1;
        String page = new StringBuilder().append(pos).append(" out of ").append(getItemCount()).toString();

        holder.pgNum.setText(page);
    }
    @Override
    public int getItemCount() {
        return artclist.size();
    }


    private void imageClick(String url){
        Intent browserIntent= new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        main.startActivity(browserIntent);
    }

}
