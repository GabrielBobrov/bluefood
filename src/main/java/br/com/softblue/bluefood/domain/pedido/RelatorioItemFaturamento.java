package br.com.softblue.bluefood.domain.pedido;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class RelatorioItemFaturamento {
	
	private String nome;
	
	private Long quantidade;
	
	private BigDecimal valor;

}
