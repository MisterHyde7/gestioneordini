package it.prova.gestioneordini.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import it.prova.gestioneordini.dao.EntityManagerUtil;
import it.prova.gestioneordini.model.Articolo;
import it.prova.gestioneordini.model.Categoria;
import it.prova.gestioneordini.model.Ordine;
import it.prova.gestioneordini.service.ArticoloService;
import it.prova.gestioneordini.service.CategoriaService;
import it.prova.gestioneordini.service.MyServiceFactory;
import it.prova.gestioneordini.service.OrdineService;

public class MyTest {

	public static void main(String[] args) {
		CategoriaService categoriaServiceInstance = MyServiceFactory.getCategoriaServiceInstance();
		ArticoloService articoloServiceInstance = MyServiceFactory.getArticoloServiceInstance();
		OrdineService ordineServiceInstance = MyServiceFactory.getOrdineServiceInstance();

		try {

			testInsertOrdine(ordineServiceInstance);

			testInsertCategoria(categoriaServiceInstance);

			testInsertArticoloAdOrdine(ordineServiceInstance, articoloServiceInstance);

			testInsertArticoloACategoria(categoriaServiceInstance, articoloServiceInstance, ordineServiceInstance);

			testInsertCategoriaAdArticolo(categoriaServiceInstance, articoloServiceInstance, ordineServiceInstance);

			testUpdateOrdine(ordineServiceInstance);

			testRemoveDiArticoloConCategoria(articoloServiceInstance, categoriaServiceInstance, ordineServiceInstance);

			testCaricaOrdineEager(ordineServiceInstance, articoloServiceInstance, categoriaServiceInstance);

		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			// questa è necessaria per chiudere tutte le connessioni quindi rilasciare il
			// main
			EntityManagerUtil.shutdown();
		}

	}

	private static void testInsertOrdine(OrdineService ordineService) throws Exception {
		System.out.println("========== Inizio test ==========");

		Ordine ordineDaInserire = new Ordine(null, "via prova", new SimpleDateFormat("dd/MM/yyyy").parse("01/01/2022"));
		if (ordineDaInserire.getId() != null)
			throw new RuntimeException("ordine gia esistente");

		ordineService.inserisciNuovo(ordineDaInserire);

		if (ordineDaInserire.getId() == null)
			throw new RuntimeException("ordine non inserito correttamente");

		ordineService.rimuovi(ordineDaInserire);

		System.out.println("========== test eseguito con successo ==========");
	}

	private static void testInsertCategoria(CategoriaService categoriaService) throws Exception {
		System.out.println("========== Inizio test ==========");

		Categoria categoriaDaInserire = new Categoria("music", "music1");
		if (categoriaDaInserire.getId() != null)
			throw new RuntimeException("categoria gia esistente");

		categoriaService.inserisciNuovo(categoriaDaInserire);

		if (categoriaDaInserire.getId() == null)
			throw new RuntimeException("categoria non inserita correttamente");

		System.out.println("========== test eseguito con successo ==========");
	}

	private static void testInsertArticoloAdOrdine(OrdineService ordineService, ArticoloService articoloService)
			throws Exception {

		System.out.println("========== Inizio test ==========");

		Ordine ordineDaRiempire = new Ordine("carlo", "via carlo", new Date());
		ordineService.inserisciNuovo(ordineDaRiempire);
		if (ordineDaRiempire.getId() == null)
			throw new RuntimeException("ordine non inserito");

		Articolo articoloDaInserireInOrdine = new Articolo("pupazzo", "pupazzo01", 10, new Date());
		articoloDaInserireInOrdine.setOrdine(ordineDaRiempire);
		articoloService.inserisciNuovo(articoloDaInserireInOrdine);
		if (articoloDaInserireInOrdine.getId() == null)
			throw new RuntimeException("articolo non inserito");

		ordineService.aggiungiArticolo(ordineDaRiempire, articoloDaInserireInOrdine);

		if (ordineService.caricaOrdineConArticoli(ordineDaRiempire).getArticoli().size() != 1)
			throw new RuntimeException("articolo non inserito nell'ordine");

		System.out.println("========== test eseguito con successo ==========");
	}

	private static void testInsertArticoloACategoria(CategoriaService categoriaService, ArticoloService articoloService,
			OrdineService ordineService) throws Exception {

		System.out.println("========== Inizio test ==========");

		Categoria categoriaDaRiempire = new Categoria("libro", "libro01");
		categoriaService.inserisciNuovo(categoriaDaRiempire);
		if (categoriaDaRiempire.getId() == null)
			throw new RuntimeException("categoria non inserita");

		Set<Categoria> listaCategorie = new HashSet<Categoria>();
		listaCategorie.add(categoriaDaRiempire);

		Ordine ordinePerArticolo = new Ordine("luca", "via amico", new Date());
		ordineService.inserisciNuovo(ordinePerArticolo);

		Articolo articoloDaInserireInOrdine = new Articolo("pupazzo", "pupazzo01", 10, new Date());
		articoloDaInserireInOrdine.setOrdine(ordinePerArticolo);
		articoloDaInserireInOrdine.setCategorie(listaCategorie);

		articoloService.inserisciNuovo(articoloDaInserireInOrdine);
		if (articoloDaInserireInOrdine.getId() == null)
			throw new RuntimeException("articolo non inserito");

		categoriaService.aggiungiArticolo(categoriaDaRiempire, articoloDaInserireInOrdine);

		if (categoriaService.caricaCategoriaConArticoli(categoriaDaRiempire).getArticoli().size() != 1)
			throw new RuntimeException("articolo non inserito nell'ordine");

		System.out.println("========== test eseguito con successo ==========");
	}

	private static void testInsertCategoriaAdArticolo(CategoriaService categoriaService,
			ArticoloService articoloService, OrdineService ordineService) throws Exception {

		System.out.println("========== Inizio test ==========");

		Categoria categoria = new Categoria("cibo", "cibo07");
		categoriaService.inserisciNuovo(categoria);
		if (categoria.getId() == null)
			throw new RuntimeException("categoria non inserita");

		Set<Categoria> listaCategorie = new HashSet<Categoria>();
		listaCategorie.add(categoria);

		Ordine ordinePerArticolo = new Ordine("luca", "via amico", new Date());
		ordineService.inserisciNuovo(ordinePerArticolo);

		Articolo articoloDaCategorizzare = new Articolo("pupazzo", "pupazzo01", 10, new Date());
		articoloDaCategorizzare.setOrdine(ordinePerArticolo);
		articoloDaCategorizzare.setCategorie(listaCategorie);

		articoloService.inserisciNuovo(articoloDaCategorizzare);
		if (articoloDaCategorizzare.getId() == null)
			throw new RuntimeException("articolo non inserito");

		articoloService.aggiungiCategoria(categoria, articoloDaCategorizzare);

		if (articoloService.caricaArticoloConCategoria(articoloDaCategorizzare).getCategorie().size() != 1)
			throw new RuntimeException("articolo non inserito nell'ordine");

		System.out.println("========== test eseguito con successo ==========");
	}

	private static void testUpdateOrdine(OrdineService ordineService) throws Exception {
		System.out.println("========== Inizio test ==========");

		Ordine ordinePerUpdate = new Ordine("franco", "via franchi", new Date());
		ordineService.inserisciNuovo(ordinePerUpdate);
		if (ordinePerUpdate.getId() == null)
			throw new RuntimeException("ordine non inserito");

		ordinePerUpdate.setNomeDestinatario("franchino");

		ordineService.aggiorna(ordinePerUpdate);
		if (!ordinePerUpdate.getNomeDestinatario().equals("franchino"))
			throw new RuntimeException("update fallito");

		System.out.println("========== test eseguito con successo ==========");
	}

	private static void testRemoveDiArticoloConCategoria(ArticoloService articoloService,
			CategoriaService categoriaService, OrdineService ordineService) throws Exception {
		System.out.println("========== Inizio test ==========");

		Categoria categoriaDaRimuovere = new Categoria("cose", "cose07");
		categoriaService.inserisciNuovo(categoriaDaRimuovere);
		if (categoriaDaRimuovere.getId() == null)
			throw new RuntimeException("categoria non inserita");

		Set<Categoria> listaCategorie = new HashSet<Categoria>();
		listaCategorie.add(categoriaDaRimuovere);

		Ordine ordinePerArticoloDaEliminare = new Ordine("test", "via test", new Date());
		ordineService.inserisciNuovo(ordinePerArticoloDaEliminare);

		Articolo articoloDaEliminare = new Articolo("pupazzo", "pupazzo01", 10, new Date());
		articoloDaEliminare.setOrdine(ordinePerArticoloDaEliminare);
		articoloDaEliminare.setCategorie(listaCategorie);

		articoloService.inserisciNuovo(articoloDaEliminare);
		if (articoloDaEliminare.getId() == null)
			throw new RuntimeException("articolo non inserito");

		articoloService.aggiungiCategoria(categoriaDaRimuovere, articoloDaEliminare);

		if (articoloService.caricaArticoloConCategoria(articoloDaEliminare).getCategorie().size() != 1)
			throw new RuntimeException("articolo non inserito nell'ordine");

		Set<Categoria> categorieDaSalvare = articoloDaEliminare.getCategorie();
		Ordine ordineDaSalvare = articoloDaEliminare.getOrdine();

		articoloService.rimuoviArticoloSenzaCampi(articoloDaEliminare);

		if (categorieDaSalvare == null || ordineDaSalvare == null)
			throw new RuntimeException("rimozione fallita");

		System.out.println("========== test eseguito con successo ==========");
	}

	private static void testCaricaOrdineEager(OrdineService ordineService, ArticoloService articoloService,
			CategoriaService categoriaService) throws Exception {
		System.out.println("========== Inizio test ==========");

		Ordine ordinePerRicercaEager = new Ordine("prova", "via prova", new Date());

		Set<Categoria> listaCategorie = new HashSet<Categoria>();
		Categoria categoriaPerFetch = new Categoria("vista", "vista01");
		listaCategorie.add(categoriaPerFetch);

		Set<Articolo> articoli = new HashSet<Articolo>();
		Articolo articoloPerFetchEager = new Articolo("occhiale", "occhiale01", 10, new Date());
		articoloPerFetchEager.setOrdine(ordinePerRicercaEager);
		articoloPerFetchEager.setCategorie(listaCategorie);
		articoli.add(articoloPerFetchEager);

		categoriaService.inserisciNuovo(categoriaPerFetch);
		if (categoriaPerFetch.getId() == null)
			throw new RuntimeException("errore di insert di categoria");

		ordineService.inserisciNuovo(ordinePerRicercaEager);
		if (ordinePerRicercaEager.getId() == null)
			throw new RuntimeException("insert di ordine eager fallita");

		articoloService.inserisciNuovo(articoloPerFetchEager);
		if (articoloPerFetchEager.getId() == null)
			throw new RuntimeException("errore di insert di articolo");

		ordinePerRicercaEager.setArticoli(articoli);

		ordineService.aggiorna(ordinePerRicercaEager);
		if (ordinePerRicercaEager.getArticoli().isEmpty())
			throw new RuntimeException("errore update ordine eager");

		Ordine ordineFetch = ordineService.caricaOrdineEager(ordinePerRicercaEager);
		if (ordineFetch.getArticoli().isEmpty())
			throw new RuntimeException("errore fetch eager");

		System.out.println("========== test eseguito con successo ==========");
	}

}
