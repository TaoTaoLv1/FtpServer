package com.ywt.ftpServer.dao.impl;

import com.ywt.ftpServer.dao.BaseRepository;
import com.ywt.ftpServer.entity.User;
import com.ywt.ftpServer.util.AccountUtil;

import java.io.BufferedWriter;
import java.io.IOException;


public class UserRepository implements BaseRepository {
    @Override
    public void executeRepository(String userName, BufferedWriter writer, User user) {
        if (AccountUtil.hasUsername(userName)) {
            System.out.println("用户名存在："+userName);
            try {
                writer.write("331请输入你的密码\r\n");
                writer.flush();
                user.setUsername(userName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("此用户名不存在! "+userName);
            try {
                writer.write("501 此用户名不存在\r\n");
                writer.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
