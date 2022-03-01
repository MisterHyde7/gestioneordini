package it.prova.gestioneordini.dao;

import it.prova.gestioneordini.dao.articolo.ArticoloDAO;
import it.prova.gestioneordini.dao.articolo.ArticoloDAOImpl;
import it.prova.gestioneordini.dao.categoria.CategoriaDAO;
import it.prova.gestioneordini.dao.categoria.CategoriaDAOImpl;
import it.prova.gestioneordini.dao.ordine.OrdineDAO;
import it.prova.gestioneordini.dao.ordine.OrdineDAOImpl;

public class MyDaoFactory {

	private static CategoriaDAO categoriaDaoInstance = null;
	private static OrdineDAO ordineDaoInstance = null;
	private static ArticoloDAO articoloDaoInstance = null;

	public static CategoriaDAO getCategoriaDAOInstance() {
		if (categoriaDaoInstance == null)
			categoriaDaoInstance = new CategoriaDAOImpl();

		return categoriaDaoInstance;
	}

	public static OrdineDAO getOrdineDAOInstance() {
		if (ordineDaoInstance == null)
			ordineDaoInstance = new OrdineDAOImpl();

		return ordineDaoInstance;
	}

	public static ArticoloDAO getArticoloDAOInstance() {
		if (articoloDaoInstance == null)
			articoloDaoInstance = new ArticoloDAOImpl();

		return articoloDaoInstance;
	}

}
