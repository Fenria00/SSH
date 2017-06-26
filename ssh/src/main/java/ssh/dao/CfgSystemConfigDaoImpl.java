package ssh.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import bean.CfgSystemConfig;
import ssh.dao.baseDao.DaoBaseMariadb;

@Component("cfgSystemConfigDaoImpl")
public class CfgSystemConfigDaoImpl extends DaoBaseMariadb implements ICfgSystemConfigDao{
    
    public void insert(CfgSystemConfig csc) {
        // ���oSession
    	Session session = getSessionFactory();
        // �}�ҥ��
        Transaction tx= session.beginTransaction();
        // �����x�s����
        session.save(csc); 
        // �e�X���
        tx.commit();
        session.close(); 
    }

	@Override
	public CfgSystemConfig queryById(int id) {
		
		
		CfgSystemConfig csc= (CfgSystemConfig)getSessionFactory().get(CfgSystemConfig.class, id);
		
		getSessionFactory().close(); 
		
		return csc;
	}

	@Override
	public CfgSystemConfig update(CfgSystemConfig csc) {
		
		Session session = getSessionFactory();
		
		Transaction tx = session.beginTransaction();
		session.update(csc);
		tx.commit();
		session.close(); 
		
		session = getSessionFactory();
		CfgSystemConfig re= (CfgSystemConfig)session.get(CfgSystemConfig.class, csc.getId());
		session.close(); 
		
		return re;
	}

	@Override
	public void deleteById(int id) {
		
		CfgSystemConfig target = queryById(id);
		
		if(target != null){
			Session session = getSessionFactory();
			Transaction tx = session.beginTransaction();
			session.delete(target);
			tx.commit();
			session.close(); 
		}
		
	}

	@Override
	public List<CfgSystemConfig> searchById(int id) {
		
		Session session = getSessionFactory();
		
		String sql = "Select * from cfg_system_config where id = :id";
		
		Query query = session.createSQLQuery(sql)
			.setParameter("id", id);
		
		List tempList = query.list();
		List<CfgSystemConfig> result = new ArrayList<CfgSystemConfig>();
		
		for(Object obj : tempList){
			CfgSystemConfig tempCsc = (CfgSystemConfig)obj;
			result.add(tempCsc);
		}
		
		return result;
	}
}