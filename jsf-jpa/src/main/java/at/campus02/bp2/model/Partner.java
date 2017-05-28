package at.campus02.bp2.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Partner")

public class Partner implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String firmenname;

	private String strasse;

	private String adresse;

	private int plz;

	private String premiumstatus;
	/*
	 * static { premiumstatus = new String[2]; premiumstatus[0] = "Nein";
	 * premiumstatus[1] = "Ja"; }
	 */

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirmenname() {
		return firmenname;
	}

	public void setFirmenname(String firmenname) {
		this.firmenname = firmenname;
	}

	public String getStrasse() {
		return strasse;
	}

	public void setStrasse(String strasse) {
		this.strasse = strasse;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public int getPlz() {
		return plz;
	}

	public void setPlz(int plz) {
		this.plz = plz;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getPremiumstatus() {
		return premiumstatus;
	}
	  
	public void setPremiumstatus(String premiumstatus) {
		this.premiumstatus = premiumstatus; 
	} 

}