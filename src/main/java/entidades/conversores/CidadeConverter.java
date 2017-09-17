package entidades.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.TypedQuery;

import controladores.EstabelecimentoController;
import entidades.Cidade;

/**
 *
 * @author Maxwell
 */
@FacesConverter(forClass = Cidade.class)
public class CidadeConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {

        if (string == null || string.length() == 0) {
            return null;
        }
        
        Integer id = new Integer(string);
        
        TypedQuery<Cidade> query = EstabelecimentoController.GerenciadorEntidade().createNamedQuery("Cidade.findByPkCidade", Cidade.class);
        
        query.setParameter("pkCidade", id);
        
		return query.getSingleResult();
    }

    //Conversor utilizado para transformar a PK da entidade em string
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Cidade) {
            Cidade o = (Cidade) object;
            if(o.getPkCidade() == 0) {
            	return "";
            }else{
            	return String.valueOf(o.getPkCidade());
            }
        } else {
            throw new IllegalArgumentException("Conversao invália para o objeto Cidade");
        }
    }

}