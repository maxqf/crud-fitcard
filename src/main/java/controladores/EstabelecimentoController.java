package controladores;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.primefaces.context.RequestContext;
import org.primefaces.expression.ComponentNotFoundException;

import entidades.Categoria;
import entidades.Cidade;
import entidades.Endereco;
import entidades.Estabelecimento;
import entidades.Estado;
import util.MegaUtil;

@ManagedBean(name = "mbEstabelecimento")
@SessionScoped
public class EstabelecimentoController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8013982047789798088L;

	private static EntityManager ge;
	private Estabelecimento estabelecimento = null;
	private Endereco endereco = null;
	private Estado estado = null;

	/*
	 * Padrao singleton para o gerenciador de entidades retorna o mesmo gereciador
	 * durante o ciclo de vida do managed bean
	 */
	public static EntityManager GerenciadorEntidade() {
		if (ge == null) {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("desafiofit");
			ge = emf.createEntityManager();
		}
		return ge;
	}

	public Estabelecimento getEstabelecimento() {
		if (estabelecimento == null) {
			estabelecimento = new Estabelecimento();
		}

		return estabelecimento;
	}

	public Estado getEstado() {

		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public Endereco getEndereco() {
		if (endereco == null) {
			endereco = new Endereco();
		}

		return endereco;
	}
	
	/*
	 * Método responsável pela persistencia de cadastro e edição
	 * retorna regra de navegacao de cadastro caso seja cadastro
	 * retorna regra de navegacao de listagem caso seja edicao
	 * verificar faces-config.file 
	 */
	public String cadastrar(boolean reset) {
		
		try {
			
			EntityTransaction tx = GerenciadorEntidade().getTransaction();
			
			/*
			 * Checa as regras de negócios, se houver algo inválido retorna 
			 * pra view de Cadastro/Edicao
			 */
			if(!validaRegrasDeNegocio()) {
				
				return viewCadastro();
				
			}else if(endereco!= null && endereco.getTbcidade() != null) {	
				
				//Persiste o endereço se tiver o usuário setou uma cidade.	
				tx.begin();
				
				GerenciadorEntidade().persist(endereco);
				
				tx.commit();
				
				estabelecimento.setTbendereco(endereco);				
			}
				
			//Persiste o estabelecimento.
			tx.begin();
	
			GerenciadorEntidade().persist(estabelecimento);
			
			tx.commit();
			
		} catch (Exception e) {
			
			MegaUtil.ensureAddErrorMessage(e, "Ocorreu um erro de persistência");
			
			return  viewCadastro();
			
		}

		MegaUtil.addSuccessMessage("Estabelecimento gravado com sucesso.");
		
		if(reset) {			
			resetaControlador();
		}
		
		return viewCadastro();
	}
	
	/*
	 * Comando de edição, realiza a mesma operação de persistencia que cadastro.
	 * Retorna view de edicao
	 * */
	public String editar() {
			
		cadastrar(false);
		
		return viewEditar();
	
	}
	

	/*
	 * Método interno para a view de listagem
	 * Retorna a regra de navegacao para detalhar estabelecimento
	 */
	public String viewAbrir(Estabelecimento estabelecimento) {
		
		resetaControlador();
		
		//Reusa o método de edição
		viewEditar(estabelecimento); 
		
		return "view_abrir_estabelecimento";
	}
	
	/*
	 * Método interno para a view de edicao
	 * Retorna a regra de navegacao
	 */
	public String viewEditar(Estabelecimento estabelecimento) {
		
		this.estabelecimento = estabelecimento;
		
		this.endereco = estabelecimento.getTbendereco();
		
		if(endereco!=null && endereco.getTbcidade()!=null){
			
			this.estado = endereco.getTbcidade().getTbestado();
		
		}

		return viewEditar();
	}
	
	/*
	 * Método interno remover entidade, não possui view.
	 * Retorna a regra de navegacao de listagem de estabelecimentos.
	 */
	@SuppressWarnings("finally")
	public String remover(Estabelecimento estabelecimento) {
		
		EntityTransaction tx = GerenciadorEntidade().getTransaction();
		
		try {
			//Persiste o endereço se tiver o usuário setou uma cidade.	
			tx.begin();
			
			GerenciadorEntidade().remove(estabelecimento);
			
			tx.commit();
			
			MegaUtil.addErrorMessage("Estabelecimento removido com sucesso.");
			
		} catch (Exception e) {
			
			MegaUtil.ensureAddErrorMessage(e, "Ocorreu um erro de persistência");
			
		} finally {
			
			return  viewListagem();
			
		}
	
	}
		
	public String viewListagem() {
		
		return "view_listagem_estabelecimentos";
		
	}
	
	public String viewCadastro() {
		
		return "view_cadastro_estabelecimento";
		
	}
	
	public String viewEditar() {
		
		return "view_editar_estabelecimento";
		
	}
	
	/*
	 * Regras de negócio
	 * */
	public boolean validaRegrasDeNegocio() {

		if(estabelecimento.getRazaoSocial().isEmpty()){	
			MegaUtil.addErrorMessage("O campo Razão Social é obrigatório.");
		}
		
		if(estabelecimento.getCnpj().isEmpty()){
			MegaUtil.addErrorMessage("O campo CNPJ é obrigatório.");					
		}
		
		if(!estabelecimento.getCnpj().isEmpty() && !MegaUtil.isCNPJvalido(estabelecimento.getCnpj())){
			MegaUtil.addErrorMessage("O CNPJ é inválido.");					
		}
		
		if(!estabelecimento.getCnpj().isEmpty()){
			Estabelecimento cadastrado = getEstabelecimentoByCNPJ(estabelecimento.getCnpj());
			
			if(cadastrado!=null && !cadastrado.equals(estabelecimento)){
				MegaUtil.addErrorMessage("Este CNPJ já está cadastrado.");
				estabelecimento.setCnpj("");
			}		
		}

		if(!estabelecimento.getEmail().isEmpty() && 
				!MegaUtil.VALID_EMAIL_ADDRESS_REGEX.matcher(estabelecimento.getEmail()).matches()){
			MegaUtil.addErrorMessage("Email inválido.");					
		}
				
		if(endereco!= null && !endereco.getEndereco().isEmpty() && endereco.getTbcidade()==null){
			MegaUtil.addErrorMessage("Selecione o estado e cidade.");
		}
		
		//Caso a categoria seja Supermercado, o telefone passa a ser obrigatório;
		if(estabelecimento.getTbcategoria()!=null && 
				estabelecimento.getTbcategoria().getPkCategoria() == 1 &&
				estabelecimento.getTelefone().isEmpty()) {
			
			MegaUtil.addErrorMessage(String.format("Categoria %s selecionada, favor preencher o telefone.", 
					estabelecimento.getTbcategoria().getNome()));
		}
		
		return FacesContext.getCurrentInstance().getMessageList().isEmpty();
	}

	/*
	 * Método interno de preparação do cadastro Retorna a regra de navegação para
	 * view de cadastro
	 */
	public String initCadastro() {
		//resetaControlador();

		estabelecimento = new Estabelecimento();

		return viewCadastro();
	}

	/*
	 * Método auxiliar para resetar atributos
	 * Não retorna nada
	 */
	public void resetaControlador() {
		estabelecimento = new Estabelecimento();
		endereco = new Endereco();
		estado = new Estado();
		try {			
			RequestContext.getCurrentInstance().reset("form:panel");
		}catch (ComponentNotFoundException e) {
			System.out.println ( "Elemento não encontrado: " + e.getMessage() );
		} 		
		catch (Exception e) {
			// TODO: handle exception
			System.out.println ( e.getMessage() );
		}
	
	}

	public SelectItem[] getCategorias() {

		TypedQuery<Categoria> query = GerenciadorEntidade().createNamedQuery("Categoria.findAll", Categoria.class);

		return MegaUtil.getSelectItems(query.getResultList(), false);

	}

	public SelectItem[] getCidades() {

		if (getEstado() != null) {
			TypedQuery<Cidade> query = GerenciadorEntidade().createNamedQuery("Cidade.findByFkEstado", Cidade.class);

			query.setParameter("fkEstado", getEstado());

			return MegaUtil.getSelectItems(query.getResultList(), false);
		} else {

			return MegaUtil.getSelectItems(new ArrayList<Cidade>(), true);
		}
	}

	/*
	 * Método reponsável por retonar a lista de estados como componente SelectItem
	 * */
	public SelectItem[] getEstados() {

		TypedQuery<Estado> query = GerenciadorEntidade().createNamedQuery("Estado.findAll", Estado.class);

		return MegaUtil.getSelectItems(query.getResultList(), false);

	}
	
	public Estabelecimento getEstabelecimentoByCNPJ(String cnpj) {

		TypedQuery<Estabelecimento> query = EstabelecimentoController.GerenciadorEntidade().createNamedQuery("Estabelecimento.findByCnpj", Estabelecimento.class);
	    
	    query.setParameter("cnpj", cnpj);
	    
	    Object obj = null;
	    
	    try {			
	    
	    	obj = query.getSingleResult();
	    	
		} catch (NoResultException e) {
		
			System.out.println("Nenhum estabelecimento encontrado com o respectivo CNPJ.");
		}
	    
		return (Estabelecimento) obj;
	}
	
	public List<Estabelecimento> getEstabelecimentos() {

		TypedQuery<Estabelecimento> query = GerenciadorEntidade().createNamedQuery("Estabelecimento.findAll", Estabelecimento.class);
		
		return query.getResultList();

	}

}
