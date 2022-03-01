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

			testRemoveDiArticolo(articoloServiceInstance, ordineServiceInstance, categoriaServiceInstance);

			testDissociaOrdine(ordineServiceInstance, articoloServiceInstance);

		} catch (Throwable e) {

			e.printStackTrace();

		} finally {

			EntityManagerUtil.shutdown();
		}

	}

	private static void testInsertOrdine(OrdineService ordineService) throws Exception {
		System.out.println("========== Inizio test ==========");

		// Creo ordine
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

		// Creo categoria
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

		// Creo ordine
		Ordine ordineDaRiempire = new Ordine("carlo", "via carlo", new Date());
		ordineService.inserisciNuovo(ordineDaRiempire);
		if (ordineDaRiempire.getId() == null)
			throw new RuntimeException("ordine non inserito");

		// Creo articolo
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

		// Creo categoria
		Categoria categoriaDaRiempire = new Categoria("libro", "libro01");
		categoriaService.inserisciNuovo(categoriaDaRiempire);
		if (categoriaDaRiempire.getId() == null)
			throw new RuntimeException("categoria non inserita");

		Set<Categoria> listaCategorie = new HashSet<Categoria>();
		listaCategorie.add(categoriaDaRiempire);

		// Creo ordine
		Ordine ordinePerArticolo = new Ordine("luca", "via amico", new Date());
		ordineService.inserisciNuovo(ordinePerArticolo);

		// Creo articolo
		Articolo articoloDaInserireInOrdine = new Articolo("pupazzo", "pupazzo01", 10, new Date());
		articoloDaInserireInOrdine.setOrdine(ordinePerArticolo);
		articoloDaInserireInOrdine.setCategorie(listaCategorie);

		articoloService.inserisciNuovo(articoloDaInserireInOrdine);
		if (articoloDaInserireInOrdine.getId() == null)
			throw new RuntimeException("articolo non inserito");

		// Inserisco l'articolo alla categoria
		categoriaService.aggiungiArticolo(categoriaDaRiempire, articoloDaInserireInOrdine);

		if (categoriaService.caricaCategoriaConArticoli(categoriaDaRiempire).getArticoli().size() != 1)
			throw new RuntimeException("articolo non inserito nell'ordine");

		System.out.println("========== test eseguito con successo ==========");
	}

	private static void testInsertCategoriaAdArticolo(CategoriaService categoriaService,
			ArticoloService articoloService, OrdineService ordineService) throws Exception {

		System.out.println("========== Inizio test ==========");

		// Creo categoria
		Categoria categoria = new Categoria("cibo", "cibo07");
		categoriaService.inserisciNuovo(categoria);
		if (categoria.getId() == null)
			throw new RuntimeException("categoria non inserita");

		Set<Categoria> listaCategorie = new HashSet<Categoria>();
		listaCategorie.add(categoria);

		// Creo ordine
		Ordine ordinePerArticolo = new Ordine("luca", "via amico", new Date());
		ordineService.inserisciNuovo(ordinePerArticolo);

		// Creo articolo
		Articolo articoloDaCategorizzare = new Articolo("pupazzo", "pupazzo01", 10, new Date());
		articoloDaCategorizzare.setOrdine(ordinePerArticolo);
		articoloDaCategorizzare.setCategorie(listaCategorie);

		articoloService.inserisciNuovo(articoloDaCategorizzare);
		if (articoloDaCategorizzare.getId() == null)
			throw new RuntimeException("articolo non inserito");

		// Aggiungo la categoria all'articolo
		articoloService.aggiungiCategoria(categoria, articoloDaCategorizzare);

		if (articoloService.caricaArticoloConCategoria(articoloDaCategorizzare).getCategorie().size() != 1)
			throw new RuntimeException("articolo non inserito nell'ordine");

		System.out.println("========== test eseguito con successo ==========");
	}

	private static void testUpdateOrdine(OrdineService ordineService) throws Exception {
		System.out.println("========== Inizio test ==========");

		// Creo ordine
		Ordine ordinePerUpdate = new Ordine("franco", "via franchi", new Date());
		ordineService.inserisciNuovo(ordinePerUpdate);
		if (ordinePerUpdate.getId() == null)
			throw new RuntimeException("ordine non inserito");

		ordinePerUpdate.setNomeDestinatario("franchino");

		// Aggiorno i dati dell'ordine
		ordineService.aggiorna(ordinePerUpdate);
		if (!ordinePerUpdate.getNomeDestinatario().equals("franchino"))
			throw new RuntimeException("update fallito");

		System.out.println("========== test eseguito con successo ==========");
	}

	private static void testRemoveDiArticoloConCategoria(ArticoloService articoloService,
			CategoriaService categoriaService, OrdineService ordineService) throws Exception {
		System.out.println("========== Inizio test ==========");

		// Creo categoria
		Categoria categoriaDaRimuovere = new Categoria("cose", "cose07");
		categoriaService.inserisciNuovo(categoriaDaRimuovere);
		if (categoriaDaRimuovere.getId() == null)
			throw new RuntimeException("categoria non inserita");

		Set<Categoria> listaCategorie = new HashSet<Categoria>();
		listaCategorie.add(categoriaDaRimuovere);

		// Creo ordine
		Ordine ordinePerArticoloDaEliminare = new Ordine("test", "via test", new Date());
		ordineService.inserisciNuovo(ordinePerArticoloDaEliminare);

		// Creo articolo
		Articolo articoloDaEliminare = new Articolo("pupazzo", "pupazzo01", 10, new Date());
		articoloDaEliminare.setOrdine(ordinePerArticoloDaEliminare);
		articoloDaEliminare.setCategorie(listaCategorie);

		articoloService.inserisciNuovo(articoloDaEliminare);
		if (articoloDaEliminare.getId() == null)
			throw new RuntimeException("articolo non inserito");

		// Collego la categoria all'articolo
		articoloService.aggiungiCategoria(categoriaDaRimuovere, articoloDaEliminare);

		if (articoloService.caricaArticoloConCategoria(articoloDaEliminare).getCategorie().size() != 1)
			throw new RuntimeException("articolo non inserito nell'ordine");

		Set<Categoria> categorieDaSalvare = articoloDaEliminare.getCategorie();
		Ordine ordineDaSalvare = articoloDaEliminare.getOrdine();

		// Rimuovo l'articolo senza toccare gli altri campi
		articoloService.rimuoviArticoloSenzaCampi(articoloDaEliminare);

		if (categorieDaSalvare == null || ordineDaSalvare == null)
			throw new RuntimeException("rimozione fallita");

		System.out.println("========== test eseguito con successo ==========");
	}

	private static void testCaricaOrdineEager(OrdineService ordineService, ArticoloService articoloService,
			CategoriaService categoriaService) throws Exception {
		System.out.println("========== Inizio test ==========");

		// Creo ordine
		Ordine ordinePerRicercaEager = new Ordine("prova", "via prova", new Date());

		// Creo categoria
		Set<Categoria> listaCategorie = new HashSet<Categoria>();
		Categoria categoriaPerFetch = new Categoria("vista", "vista01");
		listaCategorie.add(categoriaPerFetch);

		// Creo articolo
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

		// Faccio la fetch di ordine completa di tutti i campi (EAGER)
		Ordine ordineFetch = ordineService.caricaOrdineEager(ordinePerRicercaEager);
		if (ordineFetch.getArticoli().isEmpty())
			throw new RuntimeException("errore fetch eager");

		System.out.println("========== test eseguito con successo ==========");
	}

	private static void testRemoveDiArticolo(ArticoloService articoloService, OrdineService ordineService,
			CategoriaService categoriaService) throws Exception {
		System.out.println("========== Inizio test ==========");

		// Creo ordine
		Ordine ordine = new Ordine();
		if (ordine.getId() != null)
			throw new RuntimeException("ordine gia presente su db");

		ordineService.inserisciNuovo(ordine);
		if (ordine.getId() == null)
			throw new RuntimeException("errore nell'inserimento dell'ordine");

		// Creo articolo
		Articolo articoloRemove = new Articolo("articolo", "123", 50, new Date());
		if (articoloRemove.getId() != null)
			throw new RuntimeException("articolo gia presente");

		articoloRemove.setOrdine(ordine);

		articoloService.inserisciNuovo(articoloRemove);
		if (articoloRemove.getId() == null)
			throw new RuntimeException("errore nell'inserimento dell'articolo");

		// Creo categoria
		Categoria categoriaAssociata = new Categoria("cosa", "cosa123");
		if (categoriaAssociata.getId() != null)
			throw new RuntimeException("categoria gia presente");

		categoriaService.inserisciNuovo(categoriaAssociata);
		if (categoriaAssociata.getId() == null)
			throw new RuntimeException("errore nell'inserimento della categoria");

		Set<Categoria> categorieArticolo = new HashSet<Categoria>();
		categorieArticolo.add(categoriaAssociata);

		articoloRemove.setCategorie(categorieArticolo);
		articoloService.aggiorna(articoloRemove);

		// Aggiungo categoria ad articolo
		articoloService.aggiungiCategoria(categoriaAssociata, articoloRemove);
		if (articoloRemove.getCategorie().isEmpty())
			throw new RuntimeException("errore nell'accoppiamento della categoria");

		// Dissocio articolo
		articoloService.dissociaArticoloDaiCampi(articoloRemove.getId(), categoriaAssociata.getId());

		System.out.println("========== test eseguito con successo ==========");
	}

	private static void testDissociaOrdine(OrdineService ordineService, ArticoloService articoloService)
			throws Exception {
		System.out.println("========== Inizio test ==========");

		// Creo ordine
		Ordine ordine = new Ordine();
		if (ordine.getId() != null)
			throw new RuntimeException("ordine gia presente su db");

		ordineService.inserisciNuovo(ordine);
		if (ordine.getId() == null)
			throw new RuntimeException("errore insert ordine");

		// Creo articolo
		Articolo articoloDaDissociare = new Articolo("record", "777", 500, new Date());
		if (articoloDaDissociare.getId() != null)
			throw new RuntimeException("articolo gia presente");

		articoloDaDissociare.setOrdine(ordine);

		articoloService.inserisciNuovo(articoloDaDissociare);
		if (articoloDaDissociare.getId() == null)
			throw new RuntimeException("errore insert articolo");

		//Dissocio ordine da articolo
		ordineService.dissociaOrdineDaArticolo(ordine.getId(), articoloDaDissociare.getId());

		System.out.println("========== test eseguito con successo ==========");
	}

}
