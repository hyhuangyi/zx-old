package cn.common.huanxin;

import cn.common.huanxin.model.Authentic;
import cn.common.huanxin.service.TalkDataService;
import cn.common.huanxin.service.impl.TalkDataServiceImpl;
import cn.common.huanxin.service.impl.TalkHttpServiceImplApache;
import cn.common.huanxin.tool.JsonTool;


public class TalkTest {
	public static Authentic.Token TEST_TOKEN = new Authentic.Token(
			"YWMtPUYEamsSEemY9i3SzkMCpgAAAAAAAAAAAAAAAAAAAAHioD6QawIR6bPoZbAE-JYuAgMAAAFqbPUw3QBPGgBXAvdto4slYbdSwSuXSsZH_1kBIpgT1lWwMyK8v1tRbg", 1561790173048L);
	public static String TEST_USERNAME = "zx";
	public static String TEST_PASSWORD = "123456";

	public static void main1(String[] args) throws Exception {
		// 初始服务端Token
		Authentic.Token token = new Authentic(new TalkHttpServiceImplApache()).getToken();
		System.out.println(token.getToken());
		System.out.println(token.getExpire() + "L");
	}

	/**
	 * 注册|登录功能
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// 通过构造方法注入http请求业务以实现数据业务
		TalkDataService service = new TalkDataServiceImpl(new TalkHttpServiceImplApache());
		// 修改数据业务Token
		service.setToken(TEST_TOKEN);

		// 删除
		// System.out.println("删除="+JsonTool.write(service.userDrop(TEST_USERNAME))+"\n");
		// 注册
		System.out.println("注册=" + JsonTool.write(service.userSave(TEST_USERNAME, TEST_PASSWORD, "上而求索")) + "\n");
		// 登录
		System.out.println("登录=" + JsonTool.write(service.login(TEST_USERNAME, TEST_PASSWORD)) + "\n");
	}

}