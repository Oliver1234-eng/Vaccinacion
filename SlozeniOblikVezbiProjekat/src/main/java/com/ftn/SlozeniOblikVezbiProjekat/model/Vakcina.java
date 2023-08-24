package com.ftn.SlozeniOblikVezbiProjekat.model;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Vakcina {

	private Long id;
	private String nazivVakcine;
	private String nazivProizvodjaca;
	private String zemljaProizvodjac;
	private LocalDateTime datumPoslednjeIsporuke;
	private int dostupnaKolicina;
	
	public Vakcina() {}

	public Vakcina(Long id, String nazivVakcine, String nazivProizvodjaca, String zemljaProizvodjac, LocalDateTime datumPoslednjeIsporuke,
			int dostupnaKolicina) {
		super();
		this.id = id;
		this.nazivVakcine = nazivVakcine;
		this.nazivProizvodjaca = nazivProizvodjaca;
		this.zemljaProizvodjac = zemljaProizvodjac;
		this.datumPoslednjeIsporuke = datumPoslednjeIsporuke;
		this.dostupnaKolicina = dostupnaKolicina;
	}
	
	public Vakcina(String nazivVakcine, String nazivProizvodjaca, String zemljaProizvodjac, LocalDateTime datumPoslednjeIsporuke,
			int dostupnaKolicina) {
		super();
		this.nazivVakcine = nazivVakcine;
		this.nazivProizvodjaca = nazivProizvodjaca;
		this.zemljaProizvodjac = zemljaProizvodjac;
		this.datumPoslednjeIsporuke = datumPoslednjeIsporuke;
		this.dostupnaKolicina = dostupnaKolicina;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getNazivVakcine() {
		return nazivVakcine;
	}

	public void setNazivVakcine(String nazivVakcine) {
		this.nazivVakcine = nazivVakcine;
	}

	public String getNazivProizvodjaca() {
		return nazivProizvodjaca;
	}

	public void setNazivProizvodjaca(String nazivProizvodjaca) {
		this.nazivProizvodjaca = nazivProizvodjaca;
	}

	public String getZemljaProizvodjac() {
		return zemljaProizvodjac;
	}

	public void setZemljaProizvodjac(String zemljaProizvodjac) {
		this.zemljaProizvodjac = zemljaProizvodjac;
	}

	public LocalDateTime getDatumPoslednjeIsporuke() {
		return datumPoslednjeIsporuke;
	}

	public void setDatumPoslednjeIsporuke(LocalDateTime datumPoslednjeIsporuke) {
		this.datumPoslednjeIsporuke = datumPoslednjeIsporuke;
	}

	public int getDostupnaKolicina() {
		return dostupnaKolicina;
	}

	public void setDostupnaKolicina(int dostupnaKolicina) {
		this.dostupnaKolicina = dostupnaKolicina;
	}

	@Override
	public String toString() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
		String formatiranDatum = (datumPoslednjeIsporuke != null && !datumPoslednjeIsporuke.equals(LocalDateTime.MIN))
				? datumPoslednjeIsporuke.format(formatter)
				: "";

		return this.getId() + ";" + this.getNazivVakcine() + ";" + this.getNazivProizvodjaca() + ";" + this.getZemljaProizvodjac()
				+ ";" + formatiranDatum + ";" + this.getDostupnaKolicina();
	}

}
