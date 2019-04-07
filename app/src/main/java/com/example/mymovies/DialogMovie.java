package com.example.mymovies;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;

import com.example.data.Chaine;
import com.example.data.Programme;

public class DialogMovie extends Dialog implements android.view.View.OnClickListener {

    private Activity myActivity;
    private String txt;
    private String titre;
    private TextView txtView;
    private WebView webView;



    public DialogMovie(Activity activity) {
        super(activity);
        this.myActivity = activity;
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.dialogmovie_navigation_imdb:
                    txtView.setText(txt);
                    webView.loadUrl(Chaine.ENTETE_IMDB+titre+"\"");
                    return true;
                case R.id.dialogmovie_navigation_teleram:
                    txtView.setText(txt);
                    webView.loadUrl(Chaine.ENTETE_TELERAMA+titre+"\"");
                    return true;
                case R.id.navigation_tosee:
                    webView.loadUrl("");
                    return true;
                case R.id.dialogmovie_navigation_ok:
                    dismiss();
                    return true;

            }
            return false;
        }
    };


    public void setTxt(String txt,String titre) {
        this.titre=titre;
        this.txt = txt;
        if (txtView!=null) txtView.setText(txt);
        if (webView!=null)  webView.loadUrl("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_movie);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationDialogMovie);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(R.id.dialogmovie_navigation_ok);

        txtView = (TextView) findViewById(R.id.textViewDialogMovie);
        txtView.setText(txt);
        webView = (WebView) findViewById(R.id.webDialogMovie);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);

    }

    @Override
    public void onClick(View v) {

    }
}