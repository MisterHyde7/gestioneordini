package it.prova.gestioneordini.service;

import java.util.List;

import it.prova.gestioneordini.dao.ordine.OrdineDAO;
import it.prova.gestioneordini.model.Articolo;
import it.prova.gestioneordini.model.Ordine;

public interface OrdineService {

	public List<Ordine> listAll() throws Exception;

	public Ordine caricaSingoloElemento(Long id) throws Exception;

	public void aggiorna(Ordine ordineInstance) throws Exception;

	public void inserisciNuovo(Ordine ordineInstance) throws Exception;

	public void rimuovi(Ordine ordineInstance) throws Exception;
	
	public void aggiungiArticolo(Ordine ordineInstance, Articolo articoloInstance) throws Exception;
	
	public Ordine caricaOrdineConArticoli(Ordine ordineInstances) throws Exception;
	
	public Ordine caricaOrdineEager(Ordine ordineInstances) throws Exception;

	// per injection
	public void setOrdineDAO(OrdineDAO ordineDAO);

}
