package ssh.service;

import java.util.HashMap;

import bean.CfgSystemConfig;

public interface ICfgSystemConfigService{

	/**
	 * ���o CFG_SYSTEM_CONFIG pages �ݭn�����
	 * @return
	 * @throws Exception 
	 */
	public HashMap<String, Object> getSCPInitialData() throws Exception;
	
	/**
	 * add one cfg_system_config data
	 * @throws Exception
	 */
	public void addFromConfigPage(CfgSystemConfig csc) throws Exception;

}
