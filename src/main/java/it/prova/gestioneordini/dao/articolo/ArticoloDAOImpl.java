package it.prova.gestioneordini.dao.articolo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordini.model.Articolo;
import it.prova.gestioneordini.model.Categoria;

public class ArticoloDAOImpl implements ArticoloDAO {

	private EntityManager entityManager;

	@Override
	public List<Articolo> list() throws Exception {
		return entityManager.createQuery("from Articolo", Articolo.class).getResultList();
	}

	@Override
	public Articolo get(Long id) throws Exception {
		return entityManager.find(Articolo.class, id);
	}

	@Override
	public void update(Articolo input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		input = entityManager.merge(input);
	}

	@Override
	public void insert(Articolo input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(input);
	}

	@Override
	public void delete(Articolo input) throws Exception {
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
	public Articolo caricaArticoloConCategoria() throws Exception {
		return entityManager
				.createQuery("select a from Articolo a left join fetch a.categorie c where a.id = 26", Articolo.class)
				.getSingleResult();
	}

	@Override
	public void rimuoviArticoloSenzaAltriCampi(Articolo input) throws Exception {
		entityManager.createQuery("select a from Articolo a where a.id = :id", Articolo.class);
		entityManager.setProperty("id", input.getId());
	}

	@Override
	public Long dammiIlTotaleDelCostoDiArticoliConCategoria(Categoria categoriaInput) throws Exception {

		TypedQuery<Long> query = entityManager.createQuery(
				"select sum(a.prezzoSingolo) from Articolo a join a.categorie c where c.codice like :codiceCategoria",
				Long.class).setParameter("codiceCategoria", categoriaInput.getCodice());
		return (long) query.getSingleResult();

	}

}
