package com.ywt.ftpServer.controller;

import com.ywt.ftpServer.dao.BaseRepository;
import com.ywt.ftpServer.dao.impl.*;

public class CommanController {

    public static BaseRepository parseCommand(String name) {
        BaseRepository command = null;
        switch (name) {
            case "USER":
                command = new UserRepository();
                break;
            case "PASS":
                command = new PassRepository();
                break;
            case "RETR":
                command = new RetrRepository();
                break;
            case "PORT":
                command = new PortRepository();
                break;
            case "LIST":
                command = new ListRepository();
                break;
            case "QUIT":
                command = new QuitRepository();
                break;
        }
        return command;
    }
}
