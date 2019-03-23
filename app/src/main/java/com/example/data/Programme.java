package com.example.data;

import java.util.LinkedList;

public class Programme {
    //XML
    private MaDate start;
    private MaDate stop;
    private String titre;
    private LinkedList<String> style;
    private String description;
    private String image;
    private int longueur;
    private Chaine chaine;
    private String episode;
    private String rating;
    private int dateCreation;
    // creer par XML : numero d'ordre ds le XML
    private int id;

    // statut dans l'appli
    private int statutAndroid;
    private MaDate changement;

    public static int rien = 0;
    public static int poubelle = 1;
    public static int avoir = 2;
    public static int vu = 3;
    public static int pasdefiltre = -1;



    public Programme(String start, String stop) {
        super();
        this.start = new MaDate(start);
        this.stop = new MaDate(stop);
        this.titre = "";
        this.style = new LinkedList<String>();
        this.description = "";
        this.longueur = this.start.differenceMinute(this.stop);;
        this.chaine = null;
        this.dateCreation=0;
        this.episode="";
        this.id=-1;
        this.statutAndroid=Programme.rien;
        this.changement =new MaDate();
        this.image="";
    }

    public Programme(MaDate start, MaDate stop, String titre, String description, String image,
                     int longueur, Chaine chaine, int dateCreation, String episode,
                     int id, int statutAndroid, MaDate changement,String rating) {
        this.start = start;
        this.stop = stop;
        this.titre = titre;
        this.description = description;
        this.image = image;
        this.longueur = longueur;
        this.chaine = chaine;
        this.dateCreation = dateCreation;
        this.episode = episode;
        this.id = id;
        this.statutAndroid = statutAndroid;
        this.changement = changement;
        this.rating=rating;
        if (longueur<=0) longueur=start.differenceMinute(stop);
        this.style = new LinkedList<String>();
    }

    @Override
    public String toString() {
        return "Programme [start=" + start + ", stop=" + stop + ", titre="
                + titre + ", style=" + style + ", description=" + description
                + ", longueur=" + longueur + ", chaine=" + chaine
                + ", dateCreation=" + dateCreation + ", episode="
                + episode + ", id=" + id + "]";
    }


    public String toStringTxt() {
        //sans description pour l'instant
        String result = virerPointVirgule(titre)+";"+start.toStringAMJHMS()+";"+stop.toStringAMJHMS()+";"+
        virerPointVirgule("")+";"+image+";"+longueur+";"+chaine.getId()+";"+";"+
                dateCreation+";"+episode+";"+id+";"+changement.toStringAMJHMS()+";"+
                statutAndroid+";"+rating+";"+style.size();
        for(int i =0;i<style.size();i++) {
            result += ";" + style.get(i);
        }
        return result;
    }

    public String toStringIntro() {
        String result="[";
        if (dateCreation>0) result+=dateCreation+", "; else result+="-, ";
        if (!rating.equals("-0")) result+=rating+", "; else result+="-, ";
        result+=longueur+"min";
        for(int i=1;i<style.size();i++) {
            result+=", "+style.get(i);
        }
        result+="]";
        return result;
    }

    private String virerPointVirgule(String s) {
       return s.replace(';',',');
    }

    @Override
    public boolean equals(Object obj) {
        Programme o =(Programme)obj;
        //if (o.getTitre().equals(titre) && o.getLongueur()==longueur)
        if (o.getTitre().equals(titre))
            return true;
        return false;
    }
    public Chaine getChaine() {
        return chaine;
    }

    public void setChaine(Chaine chaine) {
        this.chaine = chaine;
    }

    public MaDate getStart() {
        return start;
    }

    public MaDate getStop() {
        return stop;
    }

    public String getTitre() {
        return titre;
    }

    public LinkedList<String> getStyle() {
        return style;
    }
    public String getFirstStyle() {
        return style.getFirst();
    }


    public int getLongueur() {
        return longueur;
    }

    public String getDescription() {
        return description;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
    public void setStyle(String style) {
        this.style.add(style);
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setLongueur(String longueur) {
        this.longueur = Integer.parseInt(longueur);
    }
    public int getDateCreation() {
        return dateCreation;
    }
    public void setDateCreation(String dateCreation) {
        if (dateCreation.length()>0 && dateCreation.charAt(0)>='0' && dateCreation.charAt(0)<='9')
            this.dateCreation = Integer.parseInt(dateCreation);
    }
    public String getEpisode() {
        return episode;
    }
    public void setEpisode(String episode) {
        this.episode = episode;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getStatutAndroid() {
        return statutAndroid;
    }

    public void setStatutAndroid(int statutAndroid) {
        this.statutAndroid = statutAndroid;
    }

    public MaDate getChangement() {
        return changement;
    }

    public void setChangement(MaDate changement) {
        this.changement = changement;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        if (image.length()>0)
            this.image = image;
    }
}
