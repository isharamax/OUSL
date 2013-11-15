/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.dto;

import java.io.Serializable;
import lombok.Getter;
import org.dutyroster.hibernate.database.PreOrientation;

/**
 *
 * @author mfh684
 */
public class PreOrientationDTO implements Serializable {
    @Getter 
    public PreOrientation po;
    @Getter 
    public String approvedStatus;
}
