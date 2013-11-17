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
import org.dutyroster.hibernate.database.Department;
import org.dutyroster.web.services.ConverterService;

/**
 *
 * @author mfh684
 */
@ManagedBean(name = "departmentConverter")
@RequestScoped
public class DepartmentConverter implements Converter {

    ConverterService converterService = new ConverterService();
   @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        Department department = null;
       if (value == null || value.contains("Department")) {
           return null;
        }else{
            Short id = Short.valueOf(value);
            department = converterService.getDeptById(id);         
       }

        return department;
    }

   @Override
    public String getAsString(FacesContext context,UIComponent component,Object value) {
        String result = "";
        if (value == null || value instanceof String) {
            return null;
//        } else if ((value instanceof Short)) {
//            Department department = null;
//             Short id = Short.valueOf((Short)value);
//            department = converterService.getDeptById(id);
//            result =  department.getDeptName();
//            return result;
        } else if(value instanceof Department) {
            result = ((Department) value).getDeptId().toString();
            return result;
        }else{
            return null;
        }
    }
    
}
