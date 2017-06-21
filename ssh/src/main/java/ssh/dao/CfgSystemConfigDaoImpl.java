package ssh.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import bean.CfgSystemConfig;

public class CfgSystemConfigDaoImpl implements ICfgSystemConfigDao{
	
	private SessionFactory sessionFactory; 
    
    public CfgSystemConfigDaoImpl() {
    }
    
    public CfgSystemConfigDaoImpl(SessionFactory sessionFactory) { 
        this.setSessionFactory(sessionFactory);
    }
    
    public void setSessionFactory(SessionFactory sessionFactory) { 
        this.sessionFactory = sessionFactory; 
    } 
    
    public void insert(CfgSystemConfig csc) {
        // ���oSession
        Session session = sessionFactory.openSession();
        // �}�ҥ��
        Transaction tx= session.beginTransaction();
        // �����x�s����
        session.save(csc); 
        // �e�X���
        tx.commit();
        session.close(); 
    }

    public CfgSystemConfig find(Integer id) {
    	
        Session session = sessionFactory.openSession(); 
        
        CfgSystemConfig user = (CfgSystemConfig) session.get(CfgSystemConfig.class, id);
        
        session.close();
        
        return user;
    }

}
