package br.com.softblue.bluefood.domain.restaurante;

import java.math.BigDecimal;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import br.com.softblue.bluefood.domain.usuario.Usuario;
import br.com.softblue.bluefood.infrastructure.web.validator.UploadConstraint;
import br.com.softblue.bluefood.util.FileType;
import br.com.softblue.bluefood.util.StringUtils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table(name = "restaurante")
@Entity
@SuppressWarnings("serial")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Restaurante  extends Usuario{
	@NotBlank(message = "O CNPJ não pode ser vazio")
	@Pattern(regexp = "[0-9]{14}", message= "O CNPJ possui formato inválido")
	@Column(length = 14, nullable = false)
	private String cnpj;
	
	@Size(max = 80)
	private String logotipo;
	
	@NotNull(message = "A taxa de entrega deve ser informada")
	@Min(0)
	@Max(99)
	private BigDecimal taxaEntrega;
	
	@UploadConstraint(acceptedTypes = FileType.PNG, message = "Arquivo precisa estar em formato PNG" )//notacao criada no validator
	private  transient MultipartFile logotipoFile;//transient não grava no banco
	
	@NotNull(message = "O tempo de entrega deve ser informado")
	@Min(0)
	@Max(99)
	private Integer tempoEntregaBase;
	
	@ManyToMany(fetch = FetchType.EAGER)//eager busca as categorias associadas ao banco
	@JoinTable(
			name = "restaurante_has_categorias",
			joinColumns= @JoinColumn(name="restaurante_id"),
			inverseJoinColumns = @JoinColumn(name="categoria_restaurante_id")
			
			)
	@Size(min = 1, message = "O restaurante precisa ter pelo menos uma categoria")
	@ToString.Exclude
	private Set<CategoriaRestaurante> categorias = new HashSet<>(0);
	
	@OneToMany(mappedBy = "restaurante")
	private Set<ItemCardapio> itensCardapio = new HashSet<>(0);
	
	public void setLogotipoFileName() {
		if(getId() == null) {
			throw new IllegalStateException("É preciso primeiro gravar o registro");
		}
		
		this.logotipo = String.format("%04d-logo.%s", getId(), FileType.of(logotipoFile.getContentType()).getExtension());
	}
	
	public String getCategoriaAsText() {
		Set<String> strings = new LinkedHashSet<>(); //linkedhashset não permite elementos duplicados
		
		for(CategoriaRestaurante categoria : categorias) {
			strings.add(categoria.getNome());
		}
		
		return StringUtils.concatenate(strings);
	}
	
	public Integer calcularTempoEntrega(String cep) {
		int soma = 0;
		for( char c : cep.toCharArray()) {
			int v =Character.getNumericValue(c);//getnumericvalue converte string em int
			if(v>0) {
				soma+=v;
				
			}
			
		}
		soma/=2;
		return tempoEntregaBase + soma;
	}

	}
