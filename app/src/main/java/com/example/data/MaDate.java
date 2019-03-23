package com.example.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MaDate {
	private int heure;
	private int minute;
	private int jour;
	private int mois;
	private int annee;
	private String valeur;

    public static String tous = "Tous";
    public static String today = "Today";
	
	public MaDate(String valeur) {
		this.valeur = valeur;
		jour=Integer.parseInt(valeur.substring(6, 8));
		mois=Integer.parseInt(valeur.substring(4, 6));
		annee=Integer.parseInt(valeur.substring(0, 4));
		heure=Integer.parseInt(valeur.substring(8, 10));
		minute=Integer.parseInt(valeur.substring(10, 12));
	}

	public MaDate() {
        //Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String currentDateandTime = sdf.format(new Date());
        this.valeur = currentDateandTime;
        jour=Integer.parseInt(valeur.substring(6, 8));
        mois=Integer.parseInt(valeur.substring(4, 6));
        annee=Integer.parseInt(valeur.substring(0, 4));
        heure=Integer.parseInt(valeur.substring(8, 10));
        minute=Integer.parseInt(valeur.substring(10, 12));
    }


	public int getHeure() {
		return heure;
	}
	public int getMinute() {
		return minute;
	}
	public int getJour() {
		return jour;
	}
	public int getMois() {
		return mois;
	}
	public int getAnnee() {
		return annee;
	}
	public String getValeur() {
		return valeur;
	}

	public String toStringJMA() {
		return checkZero(jour)+"-"+checkZero(mois)+"-"+checkZero(annee);
	}
	public String toStringHM() {
		return checkZero(heure)+"h"+checkZero(minute);
	}
	
	public String checkZero(int i) {
		if(i<10) return "0"+i;
		return ""+i;
	}
	public String toStringJJMA() {
		SimpleDateFormat  formater = new SimpleDateFormat("EEEE d MMM yyyy");
		return formater.format(toDate());
	
	}
	public String toStringAMJHMS() {
		return  valeur;
	}
	
	
	public Date toDate()  {
		String sDate=annee+checkZero(mois)+checkZero(jour)+
		checkZero(heure)+checkZero(minute)+"00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
        try {
			return sdf.parse(sDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Date();
		}
	}

	public int compareTo(MaDate date) {
		if(annee==date.annee && jour==date.jour && mois==date.mois) return 0;
		if (annee>date.annee) return 1;
		if (annee<date.annee) return -1;
		if (mois>date.mois) return 1;
		if (mois<date.mois) return -1;
		if (jour>date.jour) return 1;
		if (jour<date.jour) return -1;
		return 0;
	}
	
	public String differenceJour() {
		Date now = new Date();
		return ""+(now.getTime() - toDate().getTime()) / (24 * 60 * 60 * 1000);  
	}
	public int differenceJourInv() {
		Date now = new Date();
		return (int)( -(now.getTime() - toDate().getTime()) / (24 * 60 * 60 * 1000) );
	}
	public int differenceJour(MaDate uneDate) {
		return (int)( (uneDate.toDate().getTime() - toDate().getTime()) / (24 * 60 * 60 * 1000) );
	}

	public int differenceMinute(MaDate uneDate) {
	    return (int) ( (uneDate.toDate().getTime()-this.toDate().getTime()) /(60*1000));

	}

	@Override
	public String toString() {
		return "MaDate [heure=" + heure + ", minute=" + minute + ", date=" + jour + ", mois=" + mois + ", annee="
				+ annee + ", valeur=" + valeur + "]";
	} 
	
	

}
