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
import org.dutyroster.hibernate.database.Program;
import org.dutyroster.web.services.ConverterService;

/**
 *
 * @author mfh684
 */
@ManagedBean(name = "programConverter")
@RequestScoped
public class ProgramConverter implements Converter {

    ConverterService converterService = new ConverterService();

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Program programByName = null;
        if (value == null || value.contains("Program")) {
            return null;
        }else{
            Short id = Short.valueOf(value);
            programByName = converterService.getProgramById(id);
            
        }

        // Convert the unique String representation of Foo to the actual Foo object.
        return programByName;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        // Convert the Foo object to its unique String representation.
        String result = "";
        if (value == null || value instanceof String) {
            return null;
        
//        } else if ((value instanceof Short)) {
//            Program program = null;
//             Short id = Short.valueOf((Short)value);
//            program = converterService.getProgramById(id);
//            result =  program.getProgramName();
//            return result;
        } else if(value instanceof Program) {
            result = ((Program) value).getProgramId().toString();
            return result;
        }else{
            return null;
        }

    }
}
