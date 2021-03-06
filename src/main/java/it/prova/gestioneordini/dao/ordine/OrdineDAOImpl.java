package it.prova.gestioneordini.dao.ordine;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordini.model.Categoria;
import it.prova.gestioneordini.model.Ordine;

public class OrdineDAOImpl implements OrdineDAO {

	private EntityManager entityManager;

	@Override
	public List<Ordine> list() throws Exception {
		return entityManager.createQuery("from Ordine", Ordine.class).getResultList();
	}

	@Override
	public Ordine get(Long id) throws Exception {
		return entityManager.find(Ordine.class, id);
	}

	@Override
	public void update(Ordine input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		input = entityManager.merge(input);
	}

	@Override
	public void insert(Ordine input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(input);
	}

	@Override
	public void delete(Ordine input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(input));
	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	public Ordine caricaOrdineConArticoli() throws Exception {
		return entityManager
				.createQuery("select o from Ordine o left join fetch o.articoli a where o.id = 35", Ordine.class)
				.getSingleResult();
	}

	@Override
	public Ordine caricaOrdineEager(Long id) throws Exception {
		TypedQuery<Ordine> query = entityManager.createQuery(
				"select o from Ordine o left join fetch o.articoli a left join fetch a.categorie c where o.id = :idOrdine ",
				Ordine.class).setParameter("idOrdine", id);
		return query.getSingleResult();
	}

	@Override
	public List<Ordine> dammiTuttiGliOrdiniDataLaCategoria(Categoria categoriaInput) throws Exception {
		
		TypedQuery<Ordine> query = entityManager.createQuery(
				"select distinct o from Ordine o left join o.articoli a left join a.categorie c where c.codice like :categoriaInput ",
				Ordine.class).setParameter("categoriaInput", categoriaInput.getCodice());
		return query.getResultList();
		
	}

	@Override
	public List<Ordine> dammiOrdineRecentePerCategoria(Categoria categoriaInput) throws Exception {
		
		TypedQuery<Ordine> query = entityManager.createQuery(
				"select o from Ordine o left join o.articoli a left join a.categorie c where c.codice = :codiceInput ",
				Ordine.class).setParameter("codiceInput", categoriaInput.getCodice());
		return query.getResultList();
	}

	@Override
	public List<String> dammiIndirizziDiOrdiniLike(String stringaInput) throws Exception {
		
		TypedQuery<String> query = entityManager.createQuery(
				"select distinct o.indirizzoSpedizione from Ordine o left join o.articoli a where a.numeroSeriale = :stringaInput ",
				String.class).setParameter("stringaInput", stringaInput);
		return query.getResultList();
		
	}

}
