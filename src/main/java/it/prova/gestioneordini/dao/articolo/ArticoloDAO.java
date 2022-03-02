package it.prova.gestioneordini.dao.articolo;

import it.prova.gestioneordini.dao.IBaseDAO;
import it.prova.gestioneordini.model.Articolo;
import it.prova.gestioneordini.model.Categoria;
import it.prova.gestioneordini.model.Ordine;

public interface ArticoloDAO extends IBaseDAO<Articolo> {

	public Articolo caricaArticoloConCategoria() throws Exception;

	public void rimuoviArticoloSenzaAltriCampi(Articolo input) throws Exception;

	public Long dammiIlTotaleDelCostoDiArticoliConCategoria(Categoria categoriaInput) throws Exception;

	public Long dammiTotaleCostoDiUnaPersona(Ordine ordineInput) throws Exception;

}
