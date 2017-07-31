package ssh.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Component;

import bean.CfgSystemConfig;
import ssh.util.CacheUtil;
import ssh.util.StringUtil;
import systemConfig.SysCfgCode;

@Component("cfgSystemConfigServiceImpl")
public class CfgSystemConfigServiceImpl implements ICfgSystemConfigService{

	@Override
	public HashMap<String, Object> getSCPInitialData() throws Exception {
		
		HashMap<String, Object> result = new HashMap<String, Object>();
		
		try{
			//TableHeader
			List<CfgSystemConfig> tmpTHData = CacheUtil.getSysCfgByCodeCate(SysCfgCode.CodeCate.TableHeader);
			if(tmpTHData != null && tmpTHData.size() > 0){
				
				List<String> thData = new ArrayList<String>();
				for(CfgSystemConfig csc : tmpTHData){
					thData.add(csc.getCodeValue());
				}
				result.put("tableHeader", thData);
				
				//TableBody : match thData which success
				List<CfgSystemConfig> tmpCSC = CacheUtil.getSysCfgDatas();
				if(tmpCSC != null && tmpCSC.size() > 0){		
					ArrayList<ArrayList<String>> tbData = buildTableBodyData(thData,tmpCSC);
					result.put("tableBody", tbData);
				}
			}
			
			//Add_TableHeader && empty cfg_sysytem_config bean
			List<CfgSystemConfig> tmpAddTHData = CacheUtil.getSysCfgByCodeCate(SysCfgCode.CodeCate.Add_TableHeader);
			if(tmpAddTHData != null && tmpAddTHData.size() > 0){
				HashMap<String,String> addThData = buildAddCfgSystemConfigBean(tmpAddTHData);
				result.put("addCfgSysConBean", addThData);
			}
			
		}catch(Exception e){
			throw new Exception(e);
		}
		
		return result;
	}

	/**
	 * �N CfgSystemConfig �ର HashMap,key : attr, value : true = show
	 * @param tmpAddTHData
	 * @return
	 */
	private HashMap<String, String> buildAddCfgSystemConfigBean(List<CfgSystemConfig> tmpAddTHData) {
		
		LinkedHashMap<String, String> result = new LinkedHashMap<String, String>();
		List<String> camelCaseNameList = new ArrayList<String>(); //result of before mapping work
		
		//1.naming mapping : DB name -> java bean attr name
		for(CfgSystemConfig csc : tmpAddTHData){
			String tmpName = csc.getCodeValue();
			//a. ���o_����m+1(��j�g��)
			int upStrIndex = 0; //�s��_����m
			char[] tmpCharAry = tmpName.toCharArray();
			int tmpCount = 0;
			for(char c : tmpCharAry){
				tmpCount++;
				if("_".equals(String.valueOf(c))){
					upStrIndex = tmpCount; //�]���q0�}�l, �i�H�������o+1��m
					break;
				}
			}
			//b. �����ର�p�g
			tmpName = tmpName.toLowerCase();
			//c. �N_�᪺�r�ର�j�g, ����_
			if(upStrIndex != 0){
				String upStr = String.valueOf(tmpName.charAt(upStrIndex)).toUpperCase();
				String newName = tmpName.substring(0, upStrIndex-1) + upStr + tmpName.substring(upStrIndex+1);
				camelCaseNameList.add(newName);
			}else{
				camelCaseNameList.add(tmpName);
			}
		}
		
		//2. ���o csc attr id,�s�b camelCaseNameMap �h�� true(show)
		CfgSystemConfig csc = new CfgSystemConfig();
		List<String> cscNameList = new ArrayList<String>();
		Field[] fs = csc.getClass().getDeclaredFields();
		for(Field f : fs){
			cscNameList.add(f.getName());
		}
		
		//3. �]�Ƨǰ��D,header ���D
		for(String ccn : camelCaseNameList){
			for(String cscN : cscNameList){
				if(ccn.equals(cscN)){
					result.put(cscN, "true");
					break;
				}
			}
		}
		
		return result;
	}

	/**
	 * �q CfgSystemConfig List ���A����P TableHeader ���������
	 * @param thData : table header data
	 * @param tmpCSC : all cfgSystemConfig data
	 * @return
	 */
	private ArrayList<ArrayList<String>> buildTableBodyData(List<String> thData, List<CfgSystemConfig> tmpCSC) throws Exception{
		
		ArrayList<ArrayList<String>> result = new ArrayList<ArrayList<String>>();
		
		//1.�ư�_�u �P �����ର�j�g�Τ@
		List<String> beRegexThData = new ArrayList<String>();
		for(String tmpTh : thData){
			String tmpStr = tmpTh.replace("_", "").toUpperCase();
			beRegexThData.add(tmpStr);
		}
		
		//2.����C�@�� CfgSystemConfig ���
		//A. ����C�@����  CfgSystemConfig �ѼƦW���ର�j�g(�ΨӤ��)�M�Ѽƭ��ରString -> �s�b�@�ӷ|�Q��s�� HashMap, key=Table Header Data
		//B. �۹�����Ʃ�J tmpList
		//C. ����@��� List �A��i result
		for(CfgSystemConfig csc : tmpCSC){
			
			HashMap<String,String> tmpHashMap = new HashMap<String,String>(); //String_1 : �ѼƤj�g�W��(����TableHeader), String_2 : �Ѽƭ�
			ArrayList<String> tmpList = new ArrayList<String>(); //�s��Table Body Data
			
			//A.
			Field[] fs = csc.getClass().getDeclaredFields();
			for(Field f : fs){
				//String_1
				String String_1 = f.getName().toUpperCase();
				//String_2 
				String String_2 = "";
				String getStr = "get" + StringUtil.upperCaseAtFirst(f.getName());
				//static parameters, not get
				if(!java.lang.reflect.Modifier.isStatic(f.getModifiers())){
					Method getMethod = csc.getClass().getMethod(getStr);
					Object scsResult = getMethod.invoke(csc);
					String_2 = String.valueOf(scsResult);
				} 
				if(!StringUtil.isEmpty(String_1) && !StringUtil.isEmpty(String_2)){
					tmpHashMap.put(String_1, String_2);
				}
			}
			
			//B.
			for(String tmpTh : beRegexThData){
				
				if(tmpHashMap.containsKey(tmpTh)){
					tmpList.add(tmpHashMap.get(tmpTh));
				}else{
					tmpList.add("Null");
				}
			
			}
			
			//C.
			result.add(tmpList);
			
		}
		
		return result;
	}

	
	
}
