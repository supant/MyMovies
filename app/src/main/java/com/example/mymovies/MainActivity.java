package com.example.mymovies;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.data.Chaine;
import com.example.data.LesListes;
import com.example.data.Programme;
import com.example.parser.LectureXML;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Button button;
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    private List<Programme> movieList = new ArrayList<>();
    private LesListes ll ;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_trash:
                    if(ll!=null) {
                        ll.remplirPoubelle();
                        mAdapter.notifyDataSetChanged();
                    }
                    return true;
                case R.id.navigation_movie:
                    if(ll!=null) {
                        ll.remplirFilms();
                        mAdapter.notifyDataSetChanged();
                    }
                    return true;
                case R.id.navigation_tosee:
                    if(ll!=null) {
                        ll.remplirAvoir();
                        mAdapter.notifyDataSetChanged();
                    }
                    return true;
                case R.id.navigation_saw:
                    if(ll!=null) {
                        ll.remplirVu();
                        mAdapter.notifyDataSetChanged();
                    }
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextMessage = (TextView) findViewById(R.id.txtNombre);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_movie);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                clickRefreshButton();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        mAdapter = new MoviesAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        ll=new LesListes(this);
        ll.remplirFilms();

    }

    private void clickRefreshButton() {
        mTextMessage.setText("clicbutton");
    }

    public List<Programme> getMovieList() {
        return movieList;
    }
    public void setNombre(String nb) {
        mTextMessage.setText(nb);
    }


    private void prepareMovieData() {
        Programme p;
        Chaine c = new Chaine("3");
        c.setsNom("Canal");

        p=new Programme("20190101210012","20190101213011");
        p.setChaine(c);p.setTitre("bob");
        movieList.add(p);
        p=new Programme("20190101210012","20190101213011");
        p.setChaine(c);p.setTitre("bob");
        movieList.add(p);

        p=new Programme("20190101210012","20190101213011");
        p.setChaine(c);
        p.setTitre("bob");
        p.setImage("https://static.playmedia-cdn.net/img/tv_programs/544501-545000/544769_xlarge.jpg");
        p.setStyle("Horreur");
        movieList.add(p);

        p=new Programme("20190101210012","20190101213011");
        p.setChaine(c);p.setTitre("bob");
        movieList.add(p);

        p=new Programme("20190101210012","20190101213011");
        p.setChaine(c);p.setTitre("bob");
        movieList.add(p);

        p=new Programme("20190101210012","20190101213011");
        p.setChaine(c);p.setTitre("bob");
        movieList.add(p);

        p=new Programme("20190101210012","20190101213011");
        p.setChaine(c);p.setTitre("bob");
        movieList.add(p);

        p=new Programme("20190101210012","20190101213011");
        p.setChaine(c);p.setTitre("bob");
        movieList.add(p);

    }



}
