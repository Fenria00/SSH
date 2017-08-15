package ssh.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Component;

import bean.CfgSystemConfig;
import ssh.dao.baseDao.DaoBaseMariadb;
import ssh.util.StringUtil;
import systemConfig.Sqlmapping;

@Component("cfgSystemConfigDaoImpl")
public class CfgSystemConfigDaoImpl extends DaoBaseMariadb implements ICfgSystemConfigDao{ 
	
    public void insert(CfgSystemConfig csc) throws Exception {
    	try{
    		
            // ���oSession
        	Session session = getSessionFactory();
            // �}�ҥ��
            Transaction tx= session.beginTransaction();
            // �����x�s����
            session.save(csc); 
            // �e�X���
            tx.commit();
            session.close(); 
    		
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    		throw new Exception(e);
    	}
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
		
		String sql = super.getSqlStatment(Sqlmapping.CFG_SYSTEM_CONFIG_SEARCH_BY_ID);		
		
		Query query = session.createSQLQuery(sql)
			.setParameter("id", id)
			.setResultTransformer(Transformers.aliasToBean(CfgSystemConfig.class));
		
		List tempList = query.list();
		List<CfgSystemConfig> result = new ArrayList<CfgSystemConfig>();
		
		return query.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CfgSystemConfig> getAllData() {
		
		try{
			List<CfgSystemConfig> result = 
					getSessionFactory().createCriteria(CfgSystemConfig.class).addOrder(Order.asc("id")).addOrder(Order.asc("codeCate")).addOrder(Order.asc("seq")).list();
			return result;
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		return null;
	}

	@Override
	public List<CfgSystemConfig> searchCfgSysDataBySort(String orderKey, String type) {
		
		Session session = getSessionFactory();
		
		String sql = super.getSqlStatment(Sqlmapping.SEARCH_CFG_SYSTEM_DATA_FOR_SORT);
		
		if(StringUtil.isEmpty(orderKey)){
			orderKey = "id"; //default
		}
		
		if(!StringUtil.isEmpty(type)){
			sql = sql + " " + type;
		}
		
		Query query = session.createSQLQuery(sql)
			.setParameter("orderKey", orderKey)
			.setResultTransformer(Transformers.aliasToBean(CfgSystemConfig.class));
		
		return query.list();
	}

	@Override
	public void deleteByIdList(List<String> deleteCfgSysIdList) {
		
		Session session = getSessionFactory();
		
		Transaction tx = session.beginTransaction();
		
		String sql = super.getSqlStatment(Sqlmapping.CFG_SYSTEM_CONFIG_DELETE_BY_ID);
		
		Query query = session.createSQLQuery(sql)
				.setParameterList("idList", deleteCfgSysIdList);
		
		query.executeUpdate();
		
		tx.commit();
		
		session.close(); 
		
	}

	@Override
	public List<String> cfgSysConSortByHeader(String header, String orderKey, List<String> sortIdList) {
		
		Session session = getSessionFactory();
		
		Transaction tx = session.beginTransaction();
		
		String sql =  super.getSqlStatment(Sqlmapping.CFG_SYSTEM_CONFIG_DATA_SORT_BY_HEANDER) + " " + header + " " + orderKey;
		
		Query query = session.createSQLQuery(sql)
				.setParameterList("sortIdList", sortIdList);
		
		return query.list();
	}
}
