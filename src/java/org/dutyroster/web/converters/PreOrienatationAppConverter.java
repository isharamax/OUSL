/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.converters;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import org.dutyroster.hibernate.database.PreOrientation;
import org.dutyroster.web.services.ConverterService;
import org.dutyroster.web.services.PreOrientationService;

/**
 *
 * @author mfh684
 */
@ManagedBean(name = "preAppConverter")
@RequestScoped
public class PreOrienatationAppConverter implements Converter{

    ConverterService converterService = new ConverterService();
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        PreOrientation po = null;
        if (value == null ) {
            return null;
        }else{
            Short id = Short.valueOf(value);
            po = converterService.getPreOrientationById(id);
        
        }
        return po;
        
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
  
         String result = "";
        if (value == null || value instanceof String) {
            return null;
        
        } else if(value instanceof PreOrientation) {
            result = ((PreOrientation) value).getPreId().toString();
            return result;
        }else{
            return null;
        }
    }
    
}
