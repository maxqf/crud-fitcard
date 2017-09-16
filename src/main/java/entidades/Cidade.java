package entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.NamedQueries;


/**
 * The persistent class for the tbcidade database table.
 * 
 */
@Entity
@Table(name="tbcidade")
@NamedQueries({
    @NamedQuery(name = "Cidade.findAll", query = "SELECT c FROM Cidade c")
    , @NamedQuery(name = "Cidade.findByPkCidade", query = "SELECT c FROM Cidade c WHERE c.pkCidade = :pkCidade")
    , @NamedQuery(name = "Cidade.findByFkEstado", query = "SELECT c FROM Cidade c WHERE c.tbestado = :fkEstado")
    , @NamedQuery(name = "Cidade.findByNome", query = "SELECT c FROM Cidade c WHERE c.nome = :nome")})
public class Cidade implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int pkCidade;

	private String nome;

	//bi-directional many-to-one association to Estado
	@ManyToOne
	@JoinColumn(name="fkEstado")
	private Estado tbestado;

	//bi-directional many-to-one association to Endereco
	@OneToMany(mappedBy="tbcidade")
	private List<Endereco> tbenderecos;

	public Cidade() {
	}

	public int getPkCidade() {
		return this.pkCidade;
	}

	public void setPkCidade(int pkCidade) {
		this.pkCidade = pkCidade;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Estado getTbestado() {
		return this.tbestado;
	}

	public void setTbestado(Estado tbestado) {
		this.tbestado = tbestado;
	}

	public List<Endereco> getTbenderecos() {
		return this.tbenderecos;
	}

	public void setTbenderecos(List<Endereco> tbenderecos) {
		this.tbenderecos = tbenderecos;
	}

	public Endereco addTbendereco(Endereco tbendereco) {
		getTbenderecos().add(tbendereco);
		tbendereco.setTbcidade(this);

		return tbendereco;
	}

	public Endereco removeTbendereco(Endereco tbendereco) {
		getTbenderecos().remove(tbendereco);
		tbendereco.setTbcidade(null);

		return tbendereco;
	}
	
	public String toString() {
		return getNome();
	}

}