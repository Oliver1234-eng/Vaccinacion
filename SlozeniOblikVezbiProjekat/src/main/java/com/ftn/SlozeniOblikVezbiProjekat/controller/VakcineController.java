package com.ftn.SlozeniOblikVezbiProjekat.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import com.ftn.SlozeniOblikVezbiProjekat.model.Vakcina;
import com.ftn.SlozeniOblikVezbiProjekat.service.VakcinaService;

@Controller
@RequestMapping(value = "/vakcine")
public class VakcineController implements ServletContextAware {
	
	@Autowired
	private ServletContext servletContext;
	private  String bURL; 
	
	@Autowired
	private VakcinaService vakcinaService;
	
	/** inicijalizacija podataka za kontroler */
	@PostConstruct
	public void init() {	
		bURL = servletContext.getContextPath()+"/";
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	/** pribavnjanje HTML stanice za prikaz svih entiteta, get zahtev */
	// GET: vakcine
	@GetMapping
	@ResponseBody
	public String index() {	
		List<Vakcina> vakcine = vakcinaService.findAll();
			
		StringBuilder retVal = new StringBuilder();
		retVal.append(
				"<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
				"	<meta charset=\"UTF-8\">\r\n" + 
	    		"	<base href=\""+bURL+"\">" + 
				"	<title>Vakcine</title>\r\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviTabela.css\"/>\r\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviHorizontalniMeni.css\"/>\r\n"+
				"</head>\r\n" + 
				"<body> "+
				"	<ul>\r\n" + 
				"		<li><a href=\"vakcine/ulogaAdministrator\">Administrator</a></li>\r\n" + 
				"		<li><a href=\"vakcine/ulogaMedicinskiRadnik\">Medicinski radnik</a></li>\r\n" + 
				"	</ul>\r\n");
	
		retVal.append(
				"</body>\r\n"+
				"</html>\r\n");		
		return retVal.toString();
	}
	
	/** pribavnjanje HTML stanice za prikaz svih entiteta, get zahtev, uloga administratora */
	// GET: vakcine
	@GetMapping(value="/ulogaAdministrator")
	@ResponseBody
	public String indexAdministrator() {	
		List<Vakcina> vakcine = vakcinaService.findAll();
			
		StringBuilder retVal = new StringBuilder();
		retVal.append(
				"<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
				"	<meta charset=\"UTF-8\">\r\n" + 
	    		"	<base href=\""+bURL+"\">" + 
				"	<title>Vakcine</title>\r\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviTabela.css\"/>\r\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviHorizontalniMeni.css\"/>\r\n"+
				"</head>\r\n" + 
				"<body> "+
				"		<table>\r\n" + 
				"			<caption>Vakcine</caption>\r\n" + 
				"			<tr>\r\n" + 
				"				<th>R. br.</th>\r\n" + 
				"				<th></th>\r\n" + 
				"				<th>Naziv vakcine</th>\r\n" + 
				"				<th>Naziv proizvodjaca</th>\r\n" + 
				"				<th>Zemlja proizvodjac</th>\r\n" +
				"				<th>Datum poslednje isporuke</th>\r\n" +
				"				<th>Dostupna kolicina</th>\r\n" +
				"				<th></th>\r\n" +
				"			</tr>\r\n");
		
		for (int i=0; i < vakcine.size(); i++) {
			Vakcina vakcinaIt = vakcine.get(i);
			retVal.append(
				"			<tr>\r\n" + 
				"				<td class=\"broj\">"+ (i+1) +"</td>\r\n" + 
				"				<td><a href=\"vakcine/ulogaAdministrator/details?id="+vakcinaIt.getId()+"\">" + vakcinaIt.getNazivVakcine() +"</a></td>\r\n" +
				"				<td>"+ vakcinaIt.getNazivVakcine() +"</td>\r\n" +
				"				<td>"+ vakcinaIt.getNazivProizvodjaca() +"</td>\r\n" +
				"				<td>"+ vakcinaIt.getZemljaProizvodjac() +"</td>\r\n" +
				"				<td>"+ vakcinaIt.getDatumPoslednjeIsporuke() +"</td>\r\n" +
				"				<td class=\"broj\">"+ vakcinaIt.getDostupnaKolicina() +"</td>\r\n" +
//				"				<td>" + 
//				"					<form method=\"post\" action=\"vakcine/delete\">\r\n" + 
//				"						<input type=\"hidden\" name=\"id\" value=\""+ vakcinaIt.getId()+"\">\r\n" + 
//				"						<input type=\"submit\" value=\"Obrisi\"></td>\r\n" + 
//				"					</form>\r\n" +
//				"				</td>" +
				"			</tr>\r\n");
			
		}
		retVal.append(
				"		</table>\r\n");
		retVal.append(
				"	<ul>\r\n" + 
				"		<li><a href=\"vakcine/ulogaAdministrator/add\">Dodaj vakcinu</a></li>\r\n" + 
				"	</ul>\r\n");	
		
		retVal.append(
				"</body>\r\n"+
				"</html>\r\n");		
		return retVal.toString();
	}
	
	/** pribavnjanje HTML stanice za prikaz svih entiteta, get zahtev, uloga medicinskog radnika */
	// GET: vakcine
	@GetMapping(value="/ulogaMedicinskiRadnik")
	@ResponseBody
	public String indexMedicinskiRadnik() {	
		List<Vakcina> vakcine = vakcinaService.findAll();
			
		StringBuilder retVal = new StringBuilder();
		retVal.append(
				"<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
				"	<meta charset=\"UTF-8\">\r\n" + 
	    		"	<base href=\""+bURL+"\">" + 
				"	<title>Vakcine</title>\r\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviTabela.css\"/>\r\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviHorizontalniMeni.css\"/>\r\n"+
				"</head>\r\n" + 
				"<body> "+
				"		<table>\r\n" + 
				"			<caption>Vakcine</caption>\r\n" + 
				"			<tr>\r\n" + 
				"				<th>R. br.</th>\r\n" + 
				"				<th></th>\r\n" + 
				"				<th>Naziv vakcine</th>\r\n" + 
				"				<th>Naziv proizvodjaca</th>\r\n" + 
				"				<th>Zemlja proizvodjac</th>\r\n" +
				"				<th>Datum poslednje isporuke</th>\r\n" +
				"				<th>Dostupna kolicina</th>\r\n" +
				"				<th></th>\r\n" +
				"			</tr>\r\n");
		
		for (int i=0; i < vakcine.size(); i++) {
			Vakcina vakcinaIt = vakcine.get(i);
			retVal.append(
				"			<tr>\r\n" + 
				"				<td class=\"broj\">"+ (i+1) +"</td>\r\n" + 
				"				<td><a href=\"vakcine/ulogaMedicinskiRadnik/details?id="+vakcinaIt.getId()+"\">" + vakcinaIt.getNazivVakcine() +"</a></td>\r\n" +
				"				<td>"+ vakcinaIt.getNazivVakcine() +"</td>\r\n" +
				"				<td>"+ vakcinaIt.getNazivProizvodjaca() +"</td>\r\n" +
				"				<td>"+ vakcinaIt.getZemljaProizvodjac() +"</td>\r\n" +
				"				<td>"+ vakcinaIt.getDatumPoslednjeIsporuke() +"</td>\r\n" +
				"				<td class=\"broj\">"+ vakcinaIt.getDostupnaKolicina() +"</td>\r\n" +
//				"				<td>" + 
//				"					<form method=\"post\" action=\"vakcine/delete\">\r\n" + 
//				"						<input type=\"hidden\" name=\"id\" value=\""+ vakcinaIt.getId()+"\">\r\n" + 
//				"						<input type=\"submit\" value=\"Obrisi\"></td>\r\n" + 
//				"					</form>\r\n" +
//				"				</td>" +
				"			</tr>\r\n");
			
		}
		retVal.append(
				"		</table>\r\n");	
		
		retVal.append(
				"</body>\r\n"+
				"</html>\r\n");		
		return retVal.toString();
	}
	
	
	
	/** pribavnjanje HTML stanice za unos novog entiteta, get zahtev, uloga administratora */
	// GET: vakcine/dodaj
	@GetMapping(value="ulogaAdministrator/add")
	@ResponseBody
	public String create() {
		List<Vakcina> vakcine = vakcinaService.findAll();
		
		StringBuilder retVal = new StringBuilder();
		retVal.append(
				"<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
				"	<meta charset=\"UTF-8\">\r\n" + 
	    		"	<base href=\""+ bURL + "ulogaAdministrator"+"\">" + 
				"	<title>Dodaj vakcinu</title>\r\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviForma.css\"/>\r\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviHorizontalniMeni.css\"/>\r\n"+
				"</head>\r\n" + 
				"<body>\r\n" + 
				"	<form method=\"post\" action=\"vakcine/ulogaAdministrator/add\">\r\n" + 
				"		<table>\r\n" + 
				"			<caption>Vakcina</caption>\r\n" + 
				"			<tr><th>Naziv vakcine: </th><td><input type=\"text\" value=\"\" name=\"nazivVakcine\"/></td></tr>\r\n" + 
				"			<tr><th>Naziv proizvodjaca: </th><td><input type=\"text\" value=\"\" name=\"nazivProizvodjaca\"/></td></tr>\r\n" +
				"			<tr><th>Zemlja proizvodjac: </th><td><input type=\"text\" value=\"\" name=\"zemljaProizvodjac\"/></td></tr>\r\n" +
//				"			<tr><th>Datum poslednje isporuke: </th><td><input type=\"date\" value=\"\" name=\"datumPoslednjeIsporuke\"/></td></tr>\r\n" +
				"			<tr><th>Datum poslednje isporuke: </th><td><input type=\"text\" value=\"\" name=\"datumPoslednjeIsporuke\"/></td></tr>\r\n" +
				"			<tr><th>Dostupna kolicina: </th><td><input type=\"number\" min=\"1\" "+ "name=\"dostupnaKolicina\"/></td></tr>\r\n" + 
				"			<tr><th></th><td><input type=\"submit\" value=\"Dodaj\" /></td>\r\n" + 
				"		</table>\r\n" + 
				"	</form>\r\n" +
				"	<br/>\r\n");
		retVal.append(
				"</body>\r\n"+
				"</html>\r\n");		
		return retVal.toString();
	}
	
	/** obrada podataka forme za unos novog entiteta, post zahtev */
	// POST: vakcine/add
	@PostMapping(value="ulogaAdministrator/add")
	public void create(@RequestParam String nazivVakcine, @RequestParam String nazivProizvodjaca, 
			@RequestParam String zemljaProizvodjac, @RequestParam String datumPoslednjeIsporuke ,@RequestParam int dostupnaKolicina, HttpServletResponse response) throws IOException {		
		Vakcina vakcina = new Vakcina(nazivVakcine, nazivProizvodjaca, zemljaProizvodjac, datumPoslednjeIsporuke, dostupnaKolicina);
		Vakcina saved = vakcinaService.save(vakcina);
		response.sendRedirect(bURL + "vakcine/ulogaAdministrator");
	}
	
	/** obrada podataka forme za izmenu postojećeg entiteta, post zahtev, uloga administratora */
	// POST: vakcine/edit
	@PostMapping(value="/ulogaAdministrator/edit")
	public void EditUlogaAdministrator(@ModelAttribute Vakcina vakcinaEdited , HttpServletResponse response) throws IOException {	
		Vakcina vakcina = vakcinaService.findOne(vakcinaEdited.getId());
		if(vakcina != null) {
			if(vakcinaEdited.getNazivVakcine() != null && !vakcinaEdited.getNazivVakcine().trim().equals(""))
				vakcina.setNazivVakcine(vakcinaEdited.getNazivVakcine());
			
			if(vakcinaEdited.getNazivProizvodjaca() != null && !vakcinaEdited.getNazivProizvodjaca().trim().equals(""))
				vakcina.setNazivProizvodjaca(vakcinaEdited.getNazivProizvodjaca());
			
			if(vakcinaEdited.getZemljaProizvodjac() != null && !vakcinaEdited.getNazivProizvodjaca().trim().equals(""))
				vakcina.setZemljaProizvodjac(vakcinaEdited.getZemljaProizvodjac());
			
			if(vakcinaEdited.getDatumPoslednjeIsporuke() != null && !vakcinaEdited.getDatumPoslednjeIsporuke().trim().equals(""))
				vakcina.setDatumPoslednjeIsporuke(vakcinaEdited.getDatumPoslednjeIsporuke());
			
			if(vakcinaEdited.getDostupnaKolicina() > 0)
				vakcina.setDostupnaKolicina(vakcinaEdited.getDostupnaKolicina());
		}
		Vakcina saved = vakcinaService.update(vakcina);
		response.sendRedirect(bURL + "vakcine/ulogaAdministrator");
	}
	
	/** obrada podataka forme za izmenu postojećeg entiteta, post zahtev, uloga medicinskog radnika */
	// POST: vakcine/edit
	@PostMapping(value="/ulogaMedicinskiRadnik/edit")
	public void EditUlogaMedicinskiRadnik(@ModelAttribute Vakcina vakcinaEdited , HttpServletResponse response) throws IOException {	
		Vakcina vakcina = vakcinaService.findOne(vakcinaEdited.getId());
		if(vakcina != null) {
			if(vakcinaEdited.getNazivVakcine() != null && !vakcinaEdited.getNazivVakcine().trim().equals(""))
				vakcina.setNazivVakcine(vakcinaEdited.getNazivVakcine());
			
			if(vakcinaEdited.getNazivProizvodjaca() != null && !vakcinaEdited.getNazivProizvodjaca().trim().equals(""))
				vakcina.setNazivProizvodjaca(vakcinaEdited.getNazivProizvodjaca());
			
			if(vakcinaEdited.getZemljaProizvodjac() != null && !vakcinaEdited.getNazivProizvodjaca().trim().equals(""))
				vakcina.setZemljaProizvodjac(vakcinaEdited.getZemljaProizvodjac());
			
			if(vakcinaEdited.getDatumPoslednjeIsporuke() != null && !vakcinaEdited.getDatumPoslednjeIsporuke().trim().equals(""))
				vakcina.setDatumPoslednjeIsporuke(vakcinaEdited.getDatumPoslednjeIsporuke());
			
			if(vakcinaEdited.getDostupnaKolicina() > 0)
				vakcina.setDostupnaKolicina(vakcinaEdited.getDostupnaKolicina());
		}
		Vakcina saved = vakcinaService.update(vakcina);
		response.sendRedirect(bURL + "vakcine/ulogaMedicinskiRadnik");
	}
	
	
	/** obrada podataka forme za za brisanje postojećeg entiteta, post zahtev */
	// POST: vakcine/delete
//	@PostMapping(value="/delete")
//	public void delete(@RequestParam Long id, HttpServletResponse response) throws IOException {		
//		Vakcina deleted = vakcinaService.delete(id);
//		response.sendRedirect(bURL + "vakcine");
//	}
	
	/** pribavnjanje HTML stanice za prikaz određenog entiteta , get zahtev, uloga administratora */
	// GET: vakcine/details?id=1
	@GetMapping(value="/ulogaAdministrator/details")
	@ResponseBody
	public String detailsUlogaAdministrator(@RequestParam Long id) {	
		Vakcina vakcina = vakcinaService.findOne(id);
		
		StringBuilder retVal = new StringBuilder();
		retVal.append(
				"<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
				"	<meta charset=\"UTF-8\">\r\n" + 
	    		"	<base href=\""+ bURL + "ulogaAdministrator"+"\">" + 
				"	<title>Knjiga</title>\r\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviForma.css\"/>\r\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviHorizontalniMeni.css\"/>\r\n"+
				"</head>\r\n" + 
				"<body>\r\n" + 				
				"	<form method=\"post\" action=\"vakcine/ulogaAdministrator/edit\">\r\n" + 
				"		<input type=\"hidden\" name=\"id\" value=\"" + vakcina.getId() + "\">\r\n" + 
				"		<table>\r\n" + 
				"			<caption>Vakcina</caption>\r\n" + 
				"			<tr><th>Naziv vakcine: </th><td><input type=\"text\" "+
				"			value=\""+ vakcina.getNazivVakcine() +"\" name=\"nazivVakcine\"/></td></tr>\r\n" + 
//				"			<tr><th>Naziv proizvodjaca: </th><td><input type=\"text\" "+
//				"			value=\""+ vakcina.getNazivProizvodjaca() +"\" name=\"nazivProizvodjaca\"/></td></tr>\r\n" + 
// 				"			<tr><th>Zemlja proizvodjac: </th><td><input type=\"text\" "+
// 				"				value=\"" + vakcina.getZemljaProizvodjac() +"\" name=\"zemljaProizvodjac\"/></td></tr>\r\n" + 
//				"			<tr><th>Datum poslednje isporuke: </th><td><input type=\"text\" "+
//				"			value=\""+ vakcina.getDatumPoslednjeIsporuke() +"\" name=\"datumPoslednjeIsporuke\"/></td></tr>\r\n" + 
				"			<tr><th>Dostupna kolicina: </th><td><input type=\"number\" min=\"1\" "+
				 				"value=\""+ vakcina.getDostupnaKolicina() +"\" name=\"dostupnaKolicina\"/></td></tr>\r\n" + 
				"			<tr><th></th><td><input type=\"submit\" value=\"Izmeni\" /></td>\r\n" + 
				"		</table>\r\n" + 
				"	</form>\r\n" + 
				"	<br/>\r\n" + 
//				"	<form method=\"post\" action=\"vakcine/delete\">\r\n" + 
//				"		<input type=\"hidden\" name=\"id\" value=\""+ vakcina.getId() +"\">\r\n" + 
//				"		<table>\r\n" + 
//				"			<tr><th></th><td><input type=\"submit\" value=\"Obrisi\"></td>\r\n" + 
//				"		</table>\r\n" + 
//				"	</form>\r\n" +
				"	<br/>\r\n" + 
				"	<br/>\r\n"); 
		
		retVal.append(
				"</body>\r\n"+
				"</html>\r\n");		
		return retVal.toString();
	}
	
	/** pribavnjanje HTML stanice za prikaz određenog entiteta , get zahtev, uloga administratora */
	// GET: vakcine/details?id=1
	@GetMapping(value="/ulogaMedicinskiRadnik/details")
	@ResponseBody
	public String detailsUlogaMedicinskiRadnik(@RequestParam Long id) {	
		Vakcina vakcina = vakcinaService.findOne(id);
		
		StringBuilder retVal = new StringBuilder();
		retVal.append(
				"<!DOCTYPE html>\r\n" + 
				"<html>\r\n" + 
				"<head>\r\n" + 
				"	<meta charset=\"UTF-8\">\r\n" + 
	    		"	<base href=\""+ bURL + "ulogaMedicinskiRadnik"+"\">" + 
				"	<title>Knjiga</title>\r\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviForma.css\"/>\r\n" + 
				"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviHorizontalniMeni.css\"/>\r\n"+
				"</head>\r\n" + 
				"<body>\r\n" + 				
				"	<form method=\"post\" action=\"vakcine/ulogaMedicinskiRadnik/edit\">\r\n" + 
				"		<input type=\"hidden\" name=\"id\" value=\"" + vakcina.getId() + "\">\r\n" + 
				"		<table>\r\n" + 
				"			<caption>Vakcina</caption>\r\n" + 
				"			<tr><th>Naziv vakcine: </th><td><input type=\"text\" "+
				"			value=\""+ vakcina.getNazivVakcine() +"\" name=\"nazivVakcine\"/></td></tr>\r\n" + 
//				"			<tr><th>Naziv proizvodjaca: </th><td><input type=\"text\" "+
//				"			value=\""+ vakcina.getNazivProizvodjaca() +"\" name=\"nazivProizvodjaca\"/></td></tr>\r\n" + 
// 				"			<tr><th>Zemlja proizvodjac: </th><td><input type=\"text\" "+
// 				"				value=\"" + vakcina.getZemljaProizvodjac() +"\" name=\"zemljaProizvodjac\"/></td></tr>\r\n" + 
//				"			<tr><th>Datum poslednje isporuke: </th><td><input type=\"text\" "+
//				"			value=\""+ vakcina.getDatumPoslednjeIsporuke() +"\" name=\"datumPoslednjeIsporuke\"/></td></tr>\r\n" + 
				"			<tr><th>Dostupna kolicina: </th><td><input type=\"number\" min=\"1\" "+
				 				"value=\""+ vakcina.getDostupnaKolicina() +"\" name=\"dostupnaKolicina\"/></td></tr>\r\n" + 
				"			<tr><th></th><td><input type=\"submit\" value=\"Izmeni\" /></td>\r\n" + 
				"		</table>\r\n" + 
				"	</form>\r\n" + 
				"	<br/>\r\n" + 
//				"	<form method=\"post\" action=\"vakcine/delete\">\r\n" + 
//				"		<input type=\"hidden\" name=\"id\" value=\""+ vakcina.getId() +"\">\r\n" + 
//				"		<table>\r\n" + 
//				"			<tr><th></th><td><input type=\"submit\" value=\"Obrisi\"></td>\r\n" + 
//				"		</table>\r\n" + 
//				"	</form>\r\n" +
				"	<br/>\r\n" + 
				"	<br/>\r\n"); 
		
		retVal.append(
				"</body>\r\n"+
				"</html>\r\n");		
		return retVal.toString();
	}
	

}
