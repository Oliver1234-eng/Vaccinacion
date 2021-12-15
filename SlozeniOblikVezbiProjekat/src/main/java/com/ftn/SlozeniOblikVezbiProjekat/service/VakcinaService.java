package com.ftn.SlozeniOblikVezbiProjekat.service;

import java.util.List;

import com.ftn.SlozeniOblikVezbiProjekat.model.Vakcina;


public interface VakcinaService {
	
	Vakcina findOne(Long id); 
	List<Vakcina> findAll(); 
	Vakcina save(Vakcina vakcina); 
	Vakcina update(Vakcina vakcina); 
	Vakcina delete(Long id); 

}