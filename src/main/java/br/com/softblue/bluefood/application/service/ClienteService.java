package br.com.softblue.bluefood.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.softblue.bluefood.domain.cliente.Cliente;
import br.com.softblue.bluefood.domain.cliente.ClienteRepository;
import br.com.softblue.bluefood.domain.restaurante.Restaurante;
import br.com.softblue.bluefood.domain.restaurante.RestauranteRepository;

@Service
public class ClienteService {
	
	@Autowired//automatiza a criacao do repository
	private ClienteRepository clienteRepository;
	
	@Autowired
	private RestauranteRepository restauranteRepository;
	
	
	
	@Transactional
	public void saveCliente(Cliente cliente) throws ValidationException {
		if(!validateEmail(cliente.getEmail(), cliente.getId())) {
			throw new ValidationException("Este e-mail já está cadastrado no sistema!");
			
		}
		
		if(cliente.getId() != null) {//alterando
			Cliente clienteDB = clienteRepository.findById(cliente.getId()).orElseThrow();
			cliente.setSenha(clienteDB.getSenha());
		}else {//craindo
			cliente.encryptPassword();
			
		}
	
		clienteRepository.save(cliente);
		
	}
	
	private boolean validateEmail(String email, Integer id) {
		
		Restaurante restaurante = restauranteRepository.findByEmail(email);
		Cliente cliente = clienteRepository.findByEmail(email);
		
		if(restaurante != null) {
			return false;
		}
		if (cliente != null) {
			if (id == null) {
				return false;
			}
			
			if(!cliente.getId().equals(id)) {
				return false;
			}
		}
		
		return true;
	}

}
