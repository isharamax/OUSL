/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.converters;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.dutyroster.hibernate.database.Center;
import org.dutyroster.web.services.ConverterService;
import org.dutyroster.web.services.PreOrientationService;

/**
 *
 * @author mfh684
 */
@ManagedBean(name = "centerConverter")
@RequestScoped
public class CenterConverter implements Converter, Serializable {

    ConverterService converterService = new ConverterService();

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        // Convert the unique String representation of Foo to the actual Foo object.
        Center center = null;
        if (value == null || value.contains("Center")) {
            return null;
        }else{
            Short id = Short.valueOf(value);
            center = converterService.getCenterById(id);
            
        }

        return center;
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        // Convert the Foo object to its unique String representation.
        String result = "";
        if (value == null || value instanceof String) {
            return null;
//        } else if ((value instanceof Short)) {
//            Center center = null;
//             Short id = Short.valueOf((Short)value);
//            center = converterService.getCenterById(id);
//            result =  center.getCenterName();
//            return result;
        } else if(value instanceof Center) {
            result = ((Center) value).getCenterId().toString();
            return result;
        }else{
            return null;
        }
    }
}
