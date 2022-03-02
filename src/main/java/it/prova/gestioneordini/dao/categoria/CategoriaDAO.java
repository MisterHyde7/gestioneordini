package it.prova.gestioneordini.dao.categoria;

import java.util.List;

import it.prova.gestioneordini.dao.IBaseDAO;
import it.prova.gestioneordini.model.Categoria;

public interface CategoriaDAO extends IBaseDAO<Categoria> {
	
	public Categoria caricaCategoriaConArticoli() throws Exception;
	
	public List<Categoria> dammiCategorieDiArticoliInOrdine(Long idOrdine) throws Exception;
	
	public List<Categoria> dammiTutteLeCategorieOrdinateInQuestoMese(String codiceInput) throws Exception;

}
