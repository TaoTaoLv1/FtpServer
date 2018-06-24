package com.ywt.ftpServer.dao;

import com.ywt.ftpServer.entity.User;

import java.io.BufferedWriter;

public interface BaseRepository {

    void executeRepository(String datas, BufferedWriter writer, User user);
}
