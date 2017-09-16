package entidades;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the tbestado database table.
 * 
 */
@Entity
@Table(name="tbestado")
@NamedQueries({
    @NamedQuery(name = "Estado.findAll", query = "SELECT e FROM Estado e")
    , @NamedQuery(name = "Estado.findByPkEstado", query = "SELECT e FROM Estado e WHERE e.pkEstado = :pkEstado")
    , @NamedQuery(name = "Estado.findByNome", query = "SELECT e FROM Estado e WHERE e.nome = :nome")
    , @NamedQuery(name = "Estado.findByUf", query = "SELECT e FROM Estado e WHERE e.uf = :uf")})
public class Estado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int pkEstado;

	private String nome;

	private String uf;

	//bi-directional many-to-one association to Cidade
	@OneToMany(mappedBy="tbestado")
	private List<Cidade> tbcidades;

	public Estado() {
	}

	public int getPkEstado() {
		return this.pkEstado;
	}

	public void setPkEstado(int pkEstado) {
		this.pkEstado = pkEstado;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUf() {
		return this.uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public List<Cidade> getTbcidades() {
		return this.tbcidades;
	}

	public void setTbcidades(List<Cidade> tbcidades) {
		this.tbcidades = tbcidades;
	}

	public Cidade addTbcidade(Cidade tbcidade) {
		getTbcidades().add(tbcidade);
		tbcidade.setTbestado(this);

		return tbcidade;
	}

	public Cidade removeTbcidade(Cidade tbcidade) {
		getTbcidades().remove(tbcidade);
		tbcidade.setTbestado(null);

		return tbcidade;
	}
	
	public String toString() {
		return getNome();
	}

}