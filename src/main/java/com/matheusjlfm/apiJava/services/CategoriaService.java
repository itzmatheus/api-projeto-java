package com.matheusjlfm.apiJava.services;

import java.util.List;
import java.util.Optional;

import com.matheusjlfm.apiJava.domain.Categoria;
import com.matheusjlfm.apiJava.services.exceptions.DataIntegrityException;
import com.matheusjlfm.apiJava.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.matheusjlfm.apiJava.dto.CategoriaDTO;
import com.matheusjlfm.apiJava.repositories.CategoriaRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;
	
	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	@Transactional
	public Categoria insert(Categoria obj) {
		obj.setId(null);
		return repo.save(obj);
	}
	
	public Categoria update(Categoria obj) {
		Categoria newObj = find(obj.getId());
		updateData(newObj, obj);
		
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		}catch(DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos");
		}
	}

	public List<Categoria> findAll(){
		return repo.findAll();
	}
	
	private void updateData(Categoria newObj, Categoria obj) {
		newObj.setNome(obj.getNome());
	}
	
	public Page<Categoria> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	public Categoria fromDto(CategoriaDTO objDto) {
		return new Categoria(objDto.getId(), objDto.getNome());
	}
}
