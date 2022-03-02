package it.prova.gestioneordini.service;

import java.util.List;

import it.prova.gestioneordini.dao.articolo.ArticoloDAO;
import it.prova.gestioneordini.dao.categoria.CategoriaDAO;
import it.prova.gestioneordini.model.Articolo;
import it.prova.gestioneordini.model.Categoria;
import it.prova.gestioneordini.model.Ordine;

public interface ArticoloService {

	public List<Articolo> listAll() throws Exception;

	public Articolo caricaSingoloElemento(Long id) throws Exception;

	public void aggiorna(Articolo articoloInstance) throws Exception;

	public void inserisciNuovo(Articolo articoloInstance) throws Exception;

	public void rimuovi(Articolo articoloInstance) throws Exception;

	void aggiungiCategoria(Categoria categoriaInstance, Articolo articoloInstance) throws Exception;

	public Articolo caricaArticoloConCategoria(Articolo articoloInstance) throws Exception;

	public void rimuoviArticoloSenzaCampi(Articolo articoloInstance) throws Exception;
	
	public void dissociaArticoloDaiCampi(Long idArticolo, Long idCategoria) throws Exception;
	
	public Long calcolaTotaleOrdinePerCategoria(Categoria categoriaInput) throws Exception;
	
	public Long dammiTotaleDaPagareDi(Ordine input) throws Exception;

	// per injection
	public void setArticoloDAO(ArticoloDAO articoloDAO);
	
	public void setCategoriaDAO(CategoriaDAO categoriaDAO);

}
