package com.example.data;

import com.example.mymovies.MainActivity;
import com.example.mymovies.R;
import com.example.parser.LectureXML;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LesListes {
    private MainActivity mainA;
    private List<Programme> listTotal = new ArrayList<>();
    private List<Programme> listFilm = new ArrayList<>();
    private List<Programme> listLocal = new ArrayList<>();


    public LesListes(MainActivity mainA) {
        this.mainA=mainA;

        //Verifier fichier existe (si non :load)

        // Lecture
        LectureXML lxml = new LectureXML();
        InputStream rawxmltv = mainA.getResources().openRawResource(R.raw.pgtv);
        lxml.creationDocParser(rawxmltv);

        this.listTotal=lxml.getProgrammes();

        //Extraction des films
        for(int i = 0 ;i<listTotal.size();i++) {
            Programme pTmp = listTotal.get(i);
            if (pTmp.getFirstStyle().equals("Film") || pTmp.getFirstStyle().equals("Film"))
                listFilm.add(pTmp);
        }

        //Lecture Sav Locale
        listLocal=new ArrayList<Programme>();


        //Mise à jour des attributs

    }

    public void remplirFilms() {
        mainA.getMovieList().clear();
        for(int i = 0 ;i<listFilm.size();i++) {
            Programme pTmp = listFilm.get(i);
            mainA.getMovieList().add(pTmp);
        }
        mainA.setNombre( mainA.getMovieList().size()+"");
    }

    public void remplirAvoir() {
        mainA.getMovieList().clear();
        mainA.getMovieList().add(listFilm.get(0));
        mainA.setNombre( mainA.getMovieList().size()+"");
    }
    public void remplirVu() {
        mainA.getMovieList().clear();
        mainA.getMovieList().add(listFilm.get(0));

        mainA.setNombre( mainA.getMovieList().size()+"");
    }
    public void remplirPoubelle() {
        mainA.getMovieList().clear();
        mainA.getMovieList().add(listFilm.get(0));

        mainA.setNombre( mainA.getMovieList().size()+"");
    }

}
