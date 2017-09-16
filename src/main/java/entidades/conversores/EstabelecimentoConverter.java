package entidades.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.TypedQuery;

import controladores.Cadastro;
import entidades.Estabelecimento;

/**
 *
 * @author Maxwell
 */
@FacesConverter(forClass = Estabelecimento.class)
public class EstabelecimentoConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {

        if (string == null || string.length() == 0) {
            return null;
        }
        
        Integer id = new Integer(string);
        
        TypedQuery<Estabelecimento> query = Cadastro.GerenciadorEntidade().createNamedQuery("Estabelecimento.findByPkEstabelecimento", Estabelecimento.class);
        
        query.setParameter("pkEstabelecimento", id);
        
		return query.getSingleResult();
    }

    //Conversor utilizado para transformar a PK da entidade em string
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Estabelecimento) {
            Estabelecimento o = (Estabelecimento) object;
            if(o.getPkEstabelecimento() == 0) {
            	return "";
            }else{
            	return String.valueOf(o.getPkEstabelecimento());
            }
        } else {
            throw new IllegalArgumentException("Conversao invália para o objeto Estabelecimento");
        }
    }

}