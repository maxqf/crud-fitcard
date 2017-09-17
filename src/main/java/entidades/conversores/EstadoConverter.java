package entidades.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.TypedQuery;

import controladores.EstabelecimentoController;
import entidades.Estado;

/**
 *
 * @author Maxwell
 */
@FacesConverter(forClass = Estado.class)
public class EstadoConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {

        if (string == null || string.length() == 0) {
            return null;
        }
        
        Integer id = new Integer(string);
        
        TypedQuery<Estado> query = EstabelecimentoController.GerenciadorEntidade().createNamedQuery("Estado.findByPkEstado", Estado.class);
        
        query.setParameter("pkEstado", id);
        
		return query.getSingleResult();
    }

    //Conversor utilizado para transformar a PK da entidade em string
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Estado) {
            Estado o = (Estado) object;
            if(o.getPkEstado() == 0) {
            	return "";
            }else{
            	return String.valueOf(o.getPkEstado());
            }
        } else {
            throw new IllegalArgumentException("Conversao invália para o objeto Estado");
        }
    }

}