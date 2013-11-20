/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.dto;

import lombok.Getter;
import lombok.Setter;
import org.dutyroster.hibernate.database.LearningStaff;

/**
 *
 * @author mfh684
 */
public class LearningToLearnStaffDTO {
    @Getter@Setter
    private LearningStaff learningStaff;
    @Getter@Setter
    private boolean checked;
}
