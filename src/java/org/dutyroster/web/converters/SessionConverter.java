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
import javax.faces.convert.FacesConverter;
import org.dutyroster.hibernate.database.Session;
import org.dutyroster.web.services.ConverterService;

/**
 *
 * @author mfh684
 */

@ManagedBean(name = "sessionConverter")
@RequestScoped
public class SessionConverter implements Converter {
    ConverterService converterService = new ConverterService();
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        System.out.println("Session Converter getas object : "+value);
        Session sessionByDesc = null;
        if (value == null || value.contains("Session")) {
            return null;
        }else{
            Short id = Short.valueOf(value);
            sessionByDesc = converterService.getSessionById(id);
            
        }
        // Convert the unique String representation of Foo to the actual Foo object.
        return sessionByDesc;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        // Convert the Foo object to its unique String representation.
        String result = "";
         System.out.println("Session Converter getas string : "+value);
       if (value == null || value instanceof String) {
            return null;
//        } else if ((value instanceof Short)) {
//            Session sessionByDesc = null;
//            Short id = Short.valueOf((Short)value);
//            sessionByDesc = converterService.getSessionById(id);
//            result = sessionByDesc.getSessionDesc();
//            return result;
        } else if(value instanceof Session) {
            result = ((Session) value).getSessionId().toString(); 
            return result;
        }else{
            return null;
        }
    } 
}
