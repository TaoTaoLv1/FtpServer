package com.ywt.ftpServer.dao.impl;

import com.ywt.ftpServer.dao.BaseRepository;
import com.ywt.ftpServer.entity.User;

import java.io.BufferedWriter;
import java.io.IOException;

public class PortRepository implements BaseRepository {

    @Override
    public void executeRepository(String datas, BufferedWriter writer, User user) {
        String[] result = datas.split(",");
        String ip = result[0] + "." + result[1] + "." + result[2] + "." + result[3];
        System.out.println("ip:" + ip);
        int port = Integer.parseInt(result[4]) * 256 + Integer.parseInt(result[5]);
        System.out.println("port:" + port);
        try {
            writer.write("200 Received IP number and port number\r\n");
            writer.flush();
            user.setIp(ip);
            user.setPort(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
