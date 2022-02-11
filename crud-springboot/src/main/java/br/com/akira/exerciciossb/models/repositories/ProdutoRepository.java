package br.com.akira.exerciciossb.models.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.com.akira.exerciciossb.models.entities.Produto;

public interface ProdutoRepository
	extends PagingAndSortingRepository<Produto, Integer> {
	
	//find, Containing e IgnoreCase são convenções dentro do próprio SpringBoot (cf. documentação do SpringBoot)
	public Iterable<Produto> findByNomeContainingIgnoreCase(String parteNome);
	
	/* Exemplos de convenções:
	 * findByNomeContaining, findByNomeIsContaining, findByNomeContains
	 * 
	 * findByNomeStartsWith
	 * findByNomeEndsWith
	 * 
	 * findByNomeNotContaining
	 */

	
	
	//Utilizando queries com mais flexibilidade
	@Query("SELECT p FROM Produto p WHERE p.nome LIKE %:nome%")
	public Iterable<Produto> searchByNameLike(@Param("nome") String nome);
	
	
	//@Query("SELECT p FROM Produto p WHERE p.id IS id")
	//public Iterable<Produto> searchByIdLike(@Param("id") int id);
	
	
}
