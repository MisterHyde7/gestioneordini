package it.prova.gestioneordini.service;

import it.prova.gestioneordini.dao.MyDaoFactory;

public class MyServiceFactory {

	private static CategoriaService categoriaServiceInstance = null;
	private static ArticoloService articoloServiceInstance = null;
	private static OrdineService ordineServiceInstance = null;

	public static CategoriaService getCategoriaServiceInstance() {
		if (categoriaServiceInstance == null)
			categoriaServiceInstance = new CategoriaServiceImpl();

		categoriaServiceInstance.setCategoriaDAO(MyDaoFactory.getCategoriaDAOInstance());

		return categoriaServiceInstance;
	}

	public static ArticoloService getArticoloServiceInstance() {
		if (articoloServiceInstance == null)
			articoloServiceInstance = new ArticoloServiceImpl();

		articoloServiceInstance.setArticoloDAO(MyDaoFactory.getArticoloDAOInstance());

		return articoloServiceInstance;
	}

	public static OrdineService getOrdineServiceInstance() {
		if (ordineServiceInstance == null)
			ordineServiceInstance = new OrdineServiceImpl();

		ordineServiceInstance.setOrdineDAO(MyDaoFactory.getOrdineDAOInstance());

		return ordineServiceInstance;
	}

}
