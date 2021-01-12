package br.com.softblue.bluefood.domain.restaurante;

import java.util.Comparator;

import br.com.softblue.bluefood.domain.restaurante.SearchFilter.Order;

public class RestauranteComparator implements Comparator<Restaurante> {
	
	private SearchFilter filter;
	private String cep;

	public RestauranteComparator(SearchFilter filter, String cep) {
		
		this.filter = filter;
		this.cep = cep;
	}

	@Override
	public int compare(Restaurante r1, Restaurante r2) {
		int result;
		
		if(filter.getOrder() == Order.Taxa) {
			result = r1.getTaxaEntrega().compareTo(r2.getTaxaEntrega());
		}else if(filter.getOrder() == Order.Tempo) {
			result = r1.calcularTempoEntrega(cep).compareTo(r2.calcularTempoEntrega(cep));//compare to retorna 1, 0 ou -1
		}else {
			throw new IllegalStateException("O valor de ordenação" + filter.getOrder() +"não é valido" );
		}
		return filter.isAsc() ? result : -result;
		
	}

}
