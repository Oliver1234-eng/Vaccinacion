package com.ftn.SlozeniOblikVezbiProjekat.model;

public class Korisnik {
	
	private Long id;
	private String ime;
	private String prezime;
	private String email;
	private String lozinka;
	//u txt fajlu oznaka "0" je administrator, a oznaka "1" je medicinski radnik (zbog metode ordinal za enumeraciju u toStringu metodi u klasi Korisnik)
	private UlogaKorisnika ulogaKorisnika;
	private String JMBG;
	
	public Korisnik() {}
	
	public Korisnik(Long id, String ime, String prezime, String email, String lozinka, UlogaKorisnika ulogaKorisnika, String JMBG) {
		super();
		this.id = id;
		this.ime = ime;
		this.prezime = prezime;
		this.email = email;
		this.lozinka = lozinka;
		this.ulogaKorisnika = UlogaKorisnika.Administrator;
		this.JMBG = JMBG;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		this.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		this.prezime = prezime;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		this.lozinka = lozinka;
	}

	public UlogaKorisnika getUlogaKorisnika() {
		return ulogaKorisnika;
	}

	public void setUlogaKorisnika(UlogaKorisnika ulogaKorisnika) {
		this.ulogaKorisnika = ulogaKorisnika;
	}
	
	public String getJMBG() {
		return JMBG;
	}

	public void setJMBG(String JMBG) {
		this.JMBG = JMBG;
	}

	@Override
	public String toString() {
		return this.ime + " " + this.prezime + " (" + this.email + ")";
	}
	
	public String toFileString() {
		return this.getId() + ";" + this.getIme() + ";" + this.getPrezime() + ";" + 
				this.getEmail() + ";" + this.getLozinka() + ";" + this.ulogaKorisnika.ordinal()
				+ ";" + this.getJMBG();
	}
	
}
