package com.ftn.SlozeniOblikVezbiProjekat.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ftn.SlozeniOblikVezbiProjekat.model.UlogaKorisnika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.ServletContextAware;

import com.ftn.SlozeniOblikVezbiProjekat.model.Korisnik;
import com.ftn.SlozeniOblikVezbiProjekat.service.KorisnikService;

@Controller
@RequestMapping(value = "/korisnici")
public class KorisnikController implements ServletContextAware {
	
	public static final String KORISNIK_KEY = "korisnik";
	
	@Autowired
	private ServletContext servletContext;
	private String bURL;
	
	@Autowired
	private KorisnikService korisnikService;
	
	/** inicijalizacija podataka za kontroler */
	@PostConstruct
	public void init() {	
		bURL = servletContext.getContextPath()+"/";
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
	@GetMapping(value = "/login")
	public void getLogin(@RequestParam(required = false) String jmbg, @RequestParam(required = false) String sifra,
						 @RequestParam(required = false) UlogaKorisnika ulogaKorisnika,
						 HttpSession session, HttpServletResponse response) throws IOException {
		postLogin(jmbg, sifra, ulogaKorisnika, session, response);
	}
	
	@PostMapping(value = "/login")
	@ResponseBody
	public void postLogin(@RequestParam(required = false) String jmbg, @RequestParam(required = false) String sifra,
						  @RequestParam(required = false) UlogaKorisnika ulogaKorisnika,
						  HttpSession session, HttpServletResponse response) throws IOException {
		
		Korisnik korisnik = korisnikService.findOne(jmbg, sifra);
		String greska = "";
		if (korisnik == null)
			greska = "neispravni kredencijali<br/>";

		if (!greska.equals("")) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out;
			out = response.getWriter();

			StringBuilder retVal = new StringBuilder();
			retVal.append("<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "	<meta charset=\"UTF-8\">\r\n"
					+ "	<base href=\"/SlozeniOblikVezbiProjekat/\">	\r\n" + "	<title>Prijava korisnika</title>\r\n"
					+ "	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviForma.css\"/>\r\n"
					+ "	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviHorizontalniMeni.css\"/>\r\n"
					+ "</head>\r\n" + "<body>\r\n");
			if (!greska.equals(""))
				retVal.append("	<div>" + greska + "</div>\r\n");
			retVal.append("	<form method=\"post\" action=\"korisnici/login\">\r\n" + "		<table>\r\n"
					+ "			<caption>Prijava korisnika na sistem</caption>\r\n"
					+ "			<tr><th>JMBG:</th><td><input type=\"text\" value=\"\" name=\"jmbg\" required/></td></tr>\r\n"
					+ "			<tr><th>Sifra:</th><td><input type=\"password\" value=\"\" name=\"sifra\" required/></td></tr>\r\n"
					+ "			<tr><th></th><td><input type=\"submit\" value=\"Prijavi se\" /></td>\r\n"
					+ "		</table>\r\n" + "	</form>\r\n" + "	<br/>\r\n" + "</body>\r\n" + "</html>");

			out.write(retVal.toString());
			return;
		} else if (session.getAttribute(KORISNIK_KEY) != null)
			greska = "korisnik je već prijavljen na sistem morate se prethodno odjaviti<br/>";

		if (!greska.equals("")) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out;
			out = response.getWriter();

			StringBuilder retVal = new StringBuilder();
			retVal.append("<!DOCTYPE html>\r\n" + "<html>\r\n" + "<head>\r\n" + "	<meta charset=\"UTF-8\">\r\n"
					+ "	<base href=\"/SlozeniOblikVezbiProjekat/\">	\r\n" + "	<title>Prijava korisnika</title>\r\n"
					+ "	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviForma.css\"/>\r\n"
					+ "	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviHorizontalniMeni.css\"/>\r\n"
					+ "</head>\r\n" + "<body>\r\n");
			
			if (!greska.equals(""))
				retVal.append("	<div>" + greska + "</div>\r\n");
			retVal.append("	<a href=\"index.html\">Povratak</a>\r\n" + "	<br/>\r\n" + "</body>\r\n" + "</html>");

			out.write(retVal.toString());
			return;
		} else {
			session.setAttribute(KORISNIK_KEY, korisnik);

			System.out.println("Uloga korisnika: " + korisnik.getUlogaKorisnika());
			if (korisnik.getUlogaKorisnika() == UlogaKorisnika.MedicinskiRadnik) {
				response.sendRedirect(bURL + "vakcine/ulogaMedicinskiRadnik");
			} else if (korisnik.getUlogaKorisnika() == UlogaKorisnika.Administrator) {
				response.sendRedirect(bURL + "vakcine/ulogaAdministrator");
			} else {
				System.out.println("Nema takve uloge");
			}
		}
	}
	
	@GetMapping(value="/logout")
	@ResponseBody
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {	

		Korisnik korisnik = (Korisnik) request.getSession().getAttribute(KORISNIK_KEY);
		String greska = "";
		if(korisnik==null)
			greska="korisnik nije prijavljen<br/>";
		
		if(!greska.equals("")) {
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out;	
			out = response.getWriter();
			
			StringBuilder retVal = new StringBuilder();
			retVal.append(
					"<!DOCTYPE html>\r\n" + 
					"<html>\r\n" + 
					"<head>\r\n" +
					"	<meta charset=\"UTF-8\">\r\n" + 
					"	<base href=\"/SlozeniOblikVezbiProjekat/\">	\r\n" + 
					"	<title>Prijava korisnika</title>\r\n" + 
					"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviForma.css\"/>\r\n" + 
					"	<link rel=\"stylesheet\" type=\"text/css\" href=\"css/StiloviHorizontalniMeni.css\"/>\r\n" + 
					"</head>\r\n" + 
					"<body>\r\n");
			if(!greska.equals(""))
				retVal.append(
					"	<div>"+greska+"</div>\r\n");
			retVal.append(
					"	<form method=\"post\" action=\"PrijavaOdjava/Login\">\r\n" + 
					"		<table>\r\n" + 
					"			<caption>Prijava korisnika na sistem</caption>\r\n" + 
					"			<tr><th>JMBG:</th><td><input type=\"text\" value=\"\" name=\"jmbg\" required/></td></tr>\r\n" +
					"			<tr><th>Sifra:</th><td><input type=\"password\" value=\"\" name=\"sifra\" required/></td></tr>\r\n" + 
					"			<tr><th></th><td><input type=\"submit\" value=\"Prijavi se\" /></td>\r\n" + 
					"		</table>\r\n" + 
					"	</form>\r\n" + 
					"	<br/>\r\n" + 
					"	<ul>\r\n" + 
					"		<li><a href=\"korisnici/logout\">Odjavi se</a></li>\r\n" + 
					"	</ul>" +
					"</body>\r\n" + 
					"</html>");
			
			out.write(retVal.toString());
			return;
		}
		
		
		request.getSession().removeAttribute(KORISNIK_KEY);
		request.getSession().invalidate();
		response.sendRedirect(bURL);
	}
}

