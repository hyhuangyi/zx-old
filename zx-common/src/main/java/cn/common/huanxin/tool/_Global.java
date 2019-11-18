package cn.common.huanxin.tool;

/**
 * @ClassName: _Global 
 * @Description: 环信开发配置文件
 */
public class _Global {
	public static int APP_PAGE_SIZE = 5;
	/** 以下配置value需要配置    */
//	public static String APP_KEY = "5816136#healthy";
//	public static String APP_CLIENT_ID = "YXA6MCXpoOIOEee9Ut8dSMhHHg";
//	public static String APP_CLIENT_SECRET = "YXA6DyAXEejefDGqkHiP2uYwqPtoD_A";
	public static String APP_KEY = "1428190430068347#hy";
	public static String APP_CLIENT_ID = "YXA64qA-kGsCEemz6GWwBPiWLg";
	public static String APP_CLIENT_SECRET = "YXA61CjnlwU0J44V1gfQ18i74KCG1mc";
	/** 以上配置value需要配置    */
	public static final int HTTP_METHOD_GET = 1;
	public static final int HTTP_METHOD_POST = 2;
	public static final int HTTP_METHOD_PUT = 3;
	public static final int HTTP_METHOD_DELETE = 4;
	public static final String URL_HOST = "https://a1-vip5.easemob.com/" + APP_KEY.replace("#", "/") + "/";
	public static final String URR_TOKEN = URL_HOST + "token";
	public static final String URL_CHAT = URL_HOST + "chatmessages";
	public static final String URL_GROUP = URL_HOST + "chatgroups";
	public static final String URL_FILE = URL_HOST + "chatfiles";
	public static final String URL_ROOM = URL_HOST + "chatrooms";
	public static final String URL_MESSAGES = URL_HOST + "messages";
	public static final String URL_USER = URL_HOST + "users";
}