package ssh.service;

import java.util.HashMap;

public interface ICfgSystemConfigService{

	/**
	 * ���o CFG_SYSTEM_CONFIG pages �ݭn�����
	 * @return
	 * @throws Exception 
	 */
	public HashMap<String, Object> getSCPInitialData() throws Exception;

}
