package com.example.parser;

import android.util.Log;

import com.example.data.Chaine;
import com.example.data.Programme;

import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class SaxHandler extends DefaultHandler {

	private HashMap<Integer, Chaine> hm = new HashMap<Integer, Chaine>();
	private ArrayList<Programme> lPg =  new ArrayList<Programme>();
	private boolean bDisplayName = false;
	private Chaine chaine = null;
	private Programme p=null;
	private boolean bTitle=false;
	private boolean bCat=false;
	private boolean bDesc=false;
	private boolean bLength=false;
	private boolean bDate=false;
	private boolean bEpisode=false;
	private boolean bIcon=false;

	public HashMap<Integer, Chaine> getChaines() {
		return hm;
	}
	public ArrayList<Programme> getProgrammes() {
		return lPg;
	}

	public void startDocument() throws SAXException {
	}

	public void endDocument() throws SAXException {
	}

	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		if (qName.equalsIgnoreCase("channel")) {
			chaine = new Chaine(attributes.getValue("id"));
		}
		if (qName.equalsIgnoreCase("display-name")) {
			bDisplayName = true;
		}
		if (qName.equalsIgnoreCase("programme")) {
			p=new Programme(attributes.getValue("start"),attributes.getValue("stop"));
			String sChaine = attributes.getValue("channel");
			Chaine c = hm.get((sChaine.split("\\.")[0]).hashCode());
			if (c==null) {
				System.out.println("pas de chaine !!"+sChaine.split("\\.")[1]);
			}
			p.setChaine(c);	
		}
		if (qName.equalsIgnoreCase("title")) {
			bTitle=true;
		}
		if (qName.equalsIgnoreCase("category")) {
			bCat=true;
		}
		if (qName.equalsIgnoreCase("desc")) {
			bDesc=true;
		}
		if (qName.equalsIgnoreCase("length")) {
			bLength=true;
		}
		if (qName.equalsIgnoreCase("year")) {
			bDate=true;
		}
		if (qName.equalsIgnoreCase("episode-num")) {
			bEpisode=true;
		}
		if (qName.equalsIgnoreCase("icon")) {
			bIcon=true;
			if(p!=null) p.setImage(attributes.getValue("src"));
		}
		

	}

	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (qName.equalsIgnoreCase("channel")) {
			hm.put(chaine.getHash(), chaine);
		}
		if (qName.equalsIgnoreCase("programme")) {
			p.setId(lPg.size());
			lPg.add(p);
		}
	}

	public void characters(char ch[], int start, int length)
			throws SAXException {
		
		//Pour les chaines
		if (bDisplayName) {
			chaine.setsNom(new String(ch, start, length));
			bDisplayName = false;
		}
		//Pour les programmes
		if (bTitle) {
			p.setTitre(new String(ch, start, length));
			bTitle=false;
		}
		if (bCat) {
			p.setStyle(new String(ch, start, length));
			bCat=false;
		}
		if (bDesc) {
			p.setDescription(new String(ch, start, length));
			bDesc=false;
		}
		if (bLength) {
			p.setLongueur(new String(ch, start, length));
			bLength=false;
		}
		if (bDate) {
			p.setDateCreation(new String(ch, start, length));
			bDate=false;
		}
		if (bEpisode) {
			p.setEpisode(new String(ch, start, length));
			bEpisode=false;
		}
		if (bIcon) {
			bIcon=false;
		}
	}

	public void ignorableWhitespace(char ch[], int start, int length)
			throws SAXException {

	}

}