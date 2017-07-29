package ssh.service;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
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
				result.put("TableHeader", thData);
				
				//TableBody : match thData which success
				List<CfgSystemConfig> tmpCSC = CacheUtil.getSysCfgDatas();
				if(tmpCSC != null && tmpCSC.size() > 0){		
					ArrayList<ArrayList<String>> tbData = buildTableBodyData(thData,tmpCSC);
					result.put("TableBody", tbData);
				}
			}
			
			//Add_TableHeader
			List<CfgSystemConfig> tmpAddTHData = CacheUtil.getSysCfgByCodeCate(SysCfgCode.CodeCate.Add_TableHeader);
			if(tmpAddTHData != null && tmpAddTHData.size() > 0){
				List<String> addThData = new ArrayList<String>();
				for(CfgSystemConfig csc : tmpAddTHData){
					addThData.add(csc.getCodeValue());
				}
				result.put("AddTableHeader", addThData);
			}
			
		}catch(Exception e){
			throw new Exception(e);
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
