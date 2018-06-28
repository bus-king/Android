package busking.busking.android.busking.Commons;

import java.util.Set;

public class Constants {
    public static final int PASSWORD_LENGTH_MIN = 6;        // 최소 패스워드 길이
    public static final int NAME_LENGTH_MIN = 2;            // 이름 패스워드 길이

    public static final int DELAY_TIME = 800;
    public static final int PAGE_MY  = 0;    // My page
    public static final int PAGE_ALL = 1;    // All page

    /** 로그인 관련 값 */
    public static String        userKey;          // 사용자 Key
    public static boolean       keepLogin;
    public static String        userID;
    public static String        userPassword;
    public static Set<String>   cookie;
    public static String        pushToken;

    /** Http 관련 상수 */
    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final String SERVER_URL        = "http://211.249.63.209:120/api";
}
