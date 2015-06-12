package com.bmob.demo.third;

public class Constants {

	/**  
	 *  此为Bmob的APP_ID
	 */  
	public static String BMOB_APPID = "46c730e7e33eabeb3ec790b3fb0a02d7";//--外网
//	public static String BMOB_APPID = "e97f215c671442a29b08ebc9d7171117";//--内网
	
	/**  
	 * 此为腾讯官方提供给开发者用于测试的APP_ID，个人开发者需要去QQ互联官网为自己的应用申请对应的AppId
	 */  
	public static final String QQ_APP_ID ="222222";
	
	/**  
	 *  微信平台的APPID,请自行前往微信开放平台注册申请应用
	 */  
	public static final String WEIXIN_APP_ID ="wx4f49df1c2cfc15eb";
	
	/**  
	 * 微信平台的AppSecret  
	 */  
	public static final String WEIXIN_APP_SECRET ="03eec97e8be49cd84f67bbe12469f19e";
	
	/**  
	 *  微信平台的grant type，固定值：authorization_code
	 */  
	public static final String WEIXIN_GRANT_TYPE ="authorization_code";
	
	
    /**  
     * 当前Demo对应的weibo平台的APP_KEY ，开发者需使用自己的 APP_KEY替换该 APP_KEY
     */  
    public static final String WEIBO_APP_KEY      = "248229023";

    /** 
     * 当前 DEMO 应用的回调页，第三方应用可以使用自己的回调页。
     * <p>
     * 注：关于授权回调页对移动客户端应用来说对用户是不可见的，所以定义为何种形式都将不影响，
     * 但是没有定义将无法使用 SDK进行认证登录。
     * 建议使用默认回调页：https://api.weibo.com/oauth2/default.html
     * 
     * 注意:此回调地址必须和微博开放平台中填写的回调地址一致（即在新浪微博开放平台->我的应用->应用信息->高级应用->授权设置->授权回调页中填写的）
     * 否则会报：redirect_uri_mismatch（21322）错误
     * 
     * </p>
     */
    public static final String WEIBO_REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

    /**
     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
     * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利
     * 选择赋予应用的功能。
     * 
     * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的
     * 使用权限，高级权限需要进行申请。
     * 
     * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
     * 
     * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
     */
    public static final String WEIBO_SCOPE = 
            "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";
}
