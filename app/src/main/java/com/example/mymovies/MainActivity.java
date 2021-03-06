package com.example.mymovies;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.data.Chaine;
import com.example.data.LesListes;
import com.example.data.Programme;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private TextView mTextMessage;
    private Button button;
    private RecyclerView recyclerView;
    private MoviesAdapter mAdapter;
    private List<Programme> movieList = new ArrayList<>();
    private LesListes ll ;
    private Spinner spinnerChaine ;
    private Spinner spinnerDate ;

    private Menu menuHaut;


    private ProgressDialog mProgressDialog;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_trash:
                    if(ll!=null) {
                        ll.setFiltreStatut(Programme.poubelle);
                        ll.remplirFilms();
                        mAdapter.notifyDataSetChanged();
                    }
                    return true;
                case R.id.navigation_movie:
                    if(ll!=null) {
                        ll.setFiltreStatut(Programme.pasdefiltre);
                        ll.remplirFilms();
                        mAdapter.notifyDataSetChanged();
                    }
                    return true;
                case R.id.navigation_tosee:
                    if(ll!=null) {
                        ll.setFiltreStatut(Programme.avoir);
                        ll.remplirFilms();
                        mAdapter.notifyDataSetChanged();
                    }
                    return true;
                case R.id.navigation_saw:
                    if(ll!=null) {
                        ll.setFiltreStatut(Programme.vu);
                        ll.remplirFilms();
                        mAdapter.notifyDataSetChanged();
                    }
                    return true;

            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        this.menuHaut = menu;
        changeNavigation_Icon();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_infos:
                clickRefreshButton();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.navigation_movie);

        //recycler view
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        mAdapter = new MoviesAdapter(movieList,this);
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

        //Chargement
        ll=new LesListes(this);
        ll.remplirFilms();

        //filtres chaines
        spinnerChaine = (Spinner) findViewById(R.id.spinnerchaine);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ll.getListChaine());
        //mise en forme du spinner ouvert
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChaine.setAdapter(dataAdapter);
        //action on click
        spinnerChaine.setOnItemSelectedListener(this);
        //filtres date
        spinnerDate = (Spinner) findViewById(R.id.spinnerdate);
        ArrayAdapter<String> dataAdapterdate = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, ll.getDates());
        //mise en forme du spinner ouvert
        dataAdapterdate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDate.setAdapter(dataAdapterdate);
        //action on click
        spinnerDate.setOnItemSelectedListener(this);

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
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setProgress(0);
        mProgressDialog.dismiss();

        //Remettre tout à jour
        if (spinnerDate!=null) spinnerDate.setSelection(0);
        if (spinnerChaine!=null) spinnerChaine.setSelection(0);

        ll=new LesListes(MainActivity.this);
        ll.setFiltreStatut(Programme.pasdefiltre);
        ll.setFiltreChaine(null);
        ll.setFiltreDate(null);
        ll.remplirFilms();

        //mAdapter = new MoviesAdapter(movieList);
        changeNavigation_Icon();

        mAdapter.notifyDataSetChanged();

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
            mProgressDialog.setIndeterminate(false);
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
                    mProgressDialog.setProgress((int) (total * 100 / fileLength));
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

// Gestion du navigate bouton 0-6
    public void changeNavigation_Icon() {
        //ll.getFin().differenceJourInv()
        MenuItem menuItem = menuHaut.findItem(R.id.navigation_infos);
        int nbJour = ll.getFin().differenceJourInv();
        if (nbJour<=0) menuItem.setIcon(getResources().getDrawable(R.drawable.baseline_trip_origin_24px));
        if (nbJour==1) menuItem.setIcon(getResources().getDrawable(R.drawable.baseline_looks_one_24px));
        if (nbJour==2) menuItem.setIcon(getResources().getDrawable(R.drawable.baseline_looks_two_24px));
        if (nbJour==3) menuItem.setIcon(getResources().getDrawable(R.drawable.baseline_looks_3_24px));
        if (nbJour==4) menuItem.setIcon(getResources().getDrawable(R.drawable.baseline_looks_4_24px));
        if (nbJour==5) menuItem.setIcon(getResources().getDrawable(R.drawable.baseline_looks_5_24px));
        if (nbJour>=6) menuItem.setIcon(getResources().getDrawable(R.drawable.baseline_looks_6_24px));
    }

    // Gestion des clics sur les spinner
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {

        String chaineSp = (String) spinnerChaine.getSelectedItem();
        String dateSp = (String) spinnerDate.getSelectedItem();
        ll.setFiltreChaine(chaineSp);
        ll.setFiltreDate(dateSp);
        ll.remplirFilms();
        mAdapter.notifyDataSetChanged();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

}
