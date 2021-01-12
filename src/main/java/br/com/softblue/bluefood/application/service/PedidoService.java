package br.com.softblue.bluefood.application.service;


import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import br.com.softblue.bluefood.domain.pagamento.DadosCartao;
import br.com.softblue.bluefood.domain.pagamento.Pagamento;
import br.com.softblue.bluefood.domain.pagamento.PagamentoRepository;
import br.com.softblue.bluefood.domain.pagamento.StatusPagamento;
import br.com.softblue.bluefood.domain.pedido.Carrinho;
import br.com.softblue.bluefood.domain.pedido.ItemPedido;
import br.com.softblue.bluefood.domain.pedido.ItemPedidoPK;
import br.com.softblue.bluefood.domain.pedido.ItemPedidoRepository;
import br.com.softblue.bluefood.domain.pedido.Pedido;
import br.com.softblue.bluefood.domain.pedido.PedidoRepository;
import br.com.softblue.bluefood.domain.pedido.Pedido.Status;
import br.com.softblue.bluefood.util.SecurityUtils;


@Service
public class PedidoService {
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Value("${bluefood.sbpay.url}")
	private String sbPayUrl;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Value("${bluefood.sbpay.token}")
	private String sbPayToken;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@SuppressWarnings("unchecked")
	@Transactional(rollbackFor = PagamentoException.class)// caso aconteça uma exeption acontece rollback
	public Pedido criarEPagar(Carrinho carrinho, String numCartao ) throws PagamentoException {
		Pedido pedido = new Pedido();
		pedido.setData(LocalDateTime.now());
		pedido.setCliente(SecurityUtils.loggedCliente());
		pedido.setRestaurante(carrinho.getRestaurante());
		pedido.setStatus(Status.Producao);
		pedido.setTaxaEntrega(carrinho.getRestaurante().getTaxaEntrega());
		pedido.setSubtotal(carrinho.getPrecoTotal(false));
		pedido.setTotal(carrinho.getPrecoTotal(true));
		
		pedidoRepository.save(pedido);
		
		int ordem = 1;
		
		for (ItemPedido itemPedido : carrinho.getItens()) {
			itemPedido.setId(new ItemPedidoPK(pedido, ordem++));
			itemPedidoRepository.save(itemPedido);
		}
		
		DadosCartao dadosCartao = new DadosCartao();
		dadosCartao.setNumCartao(numCartao);
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Token", sbPayToken);
		
		HttpEntity<DadosCartao> requestEntity = new HttpEntity<>(dadosCartao, headers);
		
		RestTemplate restTemplate = new RestTemplate();
		Map<String, String> response;//volta os dados de estado do pagamento e erro
		try {
			 response = restTemplate.postForObject(sbPayUrl, requestEntity, Map.class);
		}catch(Exception e) {
			throw new PagamentoException("Erro no servidor de pagamento");
		}
			StatusPagamento statusPagamento = StatusPagamento.valueOf(response.get("status"));
			
			if(statusPagamento != StatusPagamento.Autorizado) {
				throw new PagamentoException(statusPagamento.getDescricao());
				
			}
		
		
			Pagamento pagamento = new Pagamento();
			pagamento.setData(LocalDateTime.now());
			pagamento.setPedido(pedido);
			pagamento.definirNumeroEBandeira(numCartao);
			pagamentoRepository.save(pagamento);
			
		return pedido;
	}
	
	

}
