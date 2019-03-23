package com.example.data;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.mymovies.MainActivity;
import com.example.mymovies.R;
import com.example.parser.LectureXML;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class LesListes {
    private MainActivity mainA;
    private List<Programme> listTotal = new ArrayList<>();
    private List<Programme> listFilm = new ArrayList<>();
    private List<Programme> listLocal = new ArrayList<>();
    private HashMap<Integer, Chaine> listChaine = new HashMap<Integer,Chaine>();

    private MaDate debut;
    private MaDate fin;


    public LesListes(MainActivity mainA) {
        this.mainA=mainA;

        // Lecture
      /*  InputStream rawxmltv = mainA.getResources().openRawResource(R.raw.pgtv);
        LectureXML lxml = new LectureXML();
        lxml.creationDocParser(rawxmltv);
        this.listTotal=lxml.getProgrammes();*/

        //Lecture PG
        FileInputStream rawxmltv = null;
        try {
            rawxmltv = mainA.openFileInput("pgtv.xmltv");

            LectureXML lxml = new LectureXML();
            lxml.creationDocParser(rawxmltv);
            this.listTotal=lxml.getProgrammes();
            this.listChaine=lxml.getChaines();
         } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Extraction des films
        for(int i = 0 ;i<listTotal.size();i++) {
            Programme pTmp = listTotal.get(i);
            if (pTmp.getFirstStyle().equals("Film") || pTmp.getFirstStyle().equals("Films"))
                listFilm.add(pTmp);
        }

        //Lecture Sav Locale
        FileInputStream rawtxtfilm = null;
        try {
            rawtxtfilm = mainA.openFileInput("mesFilms.txt");
            lectureLocal(rawtxtfilm);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        remplirMinMaxDate();
        //Log.i("bob",debut.toStringAMJHMS()+" "+fin.toStringAMJHMS());

        //Mise à date des attributs

/*
        listLocal.add(listFilm.get(1));
        listLocal.add(listFilm.get(2));
        listLocal.add(listFilm.get(3));
        listLocal.get(0).setStatutAndroid(Programme.avoir);
        listLocal.get(1).setStatutAndroid(Programme.poubelle);
        listLocal.get(2).setStatutAndroid(Programme.vu);

        Log.i("bob",listLocal.size()+"");
        */

    }

    //Lecture locale
    private void lectureLocal(FileInputStream rawtxtfilm)
    {
        try
        {
            InputStreamReader reader = new InputStreamReader(rawtxtfilm);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null)
            {
                String[] col = line.split(";");
                Chaine c = listChaine.get((col[6].split("\\.")[0]).hashCode());
                Programme p = new Programme(new MaDate(col[1]),new MaDate(col[2]),col[0],col[3],
                        col[4],Integer.parseInt(col[5]),c,
                        Integer.parseInt(col[7]),col[8],Integer.parseInt(col[9]),
                        Integer.parseInt(col[11]),new MaDate(col[10]),col[12]);
                int nbStyle=Integer.parseInt(col[13]);
                for(int i=0;i<nbStyle;i++) {
                    p.setStyle(col[i+14]);
                }
                listLocal.add(p);
            }
        }
        catch (Exception e)
        {
            Log.d("bob", e.toString());
        }
    }
    //Ecriture locale
    public void savLocal() {
        try {
            FileOutputStream rawtxtfilm = mainA.openFileOutput("mesFilms.txt", Context.MODE_PRIVATE);
            OutputStreamWriter writer = new OutputStreamWriter (rawtxtfilm);
            Log.i("bob","ecriture"+listLocal.size());
            for(int i = 0 ;i<listLocal.size();i++) {
                writer.write(listLocal.get(i).toStringTxt()+"\n");
                writer.flush();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //Dates min max
    private void remplirMinMaxDate() {
        Iterator<Programme> it = listTotal.iterator();
        debut = new MaDate("20400101000000");
        fin = new MaDate("20000101000000");
        while(it.hasNext()) {
            Programme pg = it.next();
            if (fin.compareTo(pg.getStart())<0) fin = pg.getStart();
            if (debut.compareTo(pg.getStart())>0) debut = pg.getStart();
        }

    }
    public MaDate getDebut() {
        return debut;
    }
    public MaDate getFin() {
        return fin;
    }
    public List<String> getListChaine() {
        Collection<Chaine> chaines = listChaine.values();
        List<String> result=new LinkedList<String>();
        result.add(Chaine.tous);
        for(Chaine c:chaines) {
            result.add(c.getNom());
        }
        return result;
    }
    public List<String> getDates() {
        Calendar c = Calendar.getInstance();
        c.setTime(debut.toDate());
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy");
        List<String> result=new LinkedList<String>();
        int nbJour = debut.differenceJour(fin);
        result.add(MaDate.tous);
        result.add(MaDate.today);
        if (nbJour<20) {
            for (int i = 1; i < nbJour; i++) {
                String output = sdf1.format(c.getTime());
                result.add(output);
                c.add(Calendar.DATE, 1);
            }
        }
        return result;
    }

    //Remplissage de la liste
    private int filtreStatut=Programme.pasdefiltre;
    private Chaine filtreChaine =null;
    private MaDate filtreDate =null;

    public void setFiltreStatut(int filtreStatut) {
        this.filtreStatut = filtreStatut;
    }
    public void setFiltreChaine(String chainetmp) {
        this.filtreChaine = null;
        if (chainetmp!= null && !chainetmp.equals(Chaine.tous)) {
            Collection<Chaine> chaines = listChaine.values();
            for(Chaine c:chaines) {
                if(c.equalNom(chainetmp)) filtreChaine=c;
            }
        }
    }
    public void setFiltreDate(String datetmp) {
        this.filtreDate=null;
        if (datetmp.equals(MaDate.today)) this.filtreDate=new MaDate();
        if (datetmp!=null && !datetmp.equals(MaDate.today) && !datetmp.equals(MaDate.tous)) {
            String result = datetmp.substring(6, 10) + datetmp.substring(3, 5) + datetmp.substring(0, 2) + "1010";
            this.filtreDate = new MaDate(result);
        }
    }
    /*public void remplirAlllFilms() {
        mainA.getMovieList().clear();
        Iterator<Programme> it = listFilm.iterator();
        while(it.hasNext())   {
            Programme pTmp = it.next();
            mainA.getMovieList().add(pTmp);
        }
        Toast.makeText( mainA, mainA.getMovieList().size()+"",Toast.LENGTH_SHORT).show();
    }*/

    public void remplirFilms() {
        Log.i("bob",filtreChaine+"");
        mainA.getMovieList().clear();
        Iterator<Programme> it = listFilm.iterator();
        while(it.hasNext())   {
            Programme pTmp = it.next();
            boolean ajouter = false;
            if(filtreStatut!=Programme.pasdefiltre) {
                if (pTmp.getStatutAndroid()==filtreStatut) {
                    ajouter=true;
                }
            } else ajouter=true;

            if(filtreChaine!=null && !pTmp.getChaine().equalNom(filtreChaine)) {
                ajouter=false;
            }
            if(filtreDate!=null && pTmp.getStart().compareTo(filtreDate)!=0) {
                ajouter=false;
            }
            if (ajouter) mainA.getMovieList().add(pTmp);
        }
        Toast.makeText( mainA, mainA.getMovieList().size()+"",Toast.LENGTH_SHORT).show();
    }




    public void remplirCeSoir() {
        mainA.getMovieList().clear();
        Iterator<Programme> it = listFilm.iterator();
        MaDate now = new MaDate();
        while(it.hasNext())   {
            Programme pTmp = it.next();
            if (now.compareTo(pTmp.getStart())==0) {
                mainA.getMovieList().add(pTmp);
            }
        }
        Toast.makeText( mainA, mainA.getMovieList().size()+"",Toast.LENGTH_SHORT).show();
    }


}
