package it.prova.gestioneordini.dao.categoria;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import it.prova.gestioneordini.model.Categoria;

public class CategoriaDAOImpl implements CategoriaDAO {

	private EntityManager entityManager;

	@Override
	public List<Categoria> list() throws Exception {
		return entityManager.createQuery("from Categoria", Categoria.class).getResultList();
	}

	@Override
	public Categoria get(Long id) throws Exception {
		return entityManager.find(Categoria.class, id);
	}

	@Override
	public void update(Categoria input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		input = entityManager.merge(input);
	}

	@Override
	public void insert(Categoria input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(input);
	}

	@Override
	public void delete(Categoria input) throws Exception {
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
	public Categoria caricaCategoriaConArticoli() throws Exception {
		return entityManager
				.createQuery("select c from Categoria c left join fetch c.articoli a where c.id = 28", Categoria.class)
				.getSingleResult();
	}

	@Override
	public List<Categoria> dammiCategorieDiArticoliInOrdine(Long idOrdine) throws Exception {

		TypedQuery<Categoria> query = entityManager.createQuery(
				"select distinct c from Categoria c left join c.articoli a left join a.ordine o where o.id = :idOrdine ",
				Categoria.class).setParameter("idOrdine", idOrdine);
		return query.getResultList();

	}

	@Override
	public List<Categoria> dammiTutteLeCategorieOrdinateInQuestoMese(String codiceInput) throws Exception {

		TypedQuery<Categoria> query = entityManager.createQuery(
				"select distinct c from Categoria c left join c.articoli a left join a.ordine o where c.codice = :codiceInput and o.dataSpedizione > '2022-01-31' and o.dataSpedizione < '2022-03-01'",
				Categoria.class).setParameter("codiceInput", codiceInput);
		return query.getResultList();

	}

}
