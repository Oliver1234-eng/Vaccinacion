package com.ftn.SlozeniOblikVezbiProjekat.listeners;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.ftn.SlozeniOblikVezbiProjekat.controller.VakcineController;
import com.ftn.SlozeniOblikVezbiProjekat.model.Vakcina;

public class InitHttpSessionListener implements HttpSessionListener {
	
	/** kod koji se izvrsava po kreiranju sesije */
	public void sessionCreated(HttpSessionEvent arg0) {
		System.out.println("Inicijalizacija sesije HttpSessionListener...");

		HttpSession session  = arg0.getSession();
		System.out.println("Session id korisnika je " + session.getId());
		
		System.out.println("Uspeh HttpSessionListener!");
	}
	
	/** kod koji se izvrsava po brisanju sesije */
	public void sessionDestroyed(HttpSessionEvent arg0) {
		System.out.println("Brisanje sesije HttpSessionListener...");
		
		System.out.println("Uspeh HttpSessionListener!");
	}

}
