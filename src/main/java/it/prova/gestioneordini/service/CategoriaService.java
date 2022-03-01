package it.prova.gestioneordini.service;

import java.util.List;

import it.prova.gestioneordini.dao.categoria.CategoriaDAO;
import it.prova.gestioneordini.model.Articolo;
import it.prova.gestioneordini.model.Categoria;
import it.prova.gestioneordini.model.Ordine;

public interface CategoriaService {

	public List<Categoria> listAll() throws Exception;

	public Categoria caricaSingoloElemento(Long id) throws Exception;

	public void aggiorna(Categoria categoriaInstance) throws Exception;

	public void inserisciNuovo(Categoria categoriaInstance) throws Exception;

	public void rimuovi(Categoria categoriaInstance) throws Exception;

	void creaECollegaCategoriaEArticolo(Categoria categoriaTransientInstance, Articolo articoloTransientInstance)
			throws Exception;
	
	void aggiungiArticolo(Categoria categoriaInstance, Articolo articoloInstance) throws Exception;
	
	public Categoria caricaCategoriaConArticoli(Categoria categoriaInstance) throws Exception;
	
	public List<Categoria> dammiTutteLeCategorieInOrdine(Ordine ordineInput) throws Exception;

	// per injection
	public void setCategoriaDAO(CategoriaDAO categoriaDAO);


}
