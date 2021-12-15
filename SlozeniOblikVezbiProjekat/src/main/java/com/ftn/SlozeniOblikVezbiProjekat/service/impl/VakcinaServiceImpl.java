package com.ftn.SlozeniOblikVezbiProjekat.service.impl;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ftn.SlozeniOblikVezbiProjekat.model.Vakcina;
import com.ftn.SlozeniOblikVezbiProjekat.service.VakcinaService;

@Service
@Qualifier("fajloviVakcina")
public class VakcinaServiceImpl implements VakcinaService {
	
	@Value("${vakcine.pathToFile}")
	private String pathToFile;
	
    private Map<Long, Vakcina> readFromFile() {

    	Map<Long, Vakcina> vakcine = new HashMap<>();
    	Long nextId = 1L;
    	
    	try {
			Path path = Paths.get(pathToFile);
			System.out.println(path.toFile().getAbsolutePath());
			List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));
			//DateFormat convert = new SimpleDateFormat("dd.MM.yyyy.");

			for (String line : lines) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;
				
				String[] tokens = line.split(";");
				Long id = Long.parseLong(tokens[0]);
				String nazivVakcine = tokens[1];
				String nazivProizvodjaca = tokens[2];
				String zemljaProizvodjac = tokens[3];
				String datumPoslednjeIsporuke = tokens[4];
				//String datumPoslednjeIsporukeString = tokens[4];
				//Date datumPoslednjeIsporuke = convert.parse(datumPoslednjeIsporukeString);
				int dostupnaKolicina = Integer.parseInt(tokens[5]);
				
				vakcine.put(id, new Vakcina(id, nazivVakcine, nazivProizvodjaca, zemljaProizvodjac, datumPoslednjeIsporuke, dostupnaKolicina));

				if(nextId<id)
					nextId=id;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return vakcine;
    }
    
    private Map<Long, Vakcina> saveToFile(Map<Long, Vakcina> vakcine) {
    	
    	Map<Long, Vakcina> ret = new HashMap<>();
    	
    	try {
			Path path = Paths.get(pathToFile);
			System.out.println(path.toFile().getAbsolutePath());
			List<String> lines = new ArrayList<>();
			
			for (Vakcina vakcina : vakcine.values()) {
				lines.add(vakcina.toString());
				ret.put(vakcina.getId(), vakcina);
			}
			//pisanje svih redova za vakcine
			Files.write(path, lines, Charset.forName("UTF-8"));
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return ret;
    }
    
    private Long nextId(Map<Long, Vakcina> vakcine) {
    	Long nextId = 1L;
    	for (Long id : vakcine.keySet()) {
    		if(nextId<id)
				nextId=id;
		}
    	return ++nextId;
    }

	@Override
	public Vakcina findOne(Long id) {
		Map<Long, Vakcina> vakcine = readFromFile();	
		return vakcine.get(id);
	}

	@Override
	public List<Vakcina> findAll() {
		Map<Long, Vakcina> vakcine = readFromFile();	
		return new ArrayList<>(vakcine.values());
	}

	@Override
	public Vakcina save(Vakcina vakcina) {
		Map<Long, Vakcina> vakcine = readFromFile();	
		Long nextId = nextId(vakcine); 
		
		//u slučaju da vakcina nema id
		//tada treba da se dodeli id
		if (vakcina.getId() == null) {
			vakcina.setId(nextId++);
			
		}
		vakcine.put(vakcina.getId(), vakcina);
		saveToFile(vakcine);
		return vakcina;
	}

	@Override
	public Vakcina update(Vakcina vakcina) {
		Map<Long, Vakcina> vakcine = readFromFile();	
		
		vakcine.put(vakcina.getId(), vakcina);
		saveToFile(vakcine);
		return vakcina;
	}

	@Override
	public Vakcina delete(Long id) {
		Map<Long, Vakcina> vakcine = readFromFile();	
		
		if (!vakcine.containsKey(id)) {
			throw new IllegalArgumentException("tried to remove non existing vaccine");
		}
		
		Vakcina vakcina = vakcine.get(id);
		if (vakcina != null) {
			vakcine.remove(id);
		}
		saveToFile(vakcine);
		return vakcina;
	}

}

