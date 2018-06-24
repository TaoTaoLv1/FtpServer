package com.ywt.ftpServer.controller;

import com.ywt.ftpServer.dao.BaseRepository;
import com.ywt.ftpServer.entity.User;

import java.io.*;
import java.net.Socket;

public class ClientConnectionController implements Runnable {

	private Socket socket;
	private BufferedWriter writer;
	private BufferedReader reader;

	public ClientConnectionController(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"gbk"));
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream(),"gbk"));

			// 为各个command传输信息
			User user = new User();
			user.setSocket(socket);
			// 第一次建立连接
			writer.write("220 成功连接，请输入用户名\r\n");
			writer.flush();
			reader.readLine();
			writer.write("\r\n");
			writer.flush();

			// 接受客户端发来的信息
			while (true) {
				if (!socket.isClosed()) {
					String result = null;
					try {
						result = reader.readLine();
					} catch (Exception e) {
						System.out.println("客户端强制关闭了服务器");
					}
					System.out.println(result);// 打印出客户端发送的内容
					if (result != null && result != "") {
						String[] datas = result.split(" ");
						BaseRepository command = CommanController.parseCommand(datas[0]);
						if (command != null) {
							// 当客户端发来的数据只有命令没有后面的数据时候
							if (datas.length == 1) {
								command.executeRepository("", writer, user);
							} else {
								command.executeRepository(datas[1], writer, user);
							}
						} else {
							writer.write("没有这个命令！\r\n");
							writer.flush();
						}

					} else {
						// 这时客户端强行关闭了连接
						reader.close();
						writer.close();
						socket.close();
						break;
					}
				} else {
					break;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
