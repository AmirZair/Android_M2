package com.example.film_amir;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {

    private List<Movie> movieList;
    private Context context;

    public MovieAdapter(List<Movie> movieList, Context context) {
        super(context, 0, movieList);
        this.movieList = movieList;
        this.context = context;
    }

    @Override
    public View getView(int position, View recup, ViewGroup parent) {

        if (recup == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            recup = inflater.inflate(R.layout.row, parent, false);
        }

        TextView title = (TextView) recup.findViewById(R.id.title);
        TextView year = (TextView) recup.findViewById(R.id.year);
        TextView director = (TextView) recup.findViewById(R.id.director);
        TextView producer = (TextView) recup.findViewById(R.id.producer);
        TextView cost = (TextView) recup.findViewById(R.id.cost);
        ImageView imageView = (ImageView) recup.findViewById(R.id.image);

        Movie f = this.movieList.get(position);

        title.setText(f.getTitle());
        year.setText(f.getYear());
        director.setText(f.getDirector());
        producer.setText(f.getProducer());
        cost.setText(f.getCost());
        if(f.getImage() != null)
            imageView.setImageBitmap(f.getImage());

        return recup;
    }


}
