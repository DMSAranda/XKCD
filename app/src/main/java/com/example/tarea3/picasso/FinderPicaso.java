package com.example.tarea3.picasso;

import android.content.Context;

import com.example.tarea3.database.Comic;
import com.example.tarea3.views.Viewer;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class FinderPicaso {

    private final Viewer context;

    public FinderPicaso(Viewer context) {
        this.context = context;
    }

    public  void picassoCache(Comic comic, Viewer context){

        Picasso.get()
                .load(comic.getImg())
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(context.image);

    }

    public  void picassoNoCache(Comic comic, Viewer context){

        Picasso.get()
                .load(comic.getImg())
                //    .error(R.drawable.error)
                //    .placeholder(R.drawable.placeholder)
                .into(context.image);

    }


}
