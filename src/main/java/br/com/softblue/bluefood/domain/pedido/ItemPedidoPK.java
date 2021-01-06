package br.com.softblue.bluefood.domain.pedido;

import java.io.Serializable;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@SuppressWarnings("serial")
public class ItemPedidoPK implements Serializable{

	
	@NotNull
	@ManyToOne
	private Pedido pedido;
	
	@NotNull
	private Integer ordem;
}
