package com.example.parser;


import com.example.data.Chaine;
import com.example.data.Programme;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;



public class LectureXML {

	private HashMap<Integer, Chaine> hm = new HashMap<Integer,Chaine>();
	private ArrayList<Programme> lPg =  new ArrayList<Programme>();
	
	
	
	public void creationDocParser(String sName) {
	
	SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
    try {
        SAXParser saxParser = saxParserFactory.newSAXParser();
        SaxHandler handler = new SaxHandler();
        saxParser.parse(new File(sName), handler);
        //Get Employees list
        System.out.println("Ouverture OK. Debut lecture chaine");
        hm=handler.getChaines();
        System.out.println("Lecture chaine OK. Debut lecture programme");
        lPg=handler.getProgrammes();
        System.out.println("Lecture programme OK. Total : "+lPg.size());
    } catch (Exception e) {
        e.printStackTrace();
    }
	}


	public void creationDocParser(InputStream rawxmltv) {

		SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
		try {
			SAXParser saxParser = saxParserFactory.newSAXParser();
			SaxHandler handler = new SaxHandler();
			saxParser.parse(rawxmltv, handler);
			System.out.println("Ouverture OK. Debut lecture chaine");
			hm=handler.getChaines();
			System.out.println("Lecture chaine OK. Debut lecture programme");
			lPg=handler.getProgrammes();
			System.out.println("Lecture programme OK. Total : "+lPg.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void afficheChaines() {
		Collection<Chaine> l = hm.values();
		for (Chaine c : l) {
			System.out.println(c);
		}
	}
	public HashMap<Integer,Chaine> getChaines() {
		return hm;
	}
	public ArrayList<Programme> getProgrammes() {
		return lPg;
	}
	
	
}
