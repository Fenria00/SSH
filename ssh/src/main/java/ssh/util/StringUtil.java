package ssh.util;

public class StringUtil {
	
    /**
     * �P�_�r��O�_�� null �άO�Ŧr��
     * @param str : �r��
     * @return
     */
    public static boolean isEmpty(String str) {
        boolean result = false;
        
        if (str == null || "".equals(str.trim())) {
            result = true;
        }
        
        return result;
    }
}
