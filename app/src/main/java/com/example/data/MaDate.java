package com.example.data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MaDate {
	private int heure;
	private int minute;
	private int jour;
	private int mois;
	private int annee;
	private String valeur;
	
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
	
	
	private Date toDate()  {
		String sDate=annee+checkZero(mois)+checkZero(jour)+
		checkZero(heure)+checkZero(minute)+"00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
			return sdf.parse(sDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Date();
		}
	}
	
	public String differenceJour(Date date) {
		Date now = new Date();
		return ""+(now.getTime() - toDate().getTime()) / (24 * 60 * 60 * 1000);  
	}
	@Override
	public String toString() {
		return "MaDate [heure=" + heure + ", minute=" + minute + ", jour=" + jour + ", mois=" + mois + ", annee="
				+ annee + ", valeur=" + valeur + "]";
	} 
	
	

}
