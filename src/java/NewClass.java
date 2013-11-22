
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.dutyroster.hibernate.database.LearningToLearn;
import org.dutyroster.web.controller.LearningtoLearnController;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author mfh684
 */
public class NewClass {
    public static void main(String [] args){
        Date date;
        try {
            date = new SimpleDateFormat("y-M-d", Locale.ENGLISH).parse("2013-10-11");
       System.out.println(date);
           
            
        
        } catch (ParseException ex) {
            Logger.getLogger(LearningtoLearnController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
