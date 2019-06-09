package com.matheusjlfm.apiJava.services;

import com.matheusjlfm.apiJava.domain.Categoria;
import com.matheusjlfm.apiJava.domain.Produto;
import com.matheusjlfm.apiJava.dto.ProdutoDTO;
import com.matheusjlfm.apiJava.repositories.CategoriaRepository;
import com.matheusjlfm.apiJava.repositories.ProdutoRepository;
import com.matheusjlfm.apiJava.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;

	@Autowired
	private CategoriaRepository categoriaRepository;

	public Produto update(Produto obj) {
		Produto newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	@Transactional
	public Produto insert(Produto obj) {
		obj.setId(null);
		return repo.save(obj);
	}

	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: "+ id + ", Tipo: " + Produto.class.getName()));
	}

	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
		List<Categoria> categorias =  categoriaRepository.findAllById(ids);
		return repo.search(nome, categorias, pageRequest);
	}

	public Produto fromDto(ProdutoDTO objDto) {
		return new Produto(objDto.getId(), objDto.getNome(), objDto.getPreco());
	}

	private void updateData(Produto newObj, Produto obj) {
		newObj.setNome(obj.getNome());
		newObj.setPreco(obj.getPreco());
		newObj.setCategorias(obj.getCategorias());
	}

}
