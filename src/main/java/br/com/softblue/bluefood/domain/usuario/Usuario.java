package br.com.softblue.bluefood.domain.usuario;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;




import br.com.softblue.bluefood.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@SuppressWarnings("serial")
@MappedSuperclass
public class Usuario implements Serializable{
	

	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotBlank(message = "Nome não pode ser vazio")
	@Size(max = 80, message = "Nome muito grande")
	private String nome;
	
	@NotBlank(message = "E-mail não pode ser vazio")
	@Size(max = 60, message = "Nome muito grande")
	@Email(message = "Email invalido")
	private String email;
	
	@NotBlank(message = "Senha não pode ser vazia")
	@Size(max = 250, message = "Senha muito grande")
	private String senha;
	
	@NotBlank(message = "Telefone não pode ser vazio")
	@Pattern(regexp = "[0-9]{10,11}", message= "Telefone possui formato inválido")
	@Size(max = 60, message = "Telefone muito grande")
	@Column(length = 11, nullable = false)
	private String telefone;
	
	
	public void encryptPassword() {
		this.senha = StringUtils.encrypt(this.senha);
		
	}

}
