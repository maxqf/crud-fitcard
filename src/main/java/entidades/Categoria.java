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
 * The persistent class for the tbcategoria database table.
 * 
 */
@Entity
@Table(name="tbcategoria")
@NamedQueries({
    @NamedQuery(name = "Categoria.findAll", query = "SELECT c FROM Categoria c")
    , @NamedQuery(name = "Categoria.findByPkCategoria", query = "SELECT c FROM Categoria c WHERE c.pkCategoria = :pkCategoria")
    , @NamedQuery(name = "Categoria.findByNome", query = "SELECT c FROM Categoria c WHERE c.nome = :nome")})
public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int pkCategoria;

	private String nome;

	//bi-directional many-to-one association to Estabelecimento
	@OneToMany(mappedBy="tbcategoria")
	private List<Estabelecimento> tbestabelecimentos;

	public Categoria() {
	}

	public int getPkCategoria() {
		return this.pkCategoria;
	}

	public void setPkCategoria(int pkCategoria) {
		this.pkCategoria = pkCategoria;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Estabelecimento> getTbestabelecimentos() {
		return this.tbestabelecimentos;
	}

	public void setTbestabelecimentos(List<Estabelecimento> tbestabelecimentos) {
		this.tbestabelecimentos = tbestabelecimentos;
	}

	public Estabelecimento addTbestabelecimento(Estabelecimento tbestabelecimento) {
		getTbestabelecimentos().add(tbestabelecimento);
		tbestabelecimento.setTbcategoria(this);

		return tbestabelecimento;
	}

	public Estabelecimento removeTbestabelecimento(Estabelecimento tbestabelecimento) {
		getTbestabelecimentos().remove(tbestabelecimento);
		tbestabelecimento.setTbcategoria(null);

		return tbestabelecimento;
	}
	
	public String toString() {
		return getNome();
	}

}