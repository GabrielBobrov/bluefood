package br.com.softblue.bluefood.service;


import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import br.com.softblue.bluefood.application.service.ClienteService;
import br.com.softblue.bluefood.application.service.ValidationException;
import br.com.softblue.bluefood.domain.cliente.Cliente;
import br.com.softblue.bluefood.domain.cliente.ClienteRepository;

@SpringBootTest
@ActiveProfiles("test")
public class ClienteServiceTest {
	
	@Autowired
	private ClienteService clienteService;
	
	@MockBean//fakes infos
	private ClienteRepository clienteRepository;
	
	@Test
	public void testWhenHasDuplicateEmail() throws Exception{
		Cliente c1 = new Cliente();
		c1.setId(1);
		c1.setNome("Jose");
		c1.setEmail("rrr@r.com");
		
		Mockito.when(clienteRepository.findByEmail(c1.getEmail())).thenReturn(c1);
		
		Cliente c2 = new Cliente();
		c2.setEmail("rrr@r.com");
		
		assertThatExceptionOfType(ValidationException.class).isThrownBy(()-> clienteService.saveCliente(c2));
		
	}

}
