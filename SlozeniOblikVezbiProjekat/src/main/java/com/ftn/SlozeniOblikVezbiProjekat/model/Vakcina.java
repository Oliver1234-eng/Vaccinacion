package com.ftn.SlozeniOblikVezbiProjekat.model;

import java.util.Date;

public class Vakcina {
	
	private Long id;
	private String nazivVakcine;
	private String nazivProizvodjaca;
	private String zemljaProizvodjac;
	//private Date datumPoslednjeIsporuke;
	private String datumPoslednjeIsporuke;
	private int dostupnaKolicina;
	
	public Vakcina() {}

	public Vakcina(Long id, String nazivVakcine, String nazivProizvodjaca, String zemljaProizvodjac, String datumPoslednjeIsporuke,
			int dostupnaKolicina) {
		super();
		this.id = id;
		this.nazivVakcine = nazivVakcine;
		this.nazivProizvodjaca = nazivProizvodjaca;
		this.zemljaProizvodjac = zemljaProizvodjac;
		this.datumPoslednjeIsporuke = datumPoslednjeIsporuke;
		this.dostupnaKolicina = dostupnaKolicina;
	}
	
	public Vakcina(String nazivVakcine, String nazivProizvodjaca, String zemljaProizvodjac, String datumPoslednjeIsporuke,
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

	public String getDatumPoslednjeIsporuke() {
		return datumPoslednjeIsporuke;
	}

	public void setDatumPoslednjeIsporuke(String datumPoslednjeIsporuke) {
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
		return this.getId() + ";" + this.getNazivVakcine() + ";" + this.getNazivProizvodjaca() + ";" + this.getZemljaProizvodjac()
		 + ";" + this.getDatumPoslednjeIsporuke() + ";" + this.getDostupnaKolicina();
	}

}
