package com.pires.curso;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.pires.curso.domain.Categoria;
import com.pires.curso.domain.Cidade;
import com.pires.curso.domain.Cliente;
import com.pires.curso.domain.Endereco;
import com.pires.curso.domain.Estado;
import com.pires.curso.domain.ItemPedido;
import com.pires.curso.domain.Pagamento;
import com.pires.curso.domain.PagamentoComBoleto;
import com.pires.curso.domain.PagamentoComCartao;
import com.pires.curso.domain.Pedido;
import com.pires.curso.domain.Produto;
import com.pires.curso.domain.enums.EstadoPagamento;
import com.pires.curso.domain.enums.TipoCliente;
import com.pires.curso.repositories.CategoriaRepository;
import com.pires.curso.repositories.CidadeRepository;
import com.pires.curso.repositories.ClienteRepository;
import com.pires.curso.repositories.EnderecoRepository;
import com.pires.curso.repositories.EstadoRepository;
import com.pires.curso.repositories.ItemPedidoRepository;
import com.pires.curso.repositories.PagamentoRepository;
import com.pires.curso.repositories.PedidoRepository;
import com.pires.curso.repositories.ProdutoRepository;

@SpringBootApplication
public class CursoApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository categoriaRepository; 
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	
	public static void main(String[] args) {
		SpringApplication.run(CursoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);
		
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));
		
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));
		
		Estado est1 = new Estado(null, "Minas gerais");
		Estado est2 = new Estado(null, "São paulo");
		
		Cidade cid1 = new Cidade(null, "Uberlandia", est1);
		Cidade cid2 = new Cidade(null, "São paulo", est2);
		Cidade cid3 = new Cidade(null, "Campinas", est2);
		
		est1.getCidades().addAll(Arrays.asList(cid1));
		est2.getCidades().addAll(Arrays.asList(cid2, cid3));		
				
		estadoRepository.saveAll(Arrays.asList(est1, est2));
		cidadeRepository.saveAll(Arrays.asList(cid1, cid2, cid3));
		
		Cliente cli1 = new Cliente(null, "Maria silva", "maria@gmil.com", "363789912377", TipoCliente.PESSOAFISICA);
		
		cli1.getTelefones().addAll(Arrays.asList("27363323", "93838993"));
		
		Endereco end1 = new Endereco(null, "Rua flores", "300", "Apto 303", "Jardim", "38220834", cli1, cid1);
		Endereco end2 = new Endereco(null, "Avenida mattos", "105", "Sala 800", "Centro", "38777012", cli1, cid2);
		
		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));
		
		clienteRepository.saveAll(Arrays.asList(cli1));		
		enderecoRepository.saveAll(Arrays.asList(end1, end2));	
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:MM");
		
		Pedido ped1 = new Pedido(null, sdf.parse("21/06/2018 01:08"), cli1, end1);
		Pedido ped2 = new Pedido(null, sdf.parse("15/01/2017 21:34"), cli1, end2);
		
		Pagamento pgto1 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, ped1, 6);
		ped1.setPagamento(pgto1);
		Pagamento pgto2 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE, ped2, sdf.parse("01/02/2015 01:00"), null);
		ped2.setPagamento(pgto2);
		cli1.getPedidos().addAll(Arrays.asList(ped1, ped2));
		
		pedidoRepository.saveAll(Arrays.asList(ped1, ped2));
		pagamentoRepository.saveAll(Arrays.asList(pgto1, pgto2));
		
		ItemPedido ip1 = new ItemPedido(ped1, p1, 0.00, 1, 2000.00);
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
