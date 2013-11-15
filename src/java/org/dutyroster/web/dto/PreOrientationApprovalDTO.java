/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.dto;

import lombok.Getter;
import lombok.Setter;
import org.dutyroster.hibernate.database.PreOrientationStaff;

/**
 *
 * @author mfh684
 */
public class PreOrientationApprovalDTO {
    @Getter@Setter
    private PreOrientationStaff pos;
    @Getter@Setter
    private boolean checked = false;
}
