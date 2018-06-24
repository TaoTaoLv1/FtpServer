package com.ywt.ftpServer.controller;

import com.ywt.ftpServer.server.ThreadServer;
import com.ywt.ftpServer.util.AccountUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 初始化ftp服务器，接受客户端的连接
 */
public class FtpStartController {

    private ServerSocket serverSocket;

    private FtpStartController() {
        //此方法为初始化可以接入的帐号密码
        AccountUtil.initAccount();
        try {
            serverSocket = new ServerSocket(21);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void listen() {
        try {
            while (true) {
                Socket socket = serverSocket.accept();
                ClientConnectionController connection = new ClientConnectionController(socket);
                ThreadServer.getThreadPool().execute(connection);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        FtpStartController ftpServer = new FtpStartController();
        ftpServer.listen();
    }

}
