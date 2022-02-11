package br.com.akira.exerciciossb.controller;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import br.com.akira.exerciciossb.models.entities.Produto;
import br.com.akira.exerciciossb.models.repositories.ProdutoRepository;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {

	// injeção de dependencia, o spring sera obrigado a criar um objeto deste tipo indicado
	@Autowired
	private ProdutoRepository produtoRepository;

	// post mapping para retornar sempre que for feita uma requisição de post
	// Anotacao responsebody não é necessaria, apenas é para identificacao
	// Como o @PostMapping e PutMapping estão com corpos identicos, podemos utilizar
	// o REQUEST MAPPING
	@RequestMapping(method = { RequestMethod.POST, RequestMethod.PUT })
	public @ResponseBody Produto novoProduto(@Valid Produto produto) {
		produtoRepository.save(produto);
		return produto;

	}

	@GetMapping
	public Iterable<Produto> obterProdutos() {
		return produtoRepository.findAll();
	}
	
	@GetMapping (path = "/nome/{parteNome}")
	public Iterable<Produto> obterProdutosPorNome(@PathVariable String parteNome){
		//return produtoRepository.findByNomeContainingIgnoreCase(parteNome); forma simples utilizando convenção do SpringBoot
		return produtoRepository.searchByNameLike(parteNome);
	}
	

	
	@GetMapping(path ="/pagina/{numPagina}&{qtde}")
	public Iterable<Produto> obterProdutosPorPagina(@PathVariable int numPagina, @PathVariable int qtde){

		if(qtde > 5) qtde = 5;
		
		Pageable page = PageRequest.of(numPagina, qtde);
		
		return produtoRepository.findAll(page);
	}

	// Quando for passado o ID para a url padrão, caíra no método abaixo
	@GetMapping(path = "/{id}")
	public Optional<Produto> obterProdutoPorId(@PathVariable int id) {
		return produtoRepository.findById(id);
	}
	
	
	
	//NOTA: recomendavel conceito de SOFT DELETE em bancos com muitos relacionamentos
	@DeleteMapping(path="/{id}")
	public void excluirProduto(@PathVariable int id) {
		produtoRepository.deleteById(id);
	}
	
	
	

	/*
	 * Método longo, colocando todos os parametros na criação do Spring:
	 * 
	 * @Valid para linkar com as validações da classe Produto public @ResponseBody
	 * Produto novoProduto(
	 * 
	 * @RequestParam String nome,
	 * 
	 * @RequestParam double preco,
	 * 
	 * @RequestParam double desconto) { Produto produto = new Produto(nome, preco,
	 * desconto); produtoRepository.save(produto); return produto;
	 * 
	 * PUT altera o produto inteiro e PATCH altera parcialmente
	 * 
	 * @PutMapping public Produto alterarProduto(@Valid Produto produto) {
	 * produtoRepository.save(produto); return produto; }
	 */

}
