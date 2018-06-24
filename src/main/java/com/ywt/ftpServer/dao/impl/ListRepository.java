package com.ywt.ftpServer.dao.impl;

import com.ywt.ftpServer.dao.BaseRepository;
import com.ywt.ftpServer.entity.User;
import com.ywt.ftpServer.server.FileServer;
import com.ywt.ftpServer.util.AccountUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ListRepository implements BaseRepository {

    @Override
    public void executeRepository(String datas, BufferedWriter writer, User user) {
        File file = new File(AccountUtil.getRootPath());
        if (!file.isDirectory()) {
            try {
                writer.write("210  文件目录不存在！\r\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            //拼接文件目录字符串
            StringBuffer dirList = new StringBuffer();
            int count = 1;
            for (String item : file.list()) {
                File itemFile = new File(file + File.separator + item);
                String size = FileServer.getFileSize(itemFile);
                if (size.equals("") || size == null) {
                    size = "dir";
                } else {
                    size += "	file";
                }
                dirList.append(count + "	" + item + "	" + size);
                dirList.append("\r\n");
                count++;
            }
            try {
                //告知客户端：服务器向另外一个端口发送数据
                writer.write("150 open ascii mode...\r\n");
                writer.flush();
                //与客户端发来的ip和端口号连接,自身端口设置为20
                Socket socket = new Socket(user.getIp(), user.getPort(), null, 20);
                System.out.println(socket.getLocalPort());
                BufferedWriter portWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"gbk"));
                portWriter.write(dirList.toString());
                portWriter.flush();
                socket.close();
                writer.write("220 transfer complete...\r\n");
                writer.flush();
                System.out.println(dirList.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
