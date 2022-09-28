package com.example.newsaggregator;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderArtcl extends RecyclerView.ViewHolder{

        TextView date;
        TextView header;
        ImageView artclImage;
        TextView pgNum;
        TextView artclContent;
        TextView author;


public ViewHolderArtcl(@NonNull View itemView){
        super(itemView);
        date = itemView.findViewById(R.id.date);
        header = itemView.findViewById(R.id.header);
        artclImage = itemView.findViewById(R.id.ArticleImage);

        pgNum = itemView.findViewById(R.id.count);

        artclContent = itemView.findViewById(R.id.summary);
        author = itemView.findViewById(R.id.author);
        }
}
