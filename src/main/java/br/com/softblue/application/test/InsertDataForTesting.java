package br.com.softblue.application.test;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.softblue.bluefood.domain.cliente.Cliente;
import br.com.softblue.bluefood.domain.cliente.ClienteRepository;

import br.com.softblue.bluefood.domain.restaurante.CategoriaRestaurante;
import br.com.softblue.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import br.com.softblue.bluefood.domain.restaurante.ItemCardapio;
import br.com.softblue.bluefood.domain.restaurante.ItemCardapioRepository;
import br.com.softblue.bluefood.domain.restaurante.Restaurante;
import br.com.softblue.bluefood.domain.restaurante.RestauranteRepository;
import br.com.softblue.bluefood.util.StringUtils;

@Component
public class InsertDataForTesting {
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private RestauranteRepository restauranteRepository;

	@Autowired
	private CategoriaRestauranteRepository categoriaRestauranteRepository;
	
	@Autowired
	private ItemCardapioRepository itemCardapioRepository;


	@EventListener
	public void onApplicationEvent(ContextRefreshedEvent event) {
		cliente();
		Restaurante[] restaurantes = restaurante();
	}


	private Restaurante[] restaurante() {
		List<Restaurante> restaurantes = new ArrayList<>();
		
		CategoriaRestaurante categoriaPizza = categoriaRestauranteRepository.findById(1).orElseThrow();
		CategoriaRestaurante categoriaSanduiche = categoriaRestauranteRepository.findById(2).orElseThrow();
		CategoriaRestaurante categoriaSobremessa = categoriaRestauranteRepository.findById(5).orElseThrow();
		CategoriaRestaurante categoriaChurrasco = categoriaRestauranteRepository.findById(3).orElseThrow();
		
		Restaurante r = new Restaurante();
		r.setNome("BubgerKing");
		r.setEmail("r1@gmail.com");
		r.setSenha(StringUtils.encrypt("r"));
		r.setCnpj("00000000000001");
		r.setTaxaEntrega(BigDecimal.valueOf(3.2));
		r.setTelefone("0000000001");
		r.getCategorias().add(categoriaSanduiche);
		r.getCategorias().add(categoriaSobremessa);
		r.setLogotipo("0001-logo-png");
		r.setTempoEntregaBase(30);
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		r = new Restaurante();
		r.setNome("Mac naldos");
		r.setEmail("r2@gmail.com");
		r.setSenha(StringUtils.encrypt("r"));
		r.setCnpj("00000000000002");
		r.setTaxaEntrega(BigDecimal.valueOf(3.2));
		r.setTelefone("0000000002");
		r.getCategorias().add(categoriaPizza);
		r.getCategorias().add(categoriaChurrasco);
		r.setLogotipo("0002-logo-png");
		r.setTempoEntregaBase(30);
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		r = new Restaurante();
		r.setNome("Pizza Brhuu");
		r.setEmail("r3@gmail.com");
		r.setSenha(StringUtils.encrypt("r"));
		r.setCnpj("00000000000003");
		r.setTaxaEntrega(BigDecimal.valueOf(3.2));
		r.setTelefone("0000000003");
		r.getCategorias().add(categoriaSanduiche);
		r.getCategorias().add(categoriaPizza);
		r.setLogotipo("0003-logo-png");
		r.setTempoEntregaBase(30);
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		r = new Restaurante();
		r.setNome("Wiki japa");
		r.setEmail("r4@gmail.com");
		r.setSenha(StringUtils.encrypt("r"));
		r.setCnpj("00000000000004");
		r.setTaxaEntrega(BigDecimal.valueOf(3.2));
		r.setTelefone("0000000004");
		r.getCategorias().add(categoriaChurrasco);
		r.getCategorias().add(categoriaPizza);
		r.setLogotipo("0004-logo-png");
		r.setTempoEntregaBase(30);
		restauranteRepository.save(r);
		restaurantes.add(r);
		
		Restaurante[] array = new Restaurante[restaurantes.size()];
		return restaurantes.toArray(array);
		
	}


	private Cliente[] cliente() {
		
		List<Cliente> clientes = new ArrayList<>();
		
		Cliente c = new Cliente();
		c.setNome("João Silva");
		c.setEmail("joao@bluefood.com.br");
		c.setSenha(StringUtils.encrypt("c"));
		c.setCep("79965000");
		c.setCpf("00000000010");
		c.setTelefone("1234567891");
		clienteRepository.save(c);
		
		c = new Cliente();
		c.setNome("Maria Torres");
		c.setEmail("maria@bluefood.com.br");
		c.setSenha(StringUtils.encrypt("c"));
		c.setCep("79965000");
		c.setCpf("00000000010");
		c.setTelefone("1234567891");
		clienteRepository.save(c);
		
		Cliente[] array = new Cliente[clientes.size()];
		return clientes.toArray(array);
	}
	
	private void itensCardapio(Restaurante[] restaurantes) {
		ItemCardapio ic = new ItemCardapio();
		ic.setCategoria("Sanduíche");
		ic.setDescricao("Delicioso sanduíche com molho");
		ic.setNome("Double Cheese Burger Special");
		ic.setPreco(BigDecimal.valueOf(23.8));
		ic.setRestaurante(restaurantes[0]);
		ic.setDestaque(true);
		ic.setImagem("0001-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Sanduíche");
		ic.setDescricao("Sanduíche padrão que mata a fome");
		ic.setNome("Cheese Burger Simples");
		ic.setPreco(BigDecimal.valueOf(17.8));
		ic.setRestaurante(restaurantes[0]);
		ic.setDestaque(false);
		ic.setImagem("0006-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Sanduíche");
		ic.setDescricao("Sanduíche natural com peito de peru");
		ic.setNome("Sanduíche Natural da Casa");
		ic.setPreco(BigDecimal.valueOf(11.8));
		ic.setRestaurante(restaurantes[0]);
		ic.setDestaque(false);
		ic.setImagem("0007-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Bebida");
		ic.setDescricao("Refrigerante com gás");
		ic.setNome("Refrigerante Tradicional");
		ic.setPreco(BigDecimal.valueOf(9));
		ic.setRestaurante(restaurantes[0]);
		ic.setDestaque(false);
		ic.setImagem("0004-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Bebida");
		ic.setDescricao("Suco natural e docinho");
		ic.setNome("Suco de Laranja");
		ic.setPreco(BigDecimal.valueOf(9));
		ic.setRestaurante(restaurantes[0]);
		ic.setDestaque(false);
		ic.setImagem("0005-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Pizza");
		ic.setDescricao("Pizza saborosa com cebola");
		ic.setNome("Pizza de Calabresa");
		ic.setPreco(BigDecimal.valueOf(38.9));
		ic.setRestaurante(restaurantes[3]);
		ic.setDestaque(false);
		ic.setImagem("0002-comida.png");
		itemCardapioRepository.save(ic);
		
		ic = new ItemCardapio();
		ic.setCategoria("Japonesa");
		ic.setDescricao("Delicioso Uramaki tradicional");
		ic.setNome("Uramaki");
		ic.setPreco(BigDecimal.valueOf(16.8));
		ic.setRestaurante(restaurantes[4]);
		ic.setDestaque(false);
		ic.setImagem("0003-comida.png");
		itemCardapioRepository.save(ic);
	}
}
	
