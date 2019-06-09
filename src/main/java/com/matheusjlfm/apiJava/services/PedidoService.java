package com.matheusjlfm.apiJava.services;

import java.util.Optional;

import com.matheusjlfm.apiJava.domain.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matheusjlfm.apiJava.repositories.PedidoRepository;
import com.matheusjlfm.apiJava.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: "+ id + ", Tipo: " + Pedido.class.getName()));
	}
	
}
