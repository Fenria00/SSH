package crud.cfgSystemConfig;

import java.util.Date;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import bean.CfgSystemConfig;
import ssh.dao.ICfgSystemConfigDao;

public class Insert {
		
	public static void main(String[] args) {
	
		try{
			
			ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/applicationContext.xml");
			ICfgSystemConfigDao dao = (ICfgSystemConfigDao) context.getBean("cfgSystemConfigDaoImpl");
			
		    CfgSystemConfig csc = new CfgSystemConfig();
		    csc.setId(101);
		    csc.setCodeCate("TableHeader");
		    csc.setCateName("�����D");
		    csc.setCode("TH_CFG_SYS");
		    csc.setCodeName("TH_CFG_SYS");
		    csc.setCodeValue("ID");
		    csc.setCodeDesc("CFG_SYTEM_CONFIG ���� ID");
		    csc.setSeq(1);
		    csc.setParentId(0);
		    csc.setCreateDate(new Date());
		    csc.setCreateUser("Ziv");
		    csc.setUpdateDate(new Date());
		    csc.setUpdateUser("Ziv");
		    
		    dao.insert(csc);
		    
		}catch(Exception e){
			System.out.println(e);
		}

    }

}
