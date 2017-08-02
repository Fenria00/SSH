package ssh.dao;

import java.util.List;

import bean.CfgSystemConfig;

public interface ICfgSystemConfigDao {
	
	/**
	 * insert need transaction;
	 * @param csc
	 * @throws Exception 
	 */
	public void insert(CfgSystemConfig csc) throws Exception;

	/**
	 * select by cfg_system_config id
	 * @param id
	 * @return
	 */
	public CfgSystemConfig queryById(int id);

	/**
	 * update need transaction;
	 * @param csc
	 * @return
	 */
	public CfgSystemConfig update(CfgSystemConfig csc);

	/**
	 * delete need transaction
	 * @param id
	 */
	public void deleteById(int id);

	/**
	 * selecet by id(script way), note : �p�Φ۰��ഫ, select �X���W�٭n�P�ܼƦW�٬ۦP, ���M���|�Q�ର�j�g
	 * @param id
	 * @return
	 */
	public List<CfgSystemConfig> searchById(int id);

	/**
	 * ���o CFG_SYSTEM_CONFIG �Ҧ����, �ھ� Code_Cate and Seq asc
	 * @return
	 */
	public List<CfgSystemConfig> getAllData();
    
}
