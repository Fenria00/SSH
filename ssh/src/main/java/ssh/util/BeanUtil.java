package ssh.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

public class BeanUtil {

	/**
	 * Bean class �ഫ�� key : value, ���� Table
	 * ��B�]�p, �ȥ]�t :�Ʀr�y�r��Ѽ�
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static HashMap<String,String> transferBeanToHashMap(Object obj) throws Exception{
		
		HashMap<String,String> result = null;
		
		if(obj != null){
			
			result = new HashMap<String,String>();
			
			Field[] fs = obj.getClass().getDeclaredFields();
			for(Field f : fs){
				
				String getStr = "get" + StringUtil.upperCaseAtFirst(f.getName());
				
				//when static parameters, not do anything
				if(!java.lang.reflect.Modifier.isStatic(f.getModifiers())){
					
					Method getMethod = obj.getClass().getMethod(getStr);
					Object copyResult = getMethod.invoke(obj);
					String tmpValue = null;
					if(copyResult != null){
						tmpValue = String.valueOf(copyResult);
					}
					
					result.put(f.getName(), tmpValue);
					
				}
				
			}
			
		}
		
		return result;
	}
	
}
