package entidades;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * The persistent class for the tbestabelecimento database table.
 * 
 */
@Entity
@Table(name="tbestabelecimento")
@NamedQueries({
    @NamedQuery(name = "Estabelecimento.findAll", query = "SELECT e FROM Estabelecimento e"),
    @NamedQuery(name = "Estabelecimento.findByPkEstabelecimento", query = "SELECT e FROM Estabelecimento e WHERE e.pkEstabelecimento = :pkEstabelecimento"),
    @NamedQuery(name = "Estabelecimento.findByCnpj", query = "SELECT e FROM Estabelecimento e WHERE e.cnpj = :cnpj")})
public class Estabelecimento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int pkEstabelecimento;
    
	@Basic(optional = false)
    @NotNull
    @Size(min = 14, max = 18)
	private String cnpj;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;

	private String email;

	private String nomeFantasia;

	@Basic(optional = false)
    @NotNull
    @Size(min = 14, max = 512)
	private String razaoSocial;

	private Boolean status;

	private String telefone;

	//bi-directional many-to-one association to Endereco
	@JoinColumn(name="fkEndereco")
	private Endereco tbendereco;

	//bi-directional many-to-one association to Categoria
	@ManyToOne
	@JoinColumn(name="fkCategoria")
	private Categoria tbcategoria;

	public Estabelecimento() {
	}

	public int getPkEstabelecimento() {
		return this.pkEstabelecimento;
	}

	public void setPkEstabelecimento(int pkEstabelecimento) {
		this.pkEstabelecimento = pkEstabelecimento;
	}

	public String getCnpj() {
		return this.cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Date getDataCadastro() {
		return this.dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNomeFantasia() {
		return this.nomeFantasia;
	}

	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getRazaoSocial() {
		return this.razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public Boolean getStatus() {
		return this.status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getTelefone() {
		return this.telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Endereco getTbendereco() {
		return this.tbendereco;
	}

	public void setTbendereco(Endereco tbendereco) {
		this.tbendereco = tbendereco;
	}

	public Categoria getTbcategoria() {
		return this.tbcategoria;
	}

	public void setTbcategoria(Categoria tbcategoria) {
		this.tbcategoria = tbcategoria;
	}

}