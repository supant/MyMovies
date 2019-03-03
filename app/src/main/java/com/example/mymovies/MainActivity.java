package com.example.mymovies;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
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

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private Button button;
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    private List<Programme> movieList = new ArrayList<>();
    private LesListes ll ;

    private ProgressDialog mProgressDialog;

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
                case R.id.navigation_tonight:
                    if(ll!=null) {
                        ll.remplirCeSoir();
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
                        //ll.savLocal();
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

        //progress Bar
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("Downloading");
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setCancelable(true);

        ll=new LesListes(this);
        ll.remplirFilms();
        button.setText(ll.getFin().differenceJourInv()+"");

    }

    private void clickRefreshButton() {
        final DownloadTask downloadTask = new DownloadTask(this);
        downloadTask.execute("http://allfrtv.gq/alacarte/alacarte.php?key=292acb14c42536c2ca4f4bf7e7b91b5b");
        mProgressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                downloadTask.cancel(true); //cancel the task
            }
        });
    }

    public List<Programme> getMovieList() {
        return movieList;
    }
    public void setNombre(String nb) {
        mTextMessage.setText(nb);
    }



//Download
private class DownloadTask extends AsyncTask<String, Integer, String> {

    private Context context;
    private PowerManager.WakeLock mWakeLock;

    public DownloadTask(Context context) {
        this.context = context;
    }

    protected void onPreExecute(){
        mProgressDialog.show();
    }

    protected void onPostExecute(String result) {
        mProgressDialog.dismiss();
    }

    @Override
    protected String doInBackground(String... sUrl) {
        //plusieurs args de String

        InputStream input = null;
        FileOutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(sUrl[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // expect HTTP 200 OK, so we don't mistakenly save error report
            // instead of the file
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }
            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = connection.getContentLength();
            if (fileLength <= 0) fileLength=900000;
            // download the file
            input = connection.getInputStream();
            output = openFileOutput("pgtv.xmltv", Context.MODE_PRIVATE);

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                // allow canceling with back button
                if (isCancelled()) {
                    input.close();
                    return null;
                }
                total += count;

                // publishing the progress....
                if (fileLength > 0) {// only if total length is known
                    publishProgress((int) (total * 100 / fileLength));
                    //Log.i("bob","Progress "+(int) (total * 100 / fileLength));
                }
                output.write(data, 0, count);
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }
        Log.i("bob","fin DL");
        return null;
    }
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
