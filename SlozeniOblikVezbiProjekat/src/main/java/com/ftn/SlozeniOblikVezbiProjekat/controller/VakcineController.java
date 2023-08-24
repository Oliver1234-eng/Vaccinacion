package com.ftn.SlozeniOblikVezbiProjekat.controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import com.ftn.SlozeniOblikVezbiProjekat.service.impl.VakcinaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;

import com.ftn.SlozeniOblikVezbiProjekat.model.Vakcina;
import com.ftn.SlozeniOblikVezbiProjekat.service.VakcinaService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/vakcine")
public class VakcineController implements ServletContextAware {
	
	@Autowired
	private ServletContext servletContext;
	private String bURL;
	
	@Autowired
	private VakcinaService vakcinaService;

	@Autowired
	private VakcinaServiceImpl vakcinaServiceImpl;

	@Value("${vakcine.pathToFile}")
	private String pathToFile;
	
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

	@RequestMapping(value = "/nabavi", method = RequestMethod.POST)
	public String nabaviVakcine(@RequestParam Long vakcinaId, @RequestParam int kolicina, RedirectAttributes redirectAttributes) {

		if (kolicina < 0) {
			redirectAttributes.addFlashAttribute("errorMessage", "Nabavljena količina ne može biti negativna.");
			return "redirect:/vakcine/ulogaAdministrator";
		}

		Map<Long, Vakcina> vakcine = vakcinaServiceImpl.readFromFile();

		Vakcina vakcina = vakcine.get(vakcinaId);
		if (vakcina != null) {
			int dostupnaKolicina = vakcina.getDostupnaKolicina();
			int novaKolicina = dostupnaKolicina + kolicina;
			vakcina.setDostupnaKolicina(novaKolicina);
			vakcina.setDatumPoslednjeIsporuke(LocalDateTime.now());

			vakcinaServiceImpl.saveToFile(vakcine);

			redirectAttributes.addFlashAttribute("successMessage", "Uspešno nabavljena količina vakcina za ID " + vakcinaId);

			return "redirect:/vakcine/ulogaAdministrator";
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "Vakcina sa ID " + vakcinaId + " nije pronađena");
			return "redirect:/vakcine/ulogaAdministrator";
		}
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
						"<form action=\"korisnici/logout\" method=\"get\"><input type=\"submit\" value=\"Izloguj se\"> </form>" +
				"		<table>\r\n" + 
				"			<caption>Vakcine (uloga administrator)</caption>\r\n" +
				"			<tr>\r\n" + 
				"				<th>R. br.</th>\r\n" +
				"				<th>Naziv vakcine</th>\r\n" + 
				"				<th>Naziv proizvodjaca</th>\r\n" + 
				"				<th>Zemlja proizvodjac</th>\r\n" +
				"				<th>Datum poslednje isporuke</th>\r\n" +
				"				<th>Dostupna kolicina</th>\r\n" +
				"				<th>Nabavka vakcine</th>\r\n" +
				"			</tr>\r\n");
		
		for (int i=0; i < vakcine.size(); i++) {
			Vakcina vakcinaIt = vakcine.get(i);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
			String formatiranDatum = vakcinaIt.getDatumPoslednjeIsporuke().format(formatter);
			retVal.append(
				"			<tr>\r\n" + 
				"				<td class=\"broj\">"+ (i+1) +"</td>\r\n" +
				"				<td>"+ vakcinaIt.getNazivVakcine() +"</td>\r\n" +
				"				<td>"+ vakcinaIt.getNazivProizvodjaca() +"</td>\r\n" +
				"				<td>"+ vakcinaIt.getZemljaProizvodjac() +"</td>\r\n" +
				"				<td>"+ formatiranDatum +"</td>\r\n" +
				"				<td class=\"broj\">"+ vakcinaIt.getDostupnaKolicina() +"</td>\r\n" +
						"    <td>" +
						"        <form method=\"post\" action=\"/SlozeniOblikVezbiProjekat/vakcine/nabavi\">\r\n" +
						"            <input type=\"hidden\" name=\"vakcinaId\" value=\"" + vakcinaIt.getId() + "\"/>\r\n" +
						"            <label for=\"kolicina\">Količina:</label>\r\n" +
						"            <input type=\"number\" id=\"kolicina\" name=\"kolicina\" required/>\r\n" +
						"            <input type=\"submit\" value=\"Nabavi vakcine\"/>\r\n" +
						"        </form>\r\n" +
						"    </td>\r\n" +
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

	@RequestMapping(value = "/smanjiKolicinu", method = RequestMethod.POST)
	public String smanjiKolicinu(@RequestParam Long vakcinaId, RedirectAttributes redirectAttributes) {
		Map<Long, Vakcina> vakcine = vakcinaServiceImpl.readFromFile();

		Vakcina vakcina = vakcine.get(vakcinaId);
		if (vakcina != null) {
			int dostupnaKolicina = vakcina.getDostupnaKolicina();
			if (dostupnaKolicina > 0) {
				vakcina.setDostupnaKolicina(dostupnaKolicina - 1);

				vakcinaServiceImpl.saveToFile(vakcine);

				redirectAttributes.addFlashAttribute("successMessage", "Uspešno smanjena količina za vakcinu sa ID " + vakcinaId);

				return "redirect:/vakcine/ulogaMedicinskiRadnik";
			} else {
				redirectAttributes.addFlashAttribute("errorMessage", "Nema više dostupnih vakcina za ID " + vakcinaId);
				return "redirect:/vakcine/ulogaMedicinskiRadnik";
			}
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "Vakcina sa ID " + vakcinaId + " nije pronađena");
			return "redirect:/vakcine/ulogaMedicinskiRadnik";
		}
	}
	
	/** pribavnjanje HTML stanice za prikaz svih entiteta, get zahtev, uloga medicinskog radnika */
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
						"<form action=\"korisnici/logout\" method=\"get\"><input type=\"submit\" value=\"Izloguj se\"> </form>" +
				"		<table>\r\n" + 
				"			<caption>Vakcine (uloga medicinski radnik)</caption>\r\n" +
				"			<tr>\r\n" + 
				"				<th>R. br.</th>\r\n" +
				"				<th>Naziv vakcine</th>\r\n" + 
				"				<th>Naziv proizvodjaca</th>\r\n" + 
				"				<th>Zemlja proizvodjac</th>\r\n" +
				"				<th>Datum poslednje isporuke</th>\r\n" +
				"				<th>Dostupna kolicina</th>\r\n" +
				"				<th>Uzimanje vakcine</th>\r\n" +
				"			</tr>\r\n");
		
		for (int i=0; i < vakcine.size(); i++) {
			Vakcina vakcinaIt = vakcine.get(i);
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
			String formatiranDatum = vakcinaIt.getDatumPoslednjeIsporuke().format(formatter);
			retVal.append(
				"			<tr>\r\n" + 
				"				<td class=\"broj\">"+ (i+1) +"</td>\r\n" +
				"				<td>"+ vakcinaIt.getNazivVakcine() +"</td>\r\n" +
				"				<td>"+ vakcinaIt.getNazivProizvodjaca() +"</td>\r\n" +
				"				<td>"+ vakcinaIt.getZemljaProizvodjac() +"</td>\r\n" +
				"				<td>"+ formatiranDatum +"</td>\r\n" +
				"				<td class=\"broj\">"+ vakcinaIt.getDostupnaKolicina() +"</td>\r\n" +
						"        <td>\r\n" +
						"            <form action=\"vakcine/smanjiKolicinu\" method=\"POST\">\r\n" +
						"                <input type=\"hidden\" name=\"vakcinaId\" value=\"" + vakcinaIt.getId() + "\">\r\n" +
						"                <input type=\"submit\" value=\"Daj vakcinu\">\r\n" +
						"            </form>\r\n" +
						"        </td>\r\n" +
				"			</tr>\r\n");
			
		}
		retVal.append(
				"		</table>\r\n");	
		
		retVal.append(
				"</body>\r\n"+
				"</html>\r\n");		
		return retVal.toString();
	}

	private List<String> getAllProizvodjaci() {
		List<String> proizvodjaci = new ArrayList<>();
		try {
			Path path = Paths.get(pathToFile);
			List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

			for (String line : lines) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;

				String[] tokens = line.split(";");
				if (tokens.length >= 4) {
					String proizvodjac = tokens[2].trim();
					if (!proizvodjaci.contains(proizvodjaci)) {
						proizvodjaci.add(proizvodjac);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return proizvodjaci;
	}

	private List<String> getAllZemljeProizvodjaci() {
		List<String> zemlje = new ArrayList<>();
		try {
			Path path = Paths.get(pathToFile);
			List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);

			for (String line : lines) {
				line = line.trim();
				if (line.equals("") || line.indexOf('#') == 0)
					continue;

				String[] tokens = line.split(";");
				if (tokens.length >= 4) {
					String zemljaProizvodjac = tokens[3].trim();
					if (!zemlje.contains(zemljaProizvodjac)) {
						zemlje.add(zemljaProizvodjac);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return zemlje;
	}
	
	/** pribavnjanje HTML stanice za unos novog entiteta, get zahtev, uloga administratora */
	@GetMapping(value = "ulogaAdministrator/add")
	@ResponseBody
	public String create() {
		List<String> proizvodjaci = getAllProizvodjaci();
		List<String> zemljeProizvodjaci = getAllZemljeProizvodjaci();

		StringBuilder retVal = new StringBuilder();
		retVal.append("<!DOCTYPE html>\r\n" +
				"<html>\r\n" +
				"<head>\r\n" +
				"    <meta charset=\"UTF-8\">\r\n" +
				"    <base href=\"" + bURL + "ulogaAdministrator" + "\">" +
				"    <title>Dodaj vakcinu</title>\r\n" +
				"    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviForma.css\"/>\r\n" +
				"    <link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviHorizontalniMeni.css\"/>\r\n" +
				"</head>\r\n" +
				"<body>\r\n" +
				"    <form method=\"post\" action=\"vakcine/ulogaAdministrator/add\">\r\n" +
				"        <table>\r\n" +
				"            <caption>Vakcina</caption>\r\n" +
				"            <tr><th>Naziv vakcine: </th><td><input type=\"text\" value=\"\" name=\"nazivVakcine\"/></td></tr>\r\n" +
						"    <tr><th>Naziv proizvođača: </th><td><select name=\"nazivProizvodjaca\">\r\n");

		for (String naziv : proizvodjaci) {
			retVal.append("<option value=\"" + naziv + "\">" + naziv + "</option>\r\n");
		}

		retVal.append("</select></td></tr>\r\n" +
				"            <tr><th>Zemlja proizvođača: </th><td><select name=\"zemljaProizvodjaca\">\r\n");

		for (String zemlja : zemljeProizvodjaci) {
			retVal.append("<option value=\"" + zemlja + "\">" + zemlja + "</option>\r\n");
		}

		retVal.append("</select></td></tr>\r\n" +
				"            <tr><th></th><td><input type=\"submit\" value=\"Dodaj\" /></td></tr>\r\n" +
				"        </table>\r\n" +
				"    </form>\r\n" +
				"    <br/>\r\n" +
				"</body>\r\n" +
				"</html>\r\n");

		return retVal.toString();
	}
	
	/** obrada podataka forme za unos novog entiteta, post zahtev */
	@PostMapping(value = "ulogaAdministrator/add")
	public void create(@RequestParam String nazivVakcine, @RequestParam String nazivProizvodjaca, @RequestParam String zemljaProizvodjaca, HttpServletResponse response) throws IOException {
		LocalDateTime datumPoslednjeIsporuke = LocalDateTime.now();
		int dostupnaKolicina = 0;

		Vakcina vakcina = new Vakcina(nazivVakcine, nazivProizvodjaca, zemljaProizvodjaca, datumPoslednjeIsporuke, dostupnaKolicina);
		Vakcina saved = vakcinaService.save(vakcina);
		response.sendRedirect(bURL + "vakcine/ulogaAdministrator");
	}
	
	/** obrada podataka forme za izmenu postojećeg entiteta, post zahtev, uloga administratora */
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
				"			<tr><th>Naziv vakcine za nabavku: </th><td><input type=\"text\" "+
				"			value=\""+ vakcina.getNazivVakcine() +"\" name=\"nazivVakcine\"/></td></tr>\r\n" + 
				"			<tr><th>Nova dostupna kolicina (prethodno stanje + nabavljeno): </th><td><input type=\"number\" min=\"1\" "+
				 				"value=\""+ vakcina.getDostupnaKolicina() +"\" name=\"dostupnaKolicina\"/></td></tr>\r\n" + 
				"			<tr><th></th><td><input type=\"submit\" value=\"Sacuvaj podatke o nabavci\" /></td>\r\n" + 
				"		</table>\r\n" + 
				"	</form>\r\n" + 
				"	<br/>\r\n" + 
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
				"			<tr><th>Daj vakcinu:  </th><td><input type=\"text\" "+
				"			value=\""+ vakcina.getNazivVakcine() +"\" name=\"nazivVakcine\"/></td></tr>\r\n" + 
				"			<tr><th>Uzmi jednu (kolicina - 1): </th><td><input type=\"number\" min=\"1\" "+
				 				"value=\""+ vakcina.getDostupnaKolicina() +"\" name=\"dostupnaKolicina\"/></td></tr>\r\n" + 
				"			<tr><th></th><td><input type=\"submit\" value=\"Uzmi vakcinu\" /></td>\r\n" + 
				"		</table>\r\n" + 
				"	</form>\r\n" + 
				"	<br/>\r\n" + 
				"	<br/>\r\n" + 
				"	<br/>\r\n"); 
		
		retVal.append(
				"</body>\r\n"+
				"</html>\r\n");		
		return retVal.toString();
	}
	

}
