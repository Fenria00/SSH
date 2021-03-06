package ssh.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import bean.CfgSystemConfig;
import ssh.dao.ICfgSystemConfigDao;

@Component("cacheUtil")
public class CacheUtil {
	
	public static List<CfgSystemConfig> allSysCfgData = new ArrayList<CfgSystemConfig>(); //all cfg_system_config data
	public static ConcurrentHashMap<String,CfgSystemConfig> sysCfgById = new ConcurrentHashMap<String,CfgSystemConfig>(); //by id
	public static ConcurrentHashMap<String,CfgSystemConfig> sysCfgByCode = new ConcurrentHashMap<String,CfgSystemConfig>(); //by code
	public static ConcurrentHashMap<String,List<CfgSystemConfig>> sysCfgByCodeCate = new ConcurrentHashMap<String,List<CfgSystemConfig>>(); //by code cate
	public static ConcurrentHashMap<String,List<CfgSystemConfig>> sysCfgByParentId = new ConcurrentHashMap<String,List<CfgSystemConfig>>(); //by parent id
	
	@Autowired
	@Qualifier("cfgSystemConfigDaoImpl")
	private ICfgSystemConfigDao cfgSystemConfigDao;
	
	@PostConstruct
	public void init() {	
		this.initCfgSystemConfig();
	}	
	
	/**
	 * 初始化 CFG_SYSTEM_CONFIG 相關 cache data
	 * Reset static : use a temp variable
	 * Quote : https://stackoverflow.com/questions/35446345/can-you-reset-a-static-variable
	 */
	private void initCfgSystemConfig() {
		
		List<CfgSystemConfig> tmpAllSysCfgData = cfgSystemConfigDao.getAllData();
		ConcurrentHashMap<String,CfgSystemConfig> tmpSysCfgById = new ConcurrentHashMap<String,CfgSystemConfig>();
		ConcurrentHashMap<String,CfgSystemConfig> tmpSysCfgByCode = new ConcurrentHashMap<String,CfgSystemConfig>();
		ConcurrentHashMap<String,List<CfgSystemConfig>> tmpSysCfgByCodeCate = new ConcurrentHashMap<String,List<CfgSystemConfig>>();
		ConcurrentHashMap<String,List<CfgSystemConfig>> tmpSysCfgByParentId = new ConcurrentHashMap<String,List<CfgSystemConfig>>();
		
		if(tmpAllSysCfgData != null && tmpAllSysCfgData.size() > 0){
			
			for(CfgSystemConfig csc : tmpAllSysCfgData){
				
				//By Id
				tmpSysCfgById.put(String.valueOf(csc.getId()), csc);
				
				//By Code
				tmpSysCfgByCode.put(csc.getCode(), csc);

				//By Code Cate : 如果 codeCate list 存在就取出 -> 新增一筆 -> 再存入 ; 沒有就新創一組 List
				String tmpCodeCate = csc.getCodeCate();
				List<CfgSystemConfig> tmpCodeCateList = (tmpSysCfgByCodeCate.get(tmpCodeCate) != null)?
						tmpSysCfgByCodeCate.get(tmpCodeCate):new ArrayList<CfgSystemConfig>();
				tmpCodeCateList.add(csc);
				tmpSysCfgByCodeCate.put(tmpCodeCate, tmpCodeCateList);
				//By Parent Id
				String tmpParentId = String.valueOf(csc.getParentId());
				List<CfgSystemConfig> tmpParentIdList = (tmpSysCfgByParentId.get(tmpParentId) != null)?
						tmpSysCfgByParentId.get(tmpParentId):new ArrayList<CfgSystemConfig>();
				tmpParentIdList.add(csc);
				tmpSysCfgByParentId.put(tmpParentId, tmpParentIdList);
				
			}
		}	
		
		allSysCfgData = tmpAllSysCfgData;
		sysCfgById = tmpSysCfgById;
		sysCfgByCode = tmpSysCfgByCode;
		sysCfgByCodeCate = tmpSysCfgByCodeCate;
		sysCfgByParentId = tmpSysCfgByParentId;
		
	}

	
	/**
	 * 取得 CFG_SYSTEM_CONFIG by Id
	 * @param id
	 * @return
	 */
	public static CfgSystemConfig getSysCfgById(String id){
		return sysCfgById.get(id);
	}
	
	public static List<CfgSystemConfig> getSysCfgDatas() {
		return allSysCfgData;
	}

	public ICfgSystemConfigDao getCfgSystemConfigDao() {
		return this.cfgSystemConfigDao;
	}

	public void setCfgSystemConfigDao(ICfgSystemConfigDao cfgSystemConfigDao) {
		this.cfgSystemConfigDao = cfgSystemConfigDao;
	}

	/**
	 * 取得 CFG_SYSTEM_CONFIG List by Code_Cate
	 * @param codeCate
	 * @return
	 */
	public static List<CfgSystemConfig> getSysCfgByCodeCate(String codeCate) {
		
		if(sysCfgByCodeCate.get(codeCate) != null && sysCfgByCodeCate.get(codeCate).size() > 0){
			return sysCfgByCodeCate.get(codeCate);
		}
		
		return null;
	}

	/**
	 * 取得 CFG_SYSTEM_CONFIG by Code
	 * @param string
	 * @return
	 */
	public static CfgSystemConfig getSysCfgByCode(String code) {
		return sysCfgByCode.get(code);
	}

	/**
	 * 取得 CFG_SYSTEM_CONFIG list by Parent_Id
	 * @param tvRemoveId
	 * @return
	 */
	public static List<CfgSystemConfig> getSysCfgByParentId(String tvRemoveId) {
		
		if(sysCfgByParentId.get(tvRemoveId) != null && sysCfgByParentId.get(tvRemoveId).size() > 0){
			return sysCfgByParentId.get(tvRemoveId);
		}
		
		return null;
	}

	
}
