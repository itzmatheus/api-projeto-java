package com.matheusjlfm.apiJava.services;

import com.matheusjlfm.apiJava.domain.Categoria;
import com.matheusjlfm.apiJava.domain.Produto;
import com.matheusjlfm.apiJava.repositories.CategoriaRepository;
import com.matheusjlfm.apiJava.repositories.ProdutoRepository;
import com.matheusjlfm.apiJava.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: "+ id + ", Tipo: " + Produto.class.getName()));
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		List<Categoria> categorias =  categoriaRepository.findAllById(ids);
		return repo.search(nome, categorias, pageRequest);
	}
	
}
