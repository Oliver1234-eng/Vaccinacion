package com.ftn.SlozeniOblikVezbiProjekat.service;

import java.util.List;

import com.ftn.SlozeniOblikVezbiProjekat.model.Korisnik;

public interface KorisnikService {
	
	Korisnik findOneById(Long id);
	Korisnik findOne(String email); 
	Korisnik findOne(String email, String sifra);
	List<Korisnik> findAll(); 
	Korisnik save(Korisnik korisnik); 
	Korisnik update(Korisnik korisnik); 
	Korisnik delete(Long id); 

}
