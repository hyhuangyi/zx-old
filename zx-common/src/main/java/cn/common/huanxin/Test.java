package cn.common.huanxin;

import cn.common.huanxin.model.TalkMsg;
import cn.common.huanxin.model.TalkNode;
import cn.common.huanxin.service.TalkDataService;
import cn.common.huanxin.service.impl.TalkDataServiceImpl;
import cn.common.huanxin.service.impl.TalkHttpServiceImplApache;

import java.util.HashMap;
import java.util.Map;



public class Test {

	public static void main(String[] args) {
		TalkDataService service = new TalkDataServiceImpl(new TalkHttpServiceImplApache());
		TalkNode talkNode = new TalkNode();
		try {

			TalkMsg talkMsg=new TalkMsg();
			talkMsg.setTarget_type(1);
			talkMsg.setType(1);//消息类型
			talkMsg.setMsg("hello");
			talkMsg.setFrom("zx");
			String[]target=new String []{"huangy"};
			 talkMsg.setTarget(target);
			Map<String,Object> ext=new HashMap<String, Object>();
			ext.put("agent_username", "huangy");
			talkMsg.setExt(ext);
			talkNode=service.messageSave(talkMsg);
			System.out.println(talkNode);


			// 获取消息数量(离线)
			//talkNode=service.userMessageCount("admin");
			//System.out.println(talkNode);

			// 添加好友账户
			// talkNode=service.friendSave("admin", "123456");

			// 获取好友列表
			// talkNode=service.friendList("xiaoming");

			// 登录环信平台
			// talkNode=service.login("lisi", "lisi");

			// 退出环信平台
			// talkNode=service.logout("lisi");

			// 添加单个用户
			// talkNode=service.userSave("liulele123", "liulele123", "liulele123");

			// 添加用户信息(批量)
			/*
			 * String [] usernames=new String[]{"zhangsan","lisi"}; String [] passwords=new
			 * String[]{"zhangsan","lisi"}; String [] nicknames=new
			 * String[]{"zhangsan","lisi"};
			 * 
			 * talkNode=service.userSave(usernames, passwords, nicknames);
			 */

			// 删除用户信息(单个)
			// talkNode=service.userDrop("zhangsan");

			// 删除用户信息(批量)
			/*
			 * Integer size=1; talkNode=service.userDrop(size.longValue());
			 */

			// 修改用户昵称
			// talkNode=service.userModifyNickname("lisi", "李四");

			// 修改用户密码
			// talkNode=service.userModifyPassword("lisi", "lisi1");

			// 获取用户信息
			// talkNode=service.userGet("lisi");

			// 获取用户信息
			/*
			 * Integer size=1; String cursor="1"; Integer start=1;
			 * talkNode=service.userList(size.longValue(), cursor, start.longValue());
			 */

			// 判断用户状态
			// talkNode=service.userLine("lisi");

			// 获取所属群组(好友群)
			// talkNode=service.userGroupList("lisi");

			// 获取消息数量(离线)
			// talkNode=service.userMessageCount("lisi");

			// 获取消息状态(离线)
			// talkNode=service.userMessageLine("lisi", "");

			// 添加好友账户
			// talkNode=service.friendSave("lisi", "xiaoming");

			// 获取消息列表
			/*
			 * Integer size=1; String cursor="1"; Integer start=1;
			 * talkNode=service.messageList(size.longValue(), cursor, start.longValue());
			 */

			// 获取聊天列表
			/*
			 * Integer size=1; String cursor="1"; Integer start=1;
			 * talkNode=service.chatList(size.longValue(), cursor, start.longValue());
			 */

			// 添加聊天群组
			/*
			 * String[] friend=new String []{"lisi","liu123","liulele123"};
			 * talkNode=service.roomSave("lisi", "xiaoming群组","xiaoming群组", 100, friend);
			 */

			// 添加消息数据 文本 取值为【1=文本消息、2=图片消息、3=语音消息、4=视频消息、5=透传消息】
			/*
			 * TalkMsg talkMsg=new TalkMsg(); //取值为【1=文本消息，1=用户消息、2=群发消息、3=聊天室消息】
			 * talkMsg.setTarget_type(1);//users 给用户发消息。chatgroups: 给群发消息，chatrooms: 给聊天室发消息
			 * String[]target=new String []{"liu123","xiaoming","1783660491"};
			 * talkMsg.setTarget(target);// 注意这里需要用数组，数组长度建议不大于20，即使只有一个用户，也要用数组
			 * ['u1']，给用户发送时数组元素是用户名，给群组发送时 数组元素是groupid
			 * talkMsg.setMsg("123ghgf");//消息内容，参考[[start:100serverintegration:30chatlog|
			 * 聊天记录]]里的bodies内容 //
			 * talkMsg.setMsg("哈哈哈");//消息内容，参考[[start:100serverintegration:30chatlog|聊天记录]]
			 * 里的bodies内容 talkMsg.setType(1);//消息类型
			 * talkMsg.setFrom("admin");//表示消息发送者。无此字段Server会默认设置为"from":"admin"，
			 * 有from字段但值为空串("")时请求失败 talkNode=service.messageSave(talkMsg);
			 */

			// 上传图片
			/*
			 * talkNode=service.fileUpload(new File("F://Image//3.jpg")); String
			 * imageUrl=talkNode.getUri(); TalkUserMessage
			 * talkUserMessage=talkNode.getEntities().get(0); String
			 * imageUuid=talkUserMessage.getUuid(); String
			 * imageSecret=talkUserMessage.getShare_secret();
			 * System.out.println("imagetalkNode:"+imageUrl+"/"+imageUuid);
			 * 
			 * 
			 * //添加消息数据 图片 必须先上传图片 取值为【1=文本消息、2=图片消息、3=语音消息、4=视频消息、5=透传消息】 TalkMsg data=new
			 * TalkMsg(); //取值为【1=文本消息，1=用户消息、2=群发消息、3=聊天室消息】 data.setTarget_type(1);//users
			 * 给用户发消息。chatgroups: 给群发消息，chatrooms: 给聊天室发消息 String[]target=new String
			 * []{"liu123","xiaoming","lisi"}; data.setTarget(target);//
			 * 注意这里需要用数组，数组长度建议不大于20，即使只有一个用户，也要用数组 ['u1']，给用户发送时数组元素是用户名，给群组发送时
			 * 数组元素是groupid data.setType(2); // 消息类型 data.setUrl(imageUrl+"/"+imageUuid);
			 * //成功上传文件返回的UUID System.out.println("data.getUrl():"+data.getUrl());
			 * //data.setFile_id("370b7c20-68f3-11e6-b05e-4fa50592b127");//是上传文件返回的uuid
			 * data.setFilename(imageUuid+".jpg"); // 指定一个文件名 data.setSecret(imageSecret);//
			 * 成功上传文件后返回的secret data.setWidth(120);//宽 data.setHeight(120);//高
			 * data.setFrom("admin"); talkNode=service.messageSave(data);
			 */

			/*
			 * //上传语音 // talkNode=service.fileUpload(new File("F://Image//12.wav"));//最安全的
			 * 指定文件名为 wav talkNode=service.fileUpload(new File("F://Image//18.mp3"));//不可以
			 * 指定文件名为 MP3不可以 指定为wav可以 // talkNode=service.fileUpload(new
			 * File("F://Image//13.amr"));//可以 注意指定文件名为wav可以 位amr不可以 //
			 * talkNode=service.fileUpload(new File("F://Image//15.wma"));//可以
			 * 指定wma,amr,mp3文件名不可以 指定wav文件名可以 String audioUrl=talkNode.getUri();
			 * TalkUserMessage talkUserMessage=talkNode.getEntities().get(0); String
			 * audioUuid=talkUserMessage.getUuid(); String
			 * audioSecret=talkUserMessage.getShare_secret(); Integer
			 * audioDuration=talkNode.getDuration();
			 * System.out.println("imagetalkNode:"+audioUrl+"/"+audioUuid);
			 * 
			 * //添加消息数据 语音 必须先上传语音 取值为【1=文本消息、2=图片消息、3=语音消息、4=视频消息、5=透传消息】 TalkMsg data=new
			 * TalkMsg(); //取值为【1=文本消息，1=用户消息、2=群发消息、3=聊天室消息】 data.setTarget_type(1);//users
			 * 给用户发消息。chatgroups: 给群发消息，chatrooms: 给聊天室发消息 String[]target=new String
			 * []{"liu123","xiaoming","lisi"}; data.setTarget(target);//
			 * 注意这里需要用数组，数组长度建议不大于20，即使只有一个用户，也要用数组 ['u1']，给用户发送时数组元素是用户名，给群组发送时
			 * 数组元素是groupid data.setType(3);// 消息类型 data.setUrl(audioUrl+"/"+audioUuid);
			 * //成功上传文件返回的UUID System.out.println("data.getUrl():"+data.getUrl()); //
			 * data.setFilename(audioUuid); // 指定一个文件名 data.setFilename(audioUuid+".wav");
			 * // 指定一个文件名 恒定可以 // data.setFilename(audioUuid+".mp3"); // 指定一个文件名 //
			 * data.setFilename(audioUuid+".amr"); // 指定一个文件名 //
			 * data.setFilename(audioUuid+".wma"); // 指定一个文件名 // data.setLength(10);
			 * data.setLength(audioDuration); data.setSecret(audioSecret);//
			 * 成功上传文件后返回的secret data.setFrom("admin"); talkNode=service.messageSave(data);
			 */

			// 上传图片为缩略图
			/*
			 * TalkNode talkNodeImage=service.fileUpload(new File("F://Image//3.jpg"));
			 * String imageUrl=talkNodeImage.getUri(); TalkUserMessage
			 * talkUserMessageImage=talkNodeImage.getEntities().get(0); String
			 * imageUuid=talkUserMessageImage.getUuid(); String
			 * imageSecret=talkUserMessageImage.getShare_secret();
			 * System.out.println("imagetalkNode:"+imageUrl+"/"+imageUuid); //上传视频
			 * talkNode=service.fileUpload(new
			 * File("F://Image//1.mp4"));//指定文件名为mp4,avi,rmvb不可以 //
			 * talkNode=service.fileUpload(new File("F://Image//21.avi"));//指定文件名为 //
			 * talkNode=service.fileUpload(new File("F://Image//22.rmvb"));//指定文件名为 //
			 * talkNode=service.fileUpload(new File("F://Image//23.mkv"));//指定文件名为 //
			 * talkNode=service.fileUpload(new File("F://Image//24.wma"));//指定文件名为 //
			 * talkNode=service.fileUpload(new File("F://Image//25.rm"));//指定文件名为 String
			 * videoUrl=talkNode.getUri(); TalkUserMessage
			 * talkUserMessage=talkNode.getEntities().get(0); String
			 * videoUuid=talkUserMessage.getUuid(); String
			 * videoSecret=talkUserMessage.getShare_secret();
			 * System.out.println("imagetalkNode:"+videoUrl+"/"+videoUuid); //添加消息数据 视频
			 * 必须先上传语音 取值为【1=文本消息、2=图片消息、3=语音消息、4=视频消息、5=透传消息】 TalkMsg data=new TalkMsg();
			 * //取值为【1=文本消息，1=用户消息、2=群发消息、3=聊天室消息】 data.setTarget_type(1);//users
			 * 给用户发消息。chatgroups: 给群发消息，chatrooms: 给聊天室发消息 String[]target=new String
			 * []{"liu123","xiaoming","lisi"}; data.setTarget(target);//
			 * 注意这里需要用数组，数组长度建议不大于20，即使只有一个用户，也要用数组 ['u1']，给用户发送时数组元素是用户名，给群组发送时
			 * 数组元素是groupid data.setFrom("admin"); data.setType(4);// 消息类型 //
			 * data.setFilename(videoUuid+".mp4"); // 指定一个文件名 //
			 * data.setFilename(videoUuid+".avi"); // 指定一个文件名 //
			 * data.setFilename(videoUuid+".rmvb"); // 指定一个文件名
			 * data.setFilename(videoUuid+".mkv"); // 指定一个文件名 //
			 * data.setFilename(videoUuid+".wma"); // 指定一个文件名 //
			 * data.setFilename(videoUuid+".rm"); // 指定一个文件名
			 * data.setThumb_secret(videoUrl+"/"+videoUuid);//成功上传视频缩略图返回的UUID
			 * data.setLength(20);//视频播放长度 data.setSecret(videoSecret);// 成功上传文件后返回的secret
			 * data.setUrl(videoUrl+"/"+videoUuid); //成功上传文件返回的UUID
			 * talkNode=service.messageSave(data);
			 */

			// 发送透传消息
			/*
			 * TalkMsg data=new TalkMsg(); //取值为【1=文本消息，1=用户消息、2=群发消息、3=聊天室消息】
			 * data.setTarget_type(1);//users 给用户发消息。chatgroups: 给群发消息，chatrooms: 给聊天室发消息
			 * String[]target=new String []{"liu123","xiaoming","lisi"};
			 * data.setTarget(target);// 注意这里需要用数组，数组长度建议不大于20，即使只有一个用户，也要用数组
			 * ['u1']，给用户发送时数组元素是用户名，给群组发送时 数组元素是groupid data.setType(4);// 消息类型
			 * data.setAction("action1");//调用的action data.setFrom("admin");
			 * //表示消息发送者。无此字段Server会默认设置为"from":"admin"，有from字段但值为空串("")时请求失败
			 * talkNode=service.messageSave(data);
			 */

			// 发送扩展消息
			/*
			 * TalkMsg data=new TalkMsg(); //取值为【1=文本消息，1=用户消息、2=群发消息、3=聊天室消息】
			 * data.setTarget_type(1);//users 给用户发消息。chatgroups: 给群发消息，chatrooms: 给聊天室发消息
			 * String[]target=new String []{"liu123","xiaoming","lisi"};
			 * data.setTarget(target);// 注意这里需要用数组，数组长度建议不大于20，即使只有一个用户，也要用数组
			 * ['u1']，给用户发送时数组元素是用户名，给群组发送时 数组元素是groupid data.setType(4);// 消息类型
			 * data.setMsg("消息");// 随意传入都可以 data.setFrom("admin");
			 * //表示消息发送者。无此字段Server会默认设置为"from":"admin"，有from字段但值为空串("")时请求失败
			 * Map<String,Object>ext=new HashMap<String, Object>(); ext.put("ext", new
			 * Date()); data.setExt(ext); talkNode=service.messageSave(data);
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
