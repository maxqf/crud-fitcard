package entidades.conversores;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.persistence.TypedQuery;

import controladores.EstabelecimentoController;
import entidades.Categoria;

/**
 *
 * @author Maxwell
 */
@FacesConverter(forClass = Categoria.class)
public class CategoriaConverter implements Converter {

    public Object getAsObject(FacesContext facesContext, UIComponent component, String string) {

        if (string == null || string.length() == 0) {
            return null;
        }
        
        Integer id = new Integer(string);
        
        TypedQuery<Categoria> query = EstabelecimentoController.GerenciadorEntidade().createNamedQuery("Categoria.findByPkCategoria", Categoria.class);
        
        query.setParameter("pkCategoria", id);
        
		return query.getSingleResult();
    }

    //Conversor utilizado para transformar a PK da entidade em string
    public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Categoria) {
            Categoria o = (Categoria) object;
            if(o.getPkCategoria() == 0) {
            	return "";
            }else{
            	return String.valueOf(o.getPkCategoria());
            }
        } else {
            throw new IllegalArgumentException("Conversao invália para o objeto Categoria");
        }
    }

}