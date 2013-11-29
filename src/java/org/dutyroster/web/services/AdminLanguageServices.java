/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dutyroster.web.services;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Query;
import org.dutyroster.hibernate.config.HibernateUtil;
import org.dutyroster.hibernate.database.Language;
import org.dutyroster.web.util.Constants;


/**
 *
 * @author Administrator
 */


public class AdminLanguageServices {
    
     Session session = null;
     private Short lanId;
     private String lanDesc;
       Language[] languagelist = null;
    int i;
    int result = 0;

    public String getLanDesc() {
        return lanDesc;
    }

    public void setLanDesc(String lanDesc) {
        this.lanDesc = lanDesc;
    }

    public Short getLanId() {
        return lanId;
    }

    public void setLanId(Short lanId) {
        this.lanId = lanId;
    }
     

    public AdminLanguageServices() {
        //this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    public String addLanguage(Language language) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Language add = new Language();
            add.setLanId(language.getLanId());
            add.setLanDesc(language.getLanDesc());
            session.save(add);
            session.getTransaction().commit();
            session.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;    
        }
        return Constants.SUCCESS;
    }

    
    public String editLanguage(Language language) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            //Query q = session.createQuery("UPDATE Center SET centerName= :cName WHERE centerId = :cID");
            //q.setString("cName", center.getCenterName());
            //q.setString("cID", center.getCenterId().toString());
            //result = q.executeUpdate();
            Language update = new Language();
            update.setLanId(language.getLanId());
            update.setLanDesc(language.getLanDesc());
            session.update(update);
            session.getTransaction().commit();
            session.disconnect();
            return Constants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
    }

    public String deleteLanguage(Language language) {
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Language del = new Language();
            del.setLanId(language.getLanId());
            del.setLanDesc(language.getLanDesc());
            session.delete(del);
            session.getTransaction().commit();
            session.disconnect();
            return Constants.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAILURE;
        }
    }

    public Short getLastLanguageID() {
        Short lastlId = 0;
        List<Language> languages = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("SELECT max(LanId) FROM Language");
            languages = (List<Language>) q.list();
            session.disconnect();
            if (languages != null && languages.size() > 0) {
                lastlId = languages.get(languages.size() - 1).getLanId();
            }
            return lastlId;

        } catch (Exception e) {
            e.printStackTrace();
            return lastlId;
        }
    }
        
    public Language[] searchAllLanguages() {
        List<Language> languages = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("FROM Language");
            languages = (List<Language>) q.list();
            session.disconnect();
            if (languages != null && languages.size() > 0) {
                languagelist = new Language[languages.size()];
                i = 0;
                for (Language language : languages) {
                    Language obj = new Language();
                    obj.setLanId(language.getLanId());
                    obj.setLanDesc(language.getLanDesc());
                    languagelist[i] = obj;
                    i++;
                }
            }
            return languagelist;

        } catch (Exception e) {
            e.printStackTrace();
            languagelist = new Language[1];
            return languagelist;
        }
    }
    
    
    public Language[] searchLanguagesByFilter(String Filter) {
        List<Language> languages = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            Query q = session.createQuery("from Language l where l.lanDesc like '%"+Filter+"%' ");
            
            languages = (List<Language>) q.list();
            session.disconnect();
            if (languages != null && languages.size() > 0) {
                languagelist = new Language[languages.size()];
                i = 0;
                for (Language l : languages) {
                    Language obj = new Language();
                    obj.setLanId(l.getLanId());
                    obj.setLanDesc(l.getLanDesc());
                    languagelist[i] = obj;
                    i++;
                }
            }
            return languagelist;

        } catch (Exception e) {
            e.printStackTrace();
            languagelist = new Language[1];
            return languagelist;
        }
    }
    
}
