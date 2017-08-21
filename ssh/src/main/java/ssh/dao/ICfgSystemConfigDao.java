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
	 * delete by id
	 * @param id
	 */
	public void deleteById(int id);

	/**
	 * delete by id
	 * @param id
	 */
	public void deleteById(String id);
	
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

	/**
	 * ���� CFG_SYSTEM_CONFIG �Ҧ����, �ھڱƧ� Key Word �Ƨ�
	 * @param string
	 * @return
	 */
	public List<CfgSystemConfig> searchCfgSysDataBySort(String orderKey, String type);

	/**
	 * batch delete by id
	 * @param deleteCfgSysIdList
	 */
	public void deleteByIdList(List<String> deleteCfgSysIdList);

	/**
	 * id list sort by table header
	 * @param header
	 * @param sortBy
	 * @param sortIdList
	 * @return
	 */
	public List<String> cfgSysConSortByHeader(String header, String orderkey, List<String> sortIdList);

	/**
	 * get new Id and Seq by Code Cate
	 * @param codeCate
	 * @return
	 */
	public CfgSystemConfig getNewIdSeqByCodeCate(String codeCate);

    
}
