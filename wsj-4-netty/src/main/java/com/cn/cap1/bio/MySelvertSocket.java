package com.cn.cap1.bio;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MySelvertSocket {
    private static ExecutorService executorService = Executors.newFixedThreadPool(5);
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket();
        ss.bind(new InetSocketAddress(5678));
        while (true) {
            executorService.execute(new Hanndle(ss.accept()));
        }
    }

    private static class Hanndle implements  Runnable {
        private Socket sc;

        public Hanndle(Socket sc) {
            this.sc = sc;
        }

        public void run() {
            try (ObjectInputStream in = new ObjectInputStream(sc.getInputStream());
                 ObjectOutputStream out = new ObjectOutputStream(sc.getOutputStream())) {
                 String msg = in.readUTF();
                System.out.println("收到消息："+msg);
                out.writeUTF("hello "+ msg);
                out.flush();
            }catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    sc.close();
                }catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
    }
}
