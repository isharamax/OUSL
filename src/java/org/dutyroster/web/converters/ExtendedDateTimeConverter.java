/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.converters;

import java.util.TimeZone;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;

/**
 *
 * @author mfh684
 */
@ManagedBean(name = "dateConverter")
@RequestScoped
public class ExtendedDateTimeConverter extends DateTimeConverter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        setPattern((String) component.getAttributes().get("pattern"));
        //setTimeZone(TimeZone.getTimeZone((String) component.getAttributes().get("timeZone")));
        return super.getAsObject(context, component, value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        setPattern((String) component.getAttributes().get("pattern"));
        //setTimeZone(TimeZone.getTimeZone((String) component.getAttributes().get("timeZone")));
        return super.getAsString(context, component, value);
    }

}
