package GRUD.cfgSystemConfig;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import bean.CfgSystemConfig;
import ssh.dao.ICfgSystemConfigDao;

public class Insert {
		
	public static void main(String[] args) {
	
		try{
			
//			Session session = HibernateUtil.getSessionFactory().openSession();
//		    Transaction tx = session.beginTransaction();
		
			
			ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/applicationContext.xml");
		        
		    // �إ�DAO����
			ICfgSystemConfigDao userDAO = (ICfgSystemConfigDao) context.getBean("cfgSystemConfigDaoImpl");
			
		    CfgSystemConfig csc = new CfgSystemConfig();
		    csc.setId(3);
		    csc.setCodeCate("Test");
		    csc.setCateName("����");
		    csc.setCode("Test03");
		    csc.setCodeName("����2");
		    csc.setCodeValue("2");
		    csc.setCodeDesc("Test 02 go");
		    csc.setSeq(2);
		    csc.setParentId(1);
		    csc.setCreateDate(new Date());
		    csc.setCreateUser("Ziv");
		    csc.setUpdateDate(new Date());
		    csc.setUpdateUser("Ziv");
		    

		    userDAO.insert(csc);
		    
//		    session.save(csc);
//		    
//		    tx.commit();
//		    session.close();
//		    HibernateUtil.shutdown();
		    
		}catch(Exception e){
			System.out.println(e);
		}

    }

}
