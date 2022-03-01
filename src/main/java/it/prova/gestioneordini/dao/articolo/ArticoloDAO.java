package it.prova.gestioneordini.dao.articolo;

import it.prova.gestioneordini.dao.IBaseDAO;
import it.prova.gestioneordini.model.Articolo;

public interface ArticoloDAO extends IBaseDAO<Articolo> {

	public Articolo caricaArticoloConCategoria() throws Exception;
	
	public void rimuoviArticoloSenzaAltriCampi(Articolo input) throws Exception;

}
