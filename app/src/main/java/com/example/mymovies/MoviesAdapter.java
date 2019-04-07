package com.example.mymovies;

/**
 * Created by Antoine on 02/12/2017.
 */

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.data.Programme;
import com.squareup.picasso.Picasso;

import java.text.MessageFormat;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {

    private List<Programme> moviesList;
    private AlertDialog alertDialog;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titre, heure, genre,chaine, date;
        public ImageView photo;
        public View root;

        public MyViewHolder(View view) {
            super(view);
            titre = (TextView) view.findViewById(R.id.title);
            date = (TextView) view.findViewById(R.id.date);
            genre = (TextView) view.findViewById(R.id.genre);
            photo =(ImageView) view.findViewById(R.id.thumbnail);
            heure = (TextView) view.findViewById(R.id.hour);
            chaine = (TextView) view.findViewById(R.id.chaine);

            root = view;
        }
    }


    public MoviesAdapter(List<Programme> moviesList) {
        this.moviesList = moviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        alertDialog = new AlertDialog.Builder(parent.getContext()).create();

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Programme movie = moviesList.get(position);
        holder.titre.setText(movie.getTitre());
        holder.genre.setText(movie.getFirstStyle());
        holder.date.setText(movie.getStart().toStringJMA());
        holder.heure.setText(movie.getStart().toStringHM());
        //holder.tmp.setText(movie.getUrlDiff());
        holder.chaine.setText(movie.getChaine().getNom());

        String path = movie.getImage();
        if (path.length()>0)
            Picasso.with(holder.photo.getContext())
					.load(path)
                    .placeholder(R.drawable.imdb)
					.into(holder.photo);
            else holder.photo.setImageResource(R.drawable.imdb);


            holder.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.setMessage(movie.toStringIntro()+" "+movie.getDescription());
                    //alertDialog.setContentView(R.layout.movie_det);
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "IMDB",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //dialog.dismiss();
                                    Log.i("bob","Hooole"+which);
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "TELERAMA",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                }
            });


    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }





}