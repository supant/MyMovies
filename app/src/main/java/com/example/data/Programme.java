package com.example.data;

import java.util.LinkedList;

public class Programme {
    private MaDate start;
    private MaDate stop;
    private String titre;
    private LinkedList<String> style;
    private String description;
    private String image;
    private int longueur;
    private Chaine chaine;
    private int statut;
    private int dateCreation;
    private String episode;
    private int id;

    private int statutAndroid;
    private MaDate changement;

    public static int rien = 0;
    public static int poubelle = 1;
    public static int avoir = 2;
    public static int vu = 3;




    public Programme(String start, String stop) {
        super();
        this.start = new MaDate(start);
        this.stop = new MaDate(stop);
        this.titre = "";
        this.style = new LinkedList<String>();
        this.description = "";
        this.longueur = -1;
        this.chaine = null;
        this.statut=0;
        this.dateCreation=0;
        this.episode="";
        this.id=-1;
        this.statutAndroid=Programme.rien;
        this.changement =new MaDate();
        this.image="";
    }


    @Override
    public String toString() {
        return "Programme [start=" + start + ", stop=" + stop + ", titre="
                + titre + ", style=" + style + ", description=" + description
                + ", longueur=" + longueur + ", chaine=" + chaine + ", statut="
                + statut + ", dateCreation=" + dateCreation + ", episode="
                + episode + ", id=" + id + "]";
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
    public int getStatut() {
        return statut;
    }
    public void setStatut(int statut) {
        this.statut = statut;
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
