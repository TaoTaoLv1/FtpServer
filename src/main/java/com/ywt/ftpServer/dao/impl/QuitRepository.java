package com.ywt.ftpServer.dao.impl;

import com.ywt.ftpServer.dao.BaseRepository;
import com.ywt.ftpServer.entity.User;

import java.io.BufferedWriter;
import java.io.IOException;

public class QuitRepository implements BaseRepository {

    @Override
    public void executeRepository(String datas, BufferedWriter writer, User user) {
        try {
            writer.write("221 再见\r\n");
            writer.flush();
            user.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
