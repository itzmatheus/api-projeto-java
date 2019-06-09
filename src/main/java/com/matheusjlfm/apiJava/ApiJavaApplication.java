package com.matheusjlfm.apiJava;

import com.matheusjlfm.apiJava.domain.*;
import com.matheusjlfm.apiJava.domain.enums.EstadoPagamento;
import com.matheusjlfm.apiJava.domain.enums.TipoCliente;
import com.matheusjlfm.apiJava.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.SimpleDateFormat;
import java.util.Arrays;

@SpringBootApplication
public class ApiJavaApplication implements CommandLineRunner {

    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    public static void main(String[] args) {
        SpringApplication.run(ApiJavaApplication.class, args);
    }

    @Autowired
    private EstadoRepository estadoRepository;
    @Autowired
    private CidadeRepository cidadeRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private EnderecoRepository enderecoRepository;
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private ItemPedidoRepository itemPedidoRepository;

    @Override
    public void run(String... args) throws Exception {

        Categoria cat1 = new Categoria(null, "Informática");
        Categoria cat2 = new Categoria(null, "Escritório");
        Categoria cat3 = new Categoria(null, "Cama");
        Categoria cat4 = new Categoria(null, "Mesa");
        Categoria cat5 = new Categoria(null, "Banho");
        Categoria cat6 = new Categoria(null, "Eletrônicos");
        Categoria cat7 = new Categoria(null, "Smartphones");


        Produto p1 = new Produto(null, "Computador", 3000.00);
        Produto p2 = new Produto(null, "Impressora", 800.00);
        Produto p3 = new Produto(null, "Mouse", 80.00);
        Produto p4 = new Produto(null, "Xiaomi MI 8 Lite", 1200.00);
        Produto p5 = new Produto(null, "Xiaomi MI 9 SE", 1900.00);
        Produto p6 = new Produto(null, "Dell Inspiron 15 3000", 3199.00);

        cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3, p6));
        cat2.getProdutos().addAll(Arrays.asList(p2));
        cat7.getProdutos().addAll(Arrays.asList(p4,p5));


        p1.getCategorias().addAll(Arrays.asList(cat1));
        p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
        p3.getCategorias().addAll(Arrays.asList(cat1));
        p4.getCategorias().addAll(Arrays.asList(cat7));
        p5.getCategorias().addAll(Arrays.asList(cat7));
        p6.getCategorias().addAll(Arrays.asList(cat1));

        categoriaRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5,
                cat6, cat7));
        produtoRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6));

        Estado est1 = new Estado(null, "Paraíba");
        Estado est2 = new Estado(null, "São Paulo");

        Cidade c1 = new Cidade(null, "João Pessoa", est1);
        Cidade c2 = new Cidade(null, "São Paulo", est2);
        Cidade c3 = new Cidade(null, "Piancó", est1);

        est1.getCidades().addAll(Arrays.asList(c1, c3));
        est2.getCidades().addAll(Arrays.asList(c2));

        estadoRepository.saveAll(Arrays.asList(est1, est2));
        cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));

        Cliente cli1 = new Cliente(null, "Maria Silva", "mariasilva@email.com", "12193387814", TipoCliente.PESSOAFISICA);

        cli1.getTelefones().addAll(Arrays.asList("8733345121", "0989879929"));

        Endereco e1 = new Endereco(null, "Rua Maria Eduarda de C. Moura e Silva", "300", "Casa", "Jardim Cid. Universitária", "58051640", cli1, c1);
        Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 308", "Centro", "38777012", cli1, c2);

        cli1.getEnderecos().addAll(Arrays.asList(e1, e2));

        clienteRepository.saveAll(Arrays.asList(cli1));
        enderecoRepository.saveAll(Arrays.asList(e1, e2));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Pedido ped1 = new Pedido(null, sdf.parse("30/09/2017 10:32"), cli1, e1);
        Pedido ped2 = new Pedido(null, sdf.parse("10/10/2017 19:35"), cli1, e2);

        Pagamento pagto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
        ped1.setPagamento(pagto1);

        Pagamento pagto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("20/10/2017 00:00"), null);
        ped2.setPagamento(pagto2);

        cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));

        pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
        pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));

        ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 3000.00);
        ItemPedido ip2 = new ItemPedido(ped1, p3, 0.00, 2, 80.00);
        ItemPedido ip3 = new ItemPedido(ped2, p2, 100.00, 1, 800.00);

        ped1.getItens().addAll(Arrays.asList(ip1, ip2));
        ped2.getItens().addAll(Arrays.asList(ip3));

        p1.getItens().addAll(Arrays.asList(ip1));
        p2.getItens().addAll(Arrays.asList(ip3));
        p3.getItens().addAll(Arrays.asList(ip2));

        itemPedidoRepository.saveAll(Arrays.asList(ip1, ip2, ip3));

    }


}

