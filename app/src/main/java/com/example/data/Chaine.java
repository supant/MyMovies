package com.example.data;

public class Chaine {
    private String id;
    private int numero;
    private String nom;
    private int hash;

    public static String tous = "Tous";
    public static String ENTETE_IMDB="http://www.imdb.com/find?&q=";
    public static String ENTETE_TELERAMA="http://www.telerama.fr/recherche/recherche.php?query=";

    public Chaine(String id) {
        this.id = id;
        try {
            this.hash = (id.split("\\.")[0]).hashCode();
        } catch (Exception e) {
            System.out.print("Erreur conversion hash de chaine de " + id
                    + " vers entier");
            this.hash = -1;
        }
    }


    public String getNom() {
        return nom;
    }

    public String getId() {
        return id;
    }

    public int getNumero() {
        return numero;
    }

    public int getHash() {
        return hash;
    }


    public void setNumero(String sNumero) {
        try {

            this.numero = Integer.parseInt(sNumero);
        } catch (Exception e) {
            System.out.print("Erreur conversion numero de chaine de " + sNumero
                    + " vers entier");
            this.numero = -1;
        }
    }

    public void setsNom(String nom) {
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Chaine [id=" + id + ", numero="
                + numero + ", nom=" + nom + ", hash=" + hash + "]";
    }

    public boolean equalNom(Chaine uneAutre) {
        return this.getNom().equals(uneAutre.getNom());
    }
    public boolean equalNom(String uneAutre) {
        return this.getNom().equals(uneAutre);
    }
}
