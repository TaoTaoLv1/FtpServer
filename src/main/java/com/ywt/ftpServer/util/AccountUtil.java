package com.ywt.ftpServer.util;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class AccountUtil {

    private static final String ConfigFile = "server.xml";
    private static String rootDir;
    private static HashMap<String, String> users = new HashMap<String, String>();

    public static void initAccount() {

        StringBuffer pathName = new StringBuffer(System.getProperty("user.dir"));
        pathName.append("/src/main/resources/");
        pathName.append(ConfigFile);
        File file = new File(pathName.toString());

        try {
            SAXBuilder builder = new SAXBuilder();
            Document parse = builder.build(file);
            Element root = parse.getRootElement();

            //配置服务器的默认目录
            rootDir = System.getProperty("user.dir") + root.getChildText("rootDir");
            System.out.println("rootDir is: " + rootDir);

            //允许登录的用户
            Element usersE = root.getChild("users");
            @SuppressWarnings("unchecked")
            List<Element> usersEC = usersE.getChildren();
            String username = null;
            String password = null;
            System.out.println("所有用户信息：");
            for (Element user : usersEC) {
                username = user.getChildText("username");
                password = user.getChildText("password");
                System.out.println("用户名：" + username);
                System.out.println("密码：" + password);
                users.put(username, password);
            }
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getRootPath() {
        return rootDir;
    }

    public static boolean hasUsername(String username) {
        return users.get(username) != null;
    }

    public static String getPassword(String userName) {
        return users.get(userName);
    }
}
