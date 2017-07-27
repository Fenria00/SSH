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

	/**��Ĥ@�Ӧr���ন�j�g
	 * @param digits
	 * @return
	 */
	public static String upperCaseAtFirst(String digits) {
		StringBuilder sb = new StringBuilder(digits);
		sb = sb.replace(0, 1, String.valueOf(sb.charAt(0)).toUpperCase());
		return sb.toString();
	}
    
    
}
