package entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the tbendereco database table.
 * 
 */
@Entity
@Table(name="tbendereco")
@NamedQuery(name="Endereco.findAll", query="SELECT e FROM Endereco e")
public class Endereco implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int pkEndereco;

	private String endereco;

	//bi-directional many-to-one association to Cidade
	@ManyToOne
	@JoinColumn(name="fkCidade")
	private Cidade tbcidade;

	//bi-directional many-to-one association to Estabelecimento
	@OneToMany(mappedBy="tbendereco")
	private List<Estabelecimento> tbestabelecimentos;

	public Endereco() {
	}

	public int getPkEndereco() {
		return this.pkEndereco;
	}

	public void setPkEndereco(int pkEndereco) {
		this.pkEndereco = pkEndereco;
	}

	public String getEndereco() {
		return this.endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public Cidade getTbcidade() {
		return this.tbcidade;
	}

	public void setTbcidade(Cidade tbcidade) {
		this.tbcidade = tbcidade;
	}

	public List<Estabelecimento> getTbestabelecimentos() {
		return this.tbestabelecimentos;
	}

	public void setTbestabelecimentos(List<Estabelecimento> tbestabelecimentos) {
		this.tbestabelecimentos = tbestabelecimentos;
	}

	public Estabelecimento addTbestabelecimento(Estabelecimento tbestabelecimento) {
		getTbestabelecimentos().add(tbestabelecimento);
		tbestabelecimento.setTbendereco(this);

		return tbestabelecimento;
	}

	public Estabelecimento removeTbestabelecimento(Estabelecimento tbestabelecimento) {
		getTbestabelecimentos().remove(tbestabelecimento);
		tbestabelecimento.setTbendereco(null);

		return tbestabelecimento;
	}

}