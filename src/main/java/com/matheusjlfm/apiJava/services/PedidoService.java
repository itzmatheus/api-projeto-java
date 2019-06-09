package com.matheusjlfm.apiJava.services;

import java.util.Date;
import java.util.Optional;

import com.matheusjlfm.apiJava.domain.*;
import com.matheusjlfm.apiJava.domain.enums.EstadoPagamento;
import com.matheusjlfm.apiJava.repositories.ItemPedidoRepository;
import com.matheusjlfm.apiJava.repositories.PagamentoRepository;
import com.matheusjlfm.apiJava.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matheusjlfm.apiJava.repositories.PedidoRepository;
import com.matheusjlfm.apiJava.services.exceptions.ObjectNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	@Autowired
	private PagamentoRepository pagamentoRepository;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: "+ id + ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert (Pedido obj){
		obj.setId(null);
		obj.setInstante(new Date());

		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);

		if (obj.getPagamento() instanceof PagamentoComBoleto){
			PagamentoComBoleto pgto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pgto, obj.getInstante());
		}

		obj = repo.save(obj);
		pagamentoRepository.save(obj.getPagamento());

		for (ItemPedido pd: obj.getItens()) {
			pd.setDesconto(0.0);
			pd.setProduto(produtoService.find(pd.getProduto().getId()));
			pd.setPreco(pd.getProduto().getPreco());
			pd.setPedido(obj);
		}

		itemPedidoRepository.saveAll(obj.getItens());

		return obj;
		
	}
}
