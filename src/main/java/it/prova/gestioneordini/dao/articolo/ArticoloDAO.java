package it.prova.gestioneordini.dao.articolo;

import it.prova.gestioneordini.dao.IBaseDAO;
import it.prova.gestioneordini.model.Articolo;
import it.prova.gestioneordini.model.Categoria;

public interface ArticoloDAO extends IBaseDAO<Articolo> {

	public Articolo caricaArticoloConCategoria() throws Exception;
	
	public void rimuoviArticoloSenzaAltriCampi(Articolo input) throws Exception;
	
	public Long dammiIlTotaleDelCostoDiArticoliConCategoria(Categoria categoriaInput) throws Exception;

}
