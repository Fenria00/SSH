package ssh.action.systemConfig;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import bean.CfgSystemConfig;
import ssh.action.BaseAction;
import ssh.service.ICfgSystemConfigService;
import ssh.service.IHallService;
import ssh.util.BeanUtil;
import ssh.util.CacheUtil;
import ssh.util.StringUtil;

@Component("systemConfigAction")
public class SystemConfigAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	
	private CfgSystemConfig addData;
	private CfgSystemConfig editData;
	private CfgSystemConfig removeData;
	private String cfgSysId;
	private String oldEditId;
	private List<String> deleteCfgSysIdList;
	private String header;
	private Boolean sortBy;
	private List<String> sortIdList;
	private List<CfgSystemConfig> tvEditDataList;
	private String tvRemoveId;
	
	@Autowired
	@Qualifier("cfgSystemConfigServiceImpl")
	private ICfgSystemConfigService cfgSystemConfigService;
	
	@Autowired
	@Qualifier("hallServiceImpl")
	private IHallService hallService;
	
	public String getSCPInitialData() {
		
		try{

			HashMap<String,Object> result = cfgSystemConfigService.getSCPInitialData();
			
			super.dataHandler(result);

		}catch(Exception e){
			System.out.println(e.getMessage());
			super.dataHandler(e);
		}
		
		return SUCCESS;
		
	}
	
	/**
	 * 穝糤掸 CFG_SYSTEM_CONFIG DATA
	 * @return
	 */
	public String addCfgSystemConfig() throws Exception{
		
		try{
			
		if(addData != null){
			cfgSystemConfigService.addFromConfigPage(addData);
		}else{
			throw new Exception("addData is null");
		}
			
		}catch(Exception e){
			System.out.println(e.getMessage());
			super.dataHandler(e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * search cfg_system_config by id
	 * @return
	 * @throws Exception
	 */
	public String searchCfgSysConById() throws Exception {
		
		try{
			CfgSystemConfig csc = CacheUtil.getSysCfgById(cfgSysId);
			HashMap<String,String> result = BeanUtil.transferBeanToHashMap(csc);
			super.dataHandler(result);
		}catch(Exception e){
			System.out.println(e.getMessage());
			super.dataHandler(e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * э╰参把计
	 * @return
	 * @throws Exception
	 */
	public String editCfgSysCon() throws Exception {
		
		try{
			
			HashMap<Boolean,String> editResponse = cfgSystemConfigService.editFromConfigPage(editData,oldEditId);
			super.dataHandler(editResponse);
			
		}catch(Exception e){
			System.out.println(e.getMessage());
			super.dataHandler(e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 埃╰参把计
	 * @return
	 * @throws Exception
	 */
	public String removeCfgSysCon() throws Exception {
		
		try{
			
			cfgSystemConfigService.removeFromConfigPage(removeData);
			super.dataHandler();
			
		}catch(Exception e){
			System.out.println(e.getMessage());
			super.dataHandler(e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 埃╰参把计 
	 * @return
	 * @throws Exception
	 */
	public String removeCfgSysByBatch() throws Exception {

		try{
			
			cfgSystemConfigService.removeCfgSysByBatch(deleteCfgSysIdList);
			super.dataHandler();
			
		}catch(Exception e){
			System.out.println(e.getMessage());
			super.dataHandler(e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 沮 table header 逼
	 * @return
	 * @throws Exception
	 */
	public String cfgSysConSortByHeader() throws Exception {

		try{

			List<String> sortResult = cfgSystemConfigService.cfgSysConSortByHeader(header,sortBy,sortIdList);
			super.dataHandler(sortResult);
			
		}catch(Exception e){
			System.out.println(e.getMessage());
			super.dataHandler(e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * 眔 hall ﹍戈
	 * @return
	 * @throws Exception
	 */
	public String hallPageInitial() throws Exception {

		try{

			HashMap<String,Object> result = hallService.hallPageInitial();
			super.dataHandler(result);
			
		}catch(Exception e){
			System.out.println(e.getMessage());
			super.dataHandler(e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * save tree view edit data
	 * @return
	 * @throws Exception
	 */
	public String tvEditSave() throws Exception {

		try{

			cfgSystemConfigService.tvEditSave(tvEditDataList);
			super.dataHandler();
			
		}catch(Exception e){
			System.out.println(e);
			super.dataHandler(e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * remove tree view edit data
	 * @return
	 * @throws Exception
	 */
	public String tvEditRemove() throws Exception {

		try{

			String result = cfgSystemConfigService.tvEditRemove(tvRemoveId);
			if(StringUtil.isEmpty(result)){
				super.dataHandler(result);
			}else{
				HashMap<String,String> massage = new HashMap<String,String>();
				massage.put("massage", result);
				super.dataHandler(massage);
			}
			
		}catch(Exception e){
			System.out.println(e);
			super.dataHandler(e);
		}
		
		return SUCCESS;
	}
	
	/**
	 * getBranchPageOptions
	 * @return
	 * @throws Exception
	 */
	public String getBranchPageOptions() throws Exception {

		try{

			List<String> pages = cfgSystemConfigService.getBranchPageOptions();
			super.dataHandler(pages);
			
		}catch(Exception e){
			System.out.println(e);
			super.dataHandler(e);
		}
		
		return SUCCESS;
	}
	
	
	
	
	public CfgSystemConfig getAddData() {
		return addData;
	}

	public void setAddData(CfgSystemConfig addData) {
		this.addData = addData;
	}

	public String getCfgSysId() {
		return cfgSysId;
	}

	public void setCfgSysId(String cfgSysId) {
		this.cfgSysId = cfgSysId;
	}

	public CfgSystemConfig getEditData() {
		return editData;
	}

	public void setEditData(CfgSystemConfig editData) {
		this.editData = editData;
	}

	public String getOldEditId() {
		return oldEditId;
	}

	public void setOldEditId(String oldEditId) {
		this.oldEditId = oldEditId;
	}

	public CfgSystemConfig getRemoveData() {
		return removeData;
	}

	public void setRemoveData(CfgSystemConfig removeData) {
		this.removeData = removeData;
	}

	public List<String> getDeleteCfgSysIdList() {
		return deleteCfgSysIdList;
	}

	public void setDeleteCfgSysIdList(List<String> deleteCfgSysIdList) {
		this.deleteCfgSysIdList = deleteCfgSysIdList;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public List<String> getSortIdList() {
		return sortIdList;
	}

	public void setSortIdList(List<String> sortIdList) {
		this.sortIdList = sortIdList;
	}

	public Boolean getSortBy() {
		return sortBy;
	}

	public void setSortBy(Boolean sortBy) {
		this.sortBy = sortBy;
	}

	public List<CfgSystemConfig> getTvEditDataList() {
		return tvEditDataList;
	}

	public void setTvEditDataList(List<CfgSystemConfig> tvEditDataList) {
		this.tvEditDataList = tvEditDataList;
	}

	public String getTvRemoveId() {
		return tvRemoveId;
	}

	public void setTvRemoveId(String tvRemoveId) {
		this.tvRemoveId = tvRemoveId;
	}

}
